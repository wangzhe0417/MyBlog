package com.jyh.action;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.mail.search.SearchException;

import org.apache.struts2.ServletActionContext;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.jyh.domain.Article;
import com.jyh.domain.ArticleClass;
import com.jyh.domain.Dynamic;
import com.jyh.domain.PersonalClassification;
import com.jyh.domain.User;
import com.jyh.service.ArticleService;
import com.jyh.service.DynamicService;
import com.jyh.service.UserService;
import com.jyh.utils.JsonConvertUtil;
import com.jyh.utils.LuceneUtil;
import com.jyh.utils.PageUtil;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
public class ArticleAction extends MyBaseAction {
	
	private ArticleService articleService;
	private UserService userService;
	private Article article;
	private String pageNumber;				//页码
	private String userId;					//访问的博客的主人id
	private String myArticleClassId;		//我的文章分类
	private String dateClass;				//我的存档
	private Integer articleClass;
	private String queryCondition;					//查询条件
	//struts2无法动态的设置标签name属性，意思就是无法设置下标，所以不用struts2标签，然后就无法使用ognl表达式
	//不用ognl表达式就无法传递set集合，所以就用list集合接收数据
	private List<PersonalClassification> classList;
	private DynamicService dynamicService;
	
	public ArticleService getArticleService() {
		return articleService;
	}
	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public String getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMyArticleClassId() {
		return myArticleClassId;
	}
	public void setMyArticleClassId(String myArticleClassId) {
		this.myArticleClassId = myArticleClassId;
	}
	public String getDateClass() {
		return dateClass;
	}
	public void setDateClass(String dateClass) {
		this.dateClass = dateClass;
	}
	public Integer getArticleClass() {
		return articleClass;
	}
	public void setArticleClass(Integer articleClass) {
		this.articleClass = articleClass;
	}
	public List<PersonalClassification> getClassList() {
		return classList;
	}
	public void setClassList(List<PersonalClassification> classList) {
		this.classList = classList;
	}
	public DynamicService getDynamicService() {
		return dynamicService;
	}
	public void setDynamicService(DynamicService dynamicService) {
		this.dynamicService = dynamicService;
	}
	public String getQueryCondition() {
		return queryCondition;
	}
	public void setQueryCondition(String queryCondition) {
		this.queryCondition = queryCondition;
	}
	
	
	
	
	//保存文章
	@SuppressWarnings("unchecked")
	public String saveArticle() throws Exception{
		if(baseUser == null){
			setState("3");
			setMessage("loginView.action");
			return SUCCESS;
		}
		Set<ArticleClass> clSet = new HashSet<>();
		
		//如果选择了个人分类
		if(classList != null && classList.size() > 0){
			//循环将个人分类塞入到文章和分类的关联类中去，然后将关联类塞入set集合
			for (PersonalClassification cl : classList) {
				if(cl != null){
					ArticleClass articleClass = new ArticleClass();
					articleClass.setPersonalClassification(cl);
					clSet.add(articleClass);
				}
			}
		}else{//如果没有选择个人分类，则会遍历当前登录用户的个人分类，找到默认分类
			Set<PersonalClassification> personCls = baseUser.getPersonalClassifications();
			Iterator<PersonalClassification> iterator = personCls.iterator();
			while(iterator.hasNext()){
				PersonalClassification userCl = iterator.next();
				if(userCl == null)
					continue;
				if(userCl.getDefaultSetting()!= null &&  userCl.getDefaultSetting() == 0){//如果是默认分类
					ArticleClass articleClass = new ArticleClass();
					articleClass.setPersonalClassification(userCl);
					clSet.add(articleClass);
				}
			}

		}
		article.setArticleDate(Calendar.getInstance().getTime());
		article.setUserByAuthorId(baseUser);
		article.setReadnum(0);
		article.setArticleClasses(clSet);
		if(article.getArticleId() == null || article.getArticleId().isEmpty()){
			Serializable id = articleService.saveEntity(article);
			article.setArticleId(id.toString());
			LuceneUtil.add(article);
		}
		else{
			articleService.updateEntity(article);
			LuceneUtil.update(article);
		}
		setState("0");
		//添加动态
		Dynamic dynamic = new Dynamic(article, baseUser, Calendar.getInstance().getTime(), 0);
		dynamicService.saveEntity(dynamic);
		return SUCCESS;
	}
	
