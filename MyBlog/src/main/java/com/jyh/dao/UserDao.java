package com.jyh.dao;

import java.io.Serializable;
import java.util.List;

import com.jyh.domain.User;

public interface UserDao extends CommonDao<User> {

	/**
	 * 根据用户名获取用户
	 * @param userName 用户名
	 * @return 一个用户
	 */
	User getUserByName(String userName);
	
	/**
	 * 分页获取用户
	 * @param startIndex 	起始下标
	 * @param dataNum		数据个数
	 * @return
	 */
	List<User> findOnePageUsers(int startIndex, int dataNum);
	
	/**
	 * 获取用户数
	 * @return
	 */
	Integer getUsersCount();
	
	/**
	 * 通过用户id获取用户
	 * @param userId
	 * @return
	 */
	User getUserById(Serializable userId);
}
