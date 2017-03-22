package com.jyh.service.impl;


import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.jyh.dao.LetterDao;
import com.jyh.domain.Letter;
import com.jyh.service.LetterService;
import com.jyh.utils.JsonConvertUtil;

public class LetterServiceImpl extends CommonServiceImpl<Letter> implements
		LetterService {
	private LetterDao letterDao;

	public LetterDao getLetterDao() {
		return letterDao;
	}

	public void setLetterDao(LetterDao letterDao) {
		this.letterDao = letterDao;
	}

	@Override
	public String findSendLetter(String userId) throws IOException {
		String json = JsonConvertUtil.returnJson(letterDao.findLettersBySendUserId(userId));
		return json;
	}

	@Override
	public String findReceiveLetter(String userId) throws IOException {
		String json = JsonConvertUtil.returnJson(letterDao.findLettersByReceiveUserId(userId));
		return json;
	}
	

}