	//编写文章页面跳转
	public String writeArticle(){
		//没有登录
		if(baseUser == null)
			return "index";
		//编辑文章
		if(article != null && !article.getArticleId().isEmpty()){
			Article editArticle = articleService.getEntityById(article.getArticleId());
			ServletActionContext.getRequest().setAttribute("article", editArticle);
			//ActionContext.getContext().put("article", editArticle);
			return SUCCESS;
		}
		//有草稿就打开草稿
		Article draftArticle = articleService.getArticleByState(0);
		if(draftArticle != null)
			ServletActionContext.getRequest().setAttribute("article", draftArticle);
			//ActionContext.getContext().put("article", draftArticle);
		return SUCCESS;
	}
	
	//删除文章
	public String deleteArticle() throws Exception{
		if(baseUser == null){
			setState("3");
			setMessage("loginView.action");
			return SUCCESS;
		}
		if(article != null && !article.getArticleId().isEmpty()){
			LuceneUtil.delete(article.getArticleId());
			articleService.deleteEntityById(article.getArticleId());
			setState("0");
			setMessage("删除成功!");
		}
		else {
			setState("2");
			setMessage("删除失败!");
		}
		return "deleteArticle";
	}
	
	//主页跳转
	public String index(){
		List<User> users = userService.getRecommendUsers();
		PageUtil articlesPage = articleService.findArticlesPage("1");
		ActionContext.getContext().put("users", users);
		ActionContext.getContext().put("page", articlesPage);
		return SUCCESS;
	}
	
	//获取全部文章列表(翻页)
	public String getArticlesList(){
		PageUtil articlesPage = articleService.findArticlesPage(pageNumber);
		ActionContext.getContext().put("page", articlesPage);
		return SUCCESS;
	}
	
