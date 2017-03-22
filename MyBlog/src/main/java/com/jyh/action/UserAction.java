package com.jyh.action;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;

import org.apache.struts2.ServletActionContext;

import com.jyh.domain.Education;
import com.jyh.domain.LoginLog;
import com.jyh.domain.User;
import com.jyh.domain.Work;
import com.jyh.exceptions.MyEmailException;
import com.jyh.exceptions.UserException;
import com.jyh.service.ArticleService;
import com.jyh.service.AttentionService;
import com.jyh.service.EducationService;
import com.jyh.service.LoginLogService;
import com.jyh.service.UserService;
import com.jyh.service.WorkService;
import com.jyh.utils.GetIpUtil;
import com.jyh.utils.JsonConvertUtil;
import com.jyh.utils.MD5Util;
import com.jyh.utils.MyValidateUtil;
import com.jyh.utils.SendMailUtil;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
public class UserAction extends MyBaseAction {
	private UserService userService;
	private LoginLogService loginLogService;
	private ArticleService articleService;
	private User user;										//用户对象，用来接收参数
	private String validateCode;							//验证码
	private String remember;								//是否记住用户
	private Education education;
	private EducationService educationService;
	private Work work;
	private WorkService workService;
	private String attentionId;								//用来判断访问者有没有关注被访问者
	private AttentionService attentionService;

	public String getRemember() {
		return remember;
	}
	public void setRemember(String remember) {
		this.remember = remember;
	}
	public String getValidateCode() {
		return validateCode;
	}
	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
	public ArticleService getArticleService() {
		return articleService;
	}
	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public LoginLogService getLoginLogService() {
		return loginLogService;
	}
	public void setLoginLogService(LoginLogService loginLogService) {
		this.loginLogService = loginLogService;
	}
	public Education getEducation() {
		return education;
	}
	public void setEducation(Education education) {
		this.education = education;
	}
	public EducationService getEducationService() {
		return educationService;
	}
	public void setEducationService(EducationService educationService) {
		this.educationService = educationService;
	}
	public Work getWork() {
		return work;
	}
	public void setWork(Work work) {
		this.work = work;
	}
	public WorkService getWorkService() {
		return workService;
	}
	public void setWorkService(WorkService workService) {
		this.workService = workService;
	}
	public String getAttentionId() {
		return attentionId;
	}
	public void setAttentionId(String attentionId) {
		this.attentionId = attentionId;
	}
	public AttentionService getAttentionService() {
		return attentionService;
	}
	public void setAttentionService(AttentionService attentionService) {
		this.attentionService = attentionService;
	}
	
	
	

	//退出
	public String quit(){
		ServletActionContext.getContext().getSession().remove("user");
		return SUCCESS;
	}

	//用户登录
	public String userLogin(){
		if(haveError("用户登录失败!"))
			return LOGIN;
		User u;
		try {
			u = userService.userLogin(user);
			
			String path = ServletActionContext.getRequest().getServletContext().getContextPath();
			Cookie userName = new Cookie("userName", "");
			userName.setMaxAge(60*60*24*7);
			userName.setPath(path);
			Cookie password = new Cookie("password", "");
			password.setMaxAge(60*60*24*7);
			password.setPath(path);
			
			if("on".equals(remember)){
				userName.setValue(user.getUserName());
				password.setValue(user.getPassword());
				
			}
			ServletActionContext.getResponse().addCookie(userName);
			ServletActionContext.getResponse().addCookie(password);
			
			setState("0");
			
			loginLog(u);
			
			ActionContext.getContext().getSession().put("user", u);
		} catch (UserException e) {
			setState("2");
			setMessage(e.getMessage());
		}
		return LOGIN;
	}

	//用户注册
	public String userRegist() {
		if(haveError("用户注册失败!"))
			return LOGIN;
		try {
			user.setUserNickname(user.getUserName());
			user.setVisitedNum(0);
			user.setBirthday(new Date());
			user.setUserImg("blog/images/defaultUser.jpg");
			userService.saveUserRegist(user);
			setState("0");
		} catch (Exception e) {
			setState("2");
			setMessage(e.getMessage());
		}
		//errorMessage.getErrors().clear();
		return LOGIN;
	}
	
	//发送邮件
	public String sendMail() {
		if(haveError("邮件发送失败!"))
			return LOGIN;
		String code = MyValidateUtil.getSimpleCode();						//生成验证码
		try{
			SendMailUtil.sendMail(user.getEmail(),"验证码",code);			//将验证码通过邮件发送
			setState("0");
			ActionContext.getContext().getSession().put("code", code);		//将验证码保存在session中去
		}catch (MyEmailException e) {
			setState("2");
			setMessage(e.getMessage());
		}
		//errorMessage.getErrors().clear();
		return LOGIN;
	}
	
