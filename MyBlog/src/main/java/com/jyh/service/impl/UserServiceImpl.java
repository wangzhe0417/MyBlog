package com.jyh.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.jyh.dao.UserDao;
import com.jyh.domain.Education;
import com.jyh.domain.User;
import com.jyh.domain.Work;
import com.jyh.exceptions.NameIsUsedException;
import com.jyh.exceptions.PasswordIsError;
import com.jyh.exceptions.UserException;
import com.jyh.exceptions.UserIsNotFound;
import com.jyh.service.UserService;
import com.jyh.utils.MD5Util;

public class UserServiceImpl extends CommonServiceImpl<User> implements UserService {
	private UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void saveUserRegist(User user) throws NameIsUsedException{
		User u = userDao.getUserByName(user.getUserName());
		if(u != null)
			throw new NameIsUsedException("该用户名已存在!");
		user.setPassword(MD5Util.md5Encod(user.getPassword()));	
		userDao.save(user);
	}

	public User userLogin(User user) throws UserException {
		User u = userDao.getUserByName(user.getUserName());
		if(u == null)
			throw new UserIsNotFound("该用户不存在!");
		String pass = MD5Util.md5Encod(user.getPassword());
		if(!pass.equals(u.getPassword()))
			throw new PasswordIsError("密码错误!");
		return u;
	}

	public User getUserByName(String userName) {
		return userDao.getUserByName(userName);
	}

	public List<User> getRecommendUsers() {
		List<User> users = userDao.findOnePageUsers(0,9);
		return users;
	}
	
	public User getUserById(Serializable userId){
		User user = userDao.getUserById(userId);
		return user;
	}

}
