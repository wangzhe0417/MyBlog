package com.jyh.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.Inet6Address;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.enterprise.inject.New;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jyh.dao.AttentionDao;
import com.jyh.dao.CommonDao;
import com.jyh.dao.UserDao;
import com.jyh.dao.impl.CommonDaoImpl;
import com.jyh.domain.Article;
import com.jyh.domain.ArticleClass;
import com.jyh.domain.Comment;
import com.jyh.domain.Dynamic;
import com.jyh.domain.Education;
import com.jyh.domain.LoginLog;
import com.jyh.domain.PersonalClassification;
import com.jyh.domain.User;
import com.jyh.exceptions.PasswordIsError;
import com.jyh.exceptions.UserException;
import com.jyh.service.ArticleService;
import com.jyh.service.CollectionService;
import com.jyh.service.CommentService;
import com.jyh.service.DynamicService;
import com.jyh.service.LoginLogService;
import com.jyh.service.PersonalClassificationService;
import com.jyh.service.UserService;
import com.jyh.utils.JsonConvertUtil;
import com.jyh.utils.LuceneUtil;
import com.jyh.utils.PageUtil;
import com.jyh.utils.SendMailUtil;
import com.mysql.cj.x.json.JsonArray;

@SuppressWarnings("resource")
public class MyTest {

	@Test
	public void test1(){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("spring/applicationContext.xml");
		UserDao userDao = (UserDao)context.getBean("userDao");
		User user = new User();
		System.out.println(userDao.findOnePageUsers(1, 2));
		//System.out.println(userDao.getUsersCount());
//		user.setUserName("dddd");
//		user.setPassword("dddd");
//		try {
//			User user2 = userService.userLogin(user);
//			System.out.println(user2);
//			Set<PersonalClassification> clSet = new HashSet<>();
//			clSet = user2.getPersonalClassifications();
//			for (PersonalClassification p : clSet) {
//				System.out.println(p.getClassificationName());
//				System.out.println(p.getClassificationId());
//			}
//		} catch (UserException e1) {
//			System.out.println(e1.getMessage());
//		}
	}
	
	@Test
	public void emailTest(){
//		 String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";    
//		 Pattern regex = Pattern.compile(check);    
//		 Matcher matcher = regex.matcher("dd");    
//		 boolean isMatched = matcher.matches();    
//		 System.out.println(isMatched);   
		SendMailUtil.sendMail("15551209682@163.com", "验证码", "d41UbW");
	}
//	
	
	@Test
	public void md5Test() throws NoSuchAlgorithmException, UnsupportedEncodingException{
		
		String str = "dddd";
		try {
	        // 生成一个MD5加密计算摘要
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        // 计算md5函数
	        md.update(str.getBytes());
	        // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
	        // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
	        System.out.println(new BigInteger(1, md.digest()).toString(16));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
//	public void exe() throws UserException{
//		if(0 == 0)
//			throw new PasswordIsError("不对");
//	}
//	
//	@Test
//	public void getUserByName(){
//		try {
//			exe();
//		} catch (UserException e) {
//			System.out.println(e.getMessage());
//		}
//	}
	
	@Test
	public void dateTest() throws ParseException{
		
		Date currentTime = new Date();
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   String dateString = formatter.format(currentTime);
	   
	   System.out.println(dateString);
	   
	   Calendar calendar = Calendar.getInstance();
	   System.out.println(calendar.getTime());
	   
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testManyToMany(){
		//System.setProperty("user.timezone","GMT +8:00");
//		System.out.println(TimeZone.getDefault());
		
		
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("spring/applicationContext.xml");
		CommentService commentService = (CommentService)context.getBean("commentService");
		List<Comment> comments = commentService.findCommentsByArticle("402881f25a82814c015a83038b070002");
		System.out.println(comments.size());
	}
	@Test
	public void test3(){
		System.out.println(Calendar.getInstance().getTime());
		System.out.println(Calendar.getInstance().getTimeZone().getDefault());
	}
	
	@Test
	public void test4(){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("spring/applicationContext.xml");
		ArticleService articleService = (ArticleService)context.getBean("articleService");
		List<Article> articles = articleService.findAllEntity();
		for (Article article : articles) {
			try {
				LuceneUtil.add(article);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void test5(){
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("spring/applicationContext.xml");
		PersonalClassificationService pService = (PersonalClassificationService)context.getBean("personalClassificationService");
		try {
			System.out.println(pService.findUserClass("111"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test6(){
		String s = "hhhh.aaaa.dddd";
		s = s.substring(s.lastIndexOf("."), s.length());
		System.out.println(s);
//		for (String string : ss) {
//			System.out.println(string);
//		}
	}
	
}
