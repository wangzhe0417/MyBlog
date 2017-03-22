package com.jyh.dao;

import java.util.List;

import com.jyh.domain.Letter;

public interface LetterDao extends CommonDao<Letter>{

	/**
	 * 通过发送者的用户id获取私信
	 * @param userId	用户id
	 * @return
	 */
	List<Object[]> findLettersBySendUserId(String userId);
	
	/**
	 * 通过接受者的用户id获取私信
	 * @param userId	用户id
	 * @return
	 */
	List<Object[]> findLettersByReceiveUserId(String userId);
}
