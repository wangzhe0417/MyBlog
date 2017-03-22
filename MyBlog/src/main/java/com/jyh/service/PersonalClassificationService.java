package com.jyh.service;

import java.io.IOException;

import com.jyh.domain.PersonalClassification;

public interface PersonalClassificationService extends CommonService<PersonalClassification>{

	/**
	 * 根据用户id获取用户的分类
	 * @param userId	用户id
	 * @return			分类列表
	 */
	String findUserClass(String userId) throws IOException;
	
	/**
	 * 保存我的分类
	 * @param pClassification
	 */
	void saveUserClass(PersonalClassification pClassification);
}
