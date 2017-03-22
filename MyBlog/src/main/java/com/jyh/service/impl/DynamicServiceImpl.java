package com.jyh.service.impl;


import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.jyh.dao.DynamicDao;
import com.jyh.domain.Dynamic;
import com.jyh.service.DynamicService;
import com.jyh.utils.JsonConvertUtil;

public class DynamicServiceImpl extends CommonServiceImpl<Dynamic> implements
		DynamicService {
	private DynamicDao dynamicDao;

	public DynamicDao getDynamicDao() {
		return dynamicDao;
	}

	public void setDynamicDao(DynamicDao dynamicDao) {
		this.dynamicDao = dynamicDao;
	}

	@Override
	public String findMyDynamic(String userId) throws IOException {
		List<Object[]> objects = dynamicDao.findMyDynamic(userId);
		String json = JsonConvertUtil.returnJson(objects);
		return json;
	}

	@Override
	public String findMyAttentionDynamic(String userId) throws IOException {
		List<Object[]> objects = dynamicDao.findMyAttentionDynamic(userId);
		String json = JsonConvertUtil.returnJson(objects);
		return json;
	}
	

}