	//验证用户名是否已经被注册
	public String nameRepeat(){
		String userName = user.getUserName();
		System.out.println(userName);
		if(userService.getUserByName(userName) != null)
			setState("1");
		return LOGIN;
	}
	
	//添加登录日志
	public void loginLog(User user){
		LoginLog loginLog = new LoginLog();
		loginLog.setUser(user);
		loginLog.setLoginUsername(user.getUserName());
		loginLog.setLoginDate(Calendar.getInstance().getTime());
		loginLog.setLoginIp(GetIpUtil.getIpAddr(ServletActionContext.getRequest()));
		loginLogService.saveEntity(loginLog);
	}
	
	//进入个人中心
	public String personalInfo(){
		if(user == null || user.getUserId().isEmpty()){
			return "error";
		}
		try {
			user = userService.getUserById(user.getUserId());
			//将访问的用户保存在session中去
			ServletActionContext.getContext().getSession().put("visitedUser", user);
			
			setMessage(JsonConvertUtil.returnJson(user));
			if(baseUser != null)
				setAttentionId(attentionService.findAttentionId(user.getUserId(), baseUser.getUserId()));
			else
				setAttentionId("1");
			setState("0");
		} catch (Exception e) {
			return "error";
		}
		return "personal";
	};
	
	/**
	 * 编辑保存用户
	 */
	public String saveUser(){
		userService.updateEntity(user);
		setState("0");
		setMessage("保存成功!");
		return "personal";
	};
	/**
	 * 保存学历
	 */
	public String saveEducation(){
		if(education != null){
			education.setUser(baseUser);
			if(education.getEducationId() == null || education.getEducationId().isEmpty())
				educationService.saveEntity(education);
			else
				educationService.updateEntity(education);
			setState("0");
			setMessage("保存学历背景成功!");
		}else{
			setState("2");
			setMessage("保存学历背景失败!");
		}
		return "personal";
	};
	
	/**
	 * 删除学历背景
	 */
	public String deleteEducation(){
		if(education != null && education.getEducationId() != null){
			educationService.deleteEntityById(education.getEducationId());
			setState("0");
			setMessage("删除成功!");
		}else{
			setState("0");
			setMessage("删除成功!");
		}
		return "personal";
	}
	/**
	 * 保存工作经历
	 */
	public String saveWork(){
		if(work != null){
			work.setUser(baseUser);
			if("".equals(work.getWorkId()) || work.getWorkId() == null)
				workService.saveEntity(work);
			else
				workService.updateEntity(work);
			setState("0");
			setMessage("保存工作经历成功!");
		}else{
			setState("2");
			setMessage("保存工作经历失败!");
		}
		return "personal";
	};
	/**
	 * 删除工作经历
	 */
	public String deleteWork(){
		if(work != null && work.getWorkId() != null){
			workService.deleteEntityById(work.getWorkId());
			setState("0");
			setMessage("删除成功!");
		}else{
			setState("0");
			setMessage("删除成功!");
		}
		return "personal";
	};
	/**
	 * 修改账号
	 */
	public String updateAccount(){
		if(baseUser == null){
			setState("3");
			setMessage("loginView.action");
			return "account";
		}
		if(user == null){
			setState("2");
			setMessage("不能为空");
			return "account";
		}
		baseUser.setUserName(user.getUserName());
		baseUser.setPassword(MD5Util.md5Encod(user.getPassword()));
		userService.updateEntity(baseUser);
		setState("0");
		setMessage("修改成功!");
		return "account";
	};
	
	
	
	/**验证方法 **/
	
	
	//用户登录验证
	public void validateUserLogin(){
		if("".equals(user.getUserName())){
			errorMessage.put("user.userName", "用户名不能为空");
		}
		if("".equals(user.getPassword())){
			errorMessage.put("user.password", "密码不能为空");
		}
	}
	
	//用户注册验证
	public void validateUserRegist(){
		if(user.getUserName().isEmpty()){
			errorMessage.put("user.userName", "用户名不能为空");
		}
		if(user.getPassword().isEmpty()){
			errorMessage.put("user.password", "密码不能为空");
		}
		validateSendMail();
		if(validateCode.isEmpty() || validateCode == null){
			errorMessage.put("validateCode", "验证码不能为空");
		}else if(!validateCode.equals(ActionContext.getContext().getSession().get("code"))){
			errorMessage.put("validateCode", "验证码不正确");
		}
	}
	
	//发送邮件验证
	public void validateSendMail(){
		if(user.getEmail().isEmpty()){
			errorMessage.put("user.email", "邮箱不能为空");
		}
		
		String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(user.getEmail());
		
		if(!matcher.matches()){
			errorMessage.put("user.email", "邮箱格式不正确");
		}
	}

}
