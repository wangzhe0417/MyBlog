package com.jyh.service;

import java.io.Serializable;
import java.util.List;

import com.jyh.domain.User;
import com.jyh.exceptions.NameIsUsedException;
import com.jyh.exceptions.UserException;

public interface UserService extends CommonService<User>{

	/**
	 * 用户注册
	 * @throws Exception 
	 */
	void saveUserRegist(User user) throws NameIsUsedException;
	
	/**
	 * 用户登录
	 */
	User userLogin(User user) throws UserException;
	
	/**
	 * 通过用户名查找用户
	 */
	User getUserByName(String userName);
	
	/**
	 * 获取推荐用户
	 * @param pageNumber 页数
	 * @return
	 */
	List<User> getRecommendUsers();
	
	/**
	 * 通过id用外链接查询用户和用户的学历工作
	 * @param userId
	 * @return
	 */
	User getUserById(Serializable userId);
	
}
