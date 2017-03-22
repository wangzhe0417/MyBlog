package com.jyh.service;

import java.io.IOException;

import com.jyh.domain.Letter;

public interface LetterService extends CommonService<Letter>{

	/**
	 * 获取用户发送的私信
	 * @param userId	用户id
	 * @return
	 */
	String findSendLetter(String userId) throws IOException;
	
	/**
	 * 获取用户接收的私信
	 * @param userId	用户id
	 * @return
	 */
	String findReceiveLetter(String userId) throws IOException;
}
