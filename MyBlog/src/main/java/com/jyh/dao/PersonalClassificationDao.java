package com.jyh.dao;

import java.util.List;

import com.jyh.domain.PersonalClassification;

public interface PersonalClassificationDao extends
		CommonDao<PersonalClassification> {

	/**
	 * 根据用户id获取用户的分类
	 * @param userId	用户id
	 * @return			返回分类列表
	 */
	List<Object[]> findClassByUserId(String userId);
	
}
