package com.jyh.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.search.SearchException;

import com.jyh.dao.ArticleDao;
import com.jyh.domain.Article;
import com.jyh.domain.Comment;
import com.jyh.service.ArticleService;
import com.jyh.utils.JsonConvertUtil;
import com.jyh.utils.LuceneUtil;
import com.jyh.utils.PageUtil;

public class ArticleServiceImpl extends CommonServiceImpl<Article> implements ArticleService{
	
	private ArticleDao articleDao;

	public ArticleDao getArticleDao() {
		return articleDao;
	}

	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}
	
	public Article getArticleByState(Integer state){
		return articleDao.getArticleByState(state);
	}
	
	@Override
	public List<Map<String, String>> getArticleDateGroup(String userId) {
		
		List<Map<String, String>> resultList = new ArrayList<Map<String,String>>();
		
		StringBuffer sqlStatement = new StringBuffer("select year(a.articleDate),month(a.articleDate),count(*) ");
		sqlStatement.append("from Article as a left join a.userByAuthorId u where u.userId='");
		sqlStatement.append(userId);
		sqlStatement.append("' group by year(a.articleDate),month(a.articleDate) order by year(a.articleDate),month(a.articleDate)");
		
		List<Object[]> fieldList = articleDao.findArticleFieldList(sqlStatement.toString() , 0, 9);
		for (Object[] objects : fieldList) {
			Map<String, String> fieldMap = new HashMap<String, String>();
			fieldMap.put("year", objects[0].toString());
			fieldMap.put("month", objects[1].toString());
			fieldMap.put("articleNumber", objects[2].toString());
			resultList.add(fieldMap);
		}
		return resultList;
	}

	@Override
	public List<Object[]> findArticleByCommentDesc(String userId) {
		StringBuffer sqlStatement = new StringBuffer("select a.articleId,a.articleTitle,(select count(*) from Comment cc where cc.commentArticle.articleId=a.articleId) as commentNum "); 
		sqlStatement.append("from Article a left join a.comments c left join a.userByAuthorId u where u.userId='");
		sqlStatement.append(userId);
		sqlStatement.append("' group by a.articleId order by commentNum desc");
		return articleDao.findArticleFieldList(sqlStatement.toString(), 0, 9);
	}

	public List<Object[]> findArticleByReadNumDesc(String userId) {
		StringBuffer sqlStatement = new StringBuffer("select a.articleId,a.articleTitle,a.readnum from Article a left join a.userByAuthorId u where u.userId='"); 
		sqlStatement.append(userId);
		sqlStatement.append("' order by a.readnum desc");
		return articleDao.findArticleFieldList(sqlStatement.toString(), 0, 9);
	}

	public Map<String, Object> getMyBlogArticleData(String userId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//按照日期分类
		resultMap.put("dateGroups", getArticleDateGroup(userId));
		//按照评论数排序
		resultMap.put("commentDesc", findArticleByCommentDesc(userId));
		//按照阅读数排序
		resultMap.put("readNumDesc", findArticleByReadNumDesc(userId));
		return resultMap;
	}

	/**
	 * 模板方法，用来分页获取文章
	 * @param pageNum		页数
	 * @param contentSql	查询内容列表的sql语句
	 * @param countSql		查询总数的sql语句
	 * @return
	 */
	public PageUtil findArticlesPageCommon(String pageNum,StringBuffer contentSql,StringBuffer countSql){
		int num = 1;
		if(pageNum != null && !"".equals(pageNum))
			num = Integer.parseInt(pageNum);
		int totalRecords = articleDao.getArticleCount(countSql.toString());
		PageUtil page = new PageUtil(num, totalRecords);
		
		List<Object[]> articles = articleDao.findArticleFieldList(contentSql.toString(), page.getStartIndex(),page.getPageRecords());
		page.setRecords(articles);
		return page;
	}

	public PageUtil findArticlesPage(String pageNum) {
		StringBuffer contentSql = new StringBuffer(
				"select a.articleId,a.articleTitle,a.articleSummary,a.articleDate,a.readnum,(select count(*) from Comment cc where cc.commentArticle.articleId=a.articleId) as commentNum,u.userId,u.userImg,u.userNickname");
		contentSql.append(" from Article a left join a.comments left join a.userByAuthorId u");
		contentSql.append(" group by a.articleId order by a.articleDate desc");
		
		StringBuffer countSql = new StringBuffer("select count(*) from Article");
		return findArticlesPageCommon(pageNum, contentSql, countSql);
	}

	public PageUtil findArticlesPageByClass(Integer articleClass, String pageNum) {
		StringBuffer contentSql = new StringBuffer(
				"select a.articleId,a.articleTitle,a.articleSummary,a.articleDate,a.readnum,(select count(*) from Comment cc where cc.commentArticle.articleId=a.articleId) as commentNum,u.userId,u.userImg,u.userNickname");
		contentSql.append(" from Article a left join a.comments left join a.userByAuthorId u where a.classification=");
		contentSql.append(articleClass);
		contentSql.append(" group by a.articleId order by a.articleDate desc");
		
		StringBuffer countSql = new StringBuffer("select count(*) from Article a where a.classification=");
		countSql.append(articleClass);
		return findArticlesPageCommon(pageNum, contentSql, countSql);
	}

	public PageUtil findUserArticlesPage(String userId, String pageNum) {
		StringBuffer contentSql = new StringBuffer("select a.articleId,a.articleTitle,a.articleSummary,a.articleDate,a.readnum,a.type,(select count(*) from Comment cc where cc.commentArticle.articleId=a.articleId) as commentNum,u.userId from Article a left join a.comments left join a.userByAuthorId u where u.userId='");
		contentSql.append(userId);
		contentSql.append("' group by a.articleId order by a.articleDate desc");
		
		StringBuffer countSql = new StringBuffer("select count(*) from Article a left join a.userByAuthorId u where u.userId='");
		countSql.append(userId);
		countSql.append("'");
		return findArticlesPageCommon(pageNum, contentSql, countSql);
	}

	@Override
	public PageUtil findUserArticlesPageByTitle(String userId,
			String articleTitle, String pageNum) {
		StringBuffer contentSql = new StringBuffer("select a.articleId,a.articleTitle,a.articleSummary,a.articleDate,a.readnum,a.type,(select count(*) from Comment cc where cc.commentArticle.articleId=a.articleId) as commentNum,u.userId from Article a left join a.comments left join a.userByAuthorId u where u.userId='");
		contentSql.append(userId);
		contentSql.append("' and a.articleTitle like '%");
		contentSql.append(articleTitle);
		contentSql.append("%' group by a.articleId order by a.articleDate desc");
		
		StringBuffer countSql = new StringBuffer("select count(*) from Article a left join a.userByAuthorId u where u.userId='");
		countSql.append(userId);
		countSql.append("' and a.articleTitle like '%");
		countSql.append(articleTitle);
		countSql.append("%'");
		return findArticlesPageCommon(pageNum, contentSql, countSql);
	}

	@Override
	public PageUtil findUserArticlesPageByClass(String userId,
			String articleClass, String pageNum) {
		StringBuffer contentSql = new StringBuffer("select a.articleId,a.articleTitle,a.articleSummary,a.articleDate,a.readnum,a.type,(select count(*) from Comment cc where cc.commentArticle.articleId=a.articleId) as commentNum,u.userId ");
		contentSql.append("from Article a left join a.articleClasses c left join c.personalClassification p left join a.comments left join a.userByAuthorId u where u.userId='");
		contentSql.append(userId);
		contentSql.append("' and p.classificationId='");
		contentSql.append(articleClass);
		contentSql.append("' group by a.articleId order by a.articleDate desc");
		
		StringBuffer countSql = new StringBuffer("select count(*) from Article a left join a.articleClasses c left join c.personalClassification p left join a.userByAuthorId u where u.userId='");
		countSql.append(userId);
		countSql.append("' and p.classificationId='");
		countSql.append(articleClass);
		countSql.append("'");
		return findArticlesPageCommon(pageNum, contentSql, countSql);
	}

	@Override
	public PageUtil findUserArticlesGroupByDate(String userId,
			String dateClass, String pageNum) {
		String[] date = dateClass.split("-");
		StringBuffer contentSql = new StringBuffer("select a.articleId,a.articleTitle,a.articleSummary,a.articleDate,a.readnum,a.type,(select count(*) from Comment cc where cc.commentArticle.articleId=a.articleId) as commentNum,u.userId ");
		contentSql.append("from Article a left join a.comments left join a.userByAuthorId u where u.userId='");
		contentSql.append(userId);
		contentSql.append("' and year(a.articleDate)=");
		contentSql.append(Integer.parseInt(date[0]));
		contentSql.append(" and month(a.articleDate)=");
		contentSql.append(Integer.parseInt(date[1]));
		contentSql.append(" group by a.articleId order by a.articleDate desc");
		
		StringBuffer countSql = new StringBuffer("select count(*) from Article a left join a.userByAuthorId u where u.userId='");
		countSql.append(userId);
		countSql.append("' and year(a.articleDate)=");
		countSql.append(Integer.parseInt(date[0]));
		countSql.append(" and month(a.articleDate)=");
		countSql.append(Integer.parseInt(date[1]));
		return findArticlesPageCommon(pageNum, contentSql, countSql);
	}

	@SuppressWarnings("unchecked")
	public Map<Comment, List<Comment>> handleCommentsForArticle(String articleId) {
		Set<Comment> comments = articleDao.getById(articleId).getComments();
		List<Comment> firstComments = new ArrayList<>();
		Map<Integer, List<Comment>> cMap = new LinkedHashMap<Integer, List<Comment>>();
		for (Comment comment : comments) {//遍历评论
			if(comment.getUserByReplyUser() == null){	//如果没有回复者则表示是第一层评论
				firstComments.add(comment);				//将评论放入一个集合中去
			}else if(!cMap.containsKey(comment.getFloor())){
				//否则就是第二层评论，如果在map集合中找不到该层，则添加该层，并将当前评论加进去
				List<Comment> cs = new ArrayList<>();
				cs.add(comment);
				cMap.put(comment.getFloor(), cs);
			}else{//如果找到了该层，则直接将当前评论加进去
				cMap.get(comment.getFloor()).add(comment);
			}
		}
		//将两组整合到一组
		Map<Comment, List<Comment>> myComments = new LinkedHashMap<>();
		for (Comment comment : firstComments) {
			myComments.put(comment, cMap.get(comment.getFloor()));
		}
		return myComments;
	}

}