	//搜索文章
	public String searchAllArticle() {
		List<Article> articles = new ArrayList<Article>();
    	try {
			articles = LuceneUtil.search(queryCondition);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
    	ActionContext.getContext().put("articleList",articles);
		return SUCCESS;
	}
	
	//获取分类文章列表
	public String articleClass(){
		//判断有没有传过来分类id
		if(articleClass != null && !"".equals(articleClass))
			//如果传了则表示点击了新的分类，将该分类id存入session中去
			ActionContext.getContext().getSession().put("articleClass", articleClass);
		else//如果没有传则是对上一次点击的分类进行翻页，那么从session中获取上次点击的分类的id
			articleClass = (Integer)ActionContext.getContext().getSession().get("articleClass");
		
		PageUtil articlesPage = articleService.findArticlesPageByClass(articleClass,pageNumber);
		ActionContext.getContext().put("page", articlesPage);
		return SUCCESS;
	}
	
	//点击主页文章标题进入用户博客页面查看 
	public String articleDetail(){
		//获取文章信息
		Article articleDetail = articleService.getEntityById(article.getArticleId());
		if(articleDetail == null){
			return ERROR;
		}
		//对评论进行处理
		ActionContext.getContext().put("articleComments", articleService.handleCommentsForArticle(article.getArticleId()));
		
		//获取用户信息
		User u = articleDetail.getUserByAuthorId();
		ActionContext.getContext().put("blogHost", u);

		//将访问的博主的id存入session，为了以后再进去其它请求(例如分页或者分类等)
		ActionContext.getContext().getSession().put("visitedUser", u);
		
		ActionContext.getContext().put("articleDetail", articleDetail);
		
		//获取文章的各种分类信息(左边信息栏)
		ActionContext.getContext().put("articlesData", articleService.getMyBlogArticleData(u.getUserId()));
		
		
		
		//如果不是自己
		if(baseUser != null && !baseUser.getUserId().equals(u.getUserId())){
			//阅读数增加一
			articleDetail.setReadnum(articleDetail.getReadnum()+1);
			articleService.updateEntity(articleDetail);
			//访问量加1
			u.setVisitedNum(u.getVisitedNum()+1);
			userService.updateEntity(u);
		}
		return SUCCESS;
	}
	

	//点击用户进入用户的博客页面
	public String myBlogView(){
		//获取用户信息
		User u = userService.getEntityById(userId);
		ActionContext.getContext().put("blogHost", u);
		
		//将访问的博主的id存入session，为了以后再进去其它请求(例如分页或者分类等)
		ActionContext.getContext().getSession().put("visitedUser", u);
		
		//获取用户文章列表
		ActionContext.getContext().put("page", articleService.findUserArticlesPage(userId, pageNumber));
		
		//获取文章的各种分类信息(左边信息栏)
		ActionContext.getContext().put("articlesData", articleService.getMyBlogArticleData(userId));
		
		//如果不是自己
		if(baseUser != null && !baseUser.getUserId().equals(u.getUserId())){
			//访问数增加1
			u.setVisitedNum(u.getVisitedNum()+1);
			userService.updateEntity(u);
		}
		
		return SUCCESS;
	}
	
	//我的全部文章翻页
	public String getMyArticlesList(){
		//获取用户文章列表
		ActionContext.getContext().put("page", articleService.findUserArticlesPage(visitedUser.getUserId(), pageNumber));
		
		return SUCCESS;
	}
	
	//我的博客页面根据文章名搜索文章
	public String searchArticle(){
		String searchTitle = "";
		if(article != null && !article.getArticleTitle().isEmpty()){
			searchTitle = article.getArticleTitle();
		}
		//判断有没有传过来搜索的文章标题
		if(searchTitle == null || "".equals(searchTitle))
			//如果没则表示在上次搜索的基础上进行翻页，从session中获取上次存入的文章标题
			searchTitle = (String)ActionContext.getContext().getSession().get("searchTitle");
		else//反之则表示是新的搜索，将新的搜索文章标题存入session
			ActionContext.getContext().getSession().put("searchTitle", searchTitle);
		PageUtil page = articleService.findUserArticlesPageByTitle(visitedUser.getUserId(),searchTitle , pageNumber);
		ActionContext.getContext().put("page", page);
		return SUCCESS;
	}
	
	//按照我的分类查看文章
	public String myClassArticle(){
		//判断有没有传过来分类id
		if(myArticleClassId == null || myArticleClassId.isEmpty())
			//如果没则表示在上次点击的分类的基础上进行翻页，从session中获取上次点击的分类id
			myArticleClassId = (String)ActionContext.getContext().getSession().get("myArticleClassId");
		else//反之则表示是新的分类，将新的分类id存入session
			ActionContext.getContext().getSession().put("myArticleClassId", myArticleClassId);
		PageUtil page = articleService.findUserArticlesPageByClass(visitedUser.getUserId(),myArticleClassId , pageNumber);
		ActionContext.getContext().put("page", page);
		return SUCCESS;
	}
	
	//按照我的日期存档查看文章
	public String myArticleGroupByDate(){
		if(dateClass == null || dateClass.isEmpty())
			dateClass = (String)ActionContext.getContext().getSession().get("dateClass");
		else
			ActionContext.getContext().getSession().put("dateClass", dateClass);
		PageUtil page = articleService.findUserArticlesGroupByDate(visitedUser.getUserId(),dateClass , pageNumber);
		ActionContext.getContext().put("page", page);
		return SUCCESS;
	}
	
	//从我的博客页面查看文章详情
	public String myArticleDetail(){
		//获取文章信息
		Article articleDetail = articleService.getEntityById(article.getArticleId());
		ActionContext.getContext().put("articleDetail", articleService.getEntityById(article.getArticleId()));
		
		//对评论进行处理
		ActionContext.getContext().put("articleComments", articleService.handleCommentsForArticle(article.getArticleId()));
				
		//如果不是自己
		if(baseUser != null && !baseUser.getUserId().equals(articleDetail.getUserByAuthorId().getUserId())){
			//阅读数增加一
			articleDetail.setReadnum(articleDetail.getReadnum()+1);
			articleService.updateEntity(articleDetail);
		}
		return SUCCESS;
	}
	
	//获取当前文章处理之后的评论
	public String getArticleComments(){
		//对评论进行处理
		ActionContext.getContext().put("articleComments", articleService.handleCommentsForArticle(article.getArticleId()));
		return SUCCESS;
	}
	
	//ajax获取用户文章
	public String getMyArticlesListByAjax(){
		try {
			setMessage(JsonConvertUtil.returnJson(articleService.findUserArticlesPage(baseUser.getUserId(), pageNumber)));
			setState("0");
		} catch (IOException e) {
			e.printStackTrace();
			setState("2");
			setMessage("出错了!");
		}
		return SUCCESS;
	}
	
	//跳转到博客管理页面的验证
	public String blogManage(){
		if(baseUser != null)
			return SUCCESS;
		return ERROR;
	}
}
