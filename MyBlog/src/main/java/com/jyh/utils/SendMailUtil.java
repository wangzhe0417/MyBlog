package com.jyh.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.jyh.exceptions.MyEmailException;

public class SendMailUtil{
	
	private static JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	
	static{
		mailSender.setHost("smtp.qq.com");
		mailSender.setPort(465);
		mailSender.setUsername("xiaohaosun@foxmail.com");
		mailSender.setPassword("lpsxmhgdzpmrbbga");
		
		InputStream input;
		Properties props = new Properties();
		try {
			input = new FileInputStream(SendMailUtil.class.getClassLoader().getResource("mail.properties").getPath());
			props.load(input);
		} catch (IOException e) {
			new RuntimeException("邮件配置文件不存在!");
		}
		mailSender.setJavaMailProperties(props);
	}

	public static void sendMail(String to, String subject, String text) throws MailException{
		
		if(to == null || "".equals(to))
			throw new MyEmailException("接受者不能为空");
		
		String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(to);
		
		if(!matcher.matches())
			throw new MyEmailException("邮箱格式不正确");
		if(subject == null || "".equals(subject))
			throw new MyEmailException("邮件标题不能为空");
		if(text == null || "".equals(text))
			throw new MyEmailException("邮件内容不能为空");
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("xiaohaosun@foxmail.com");
		mailMessage.setTo(to);
		mailMessage.setSubject(subject);
		mailMessage.setText(text);
		
		mailSender.send(mailMessage);
		System.out.println("code:" + text);
	}
}