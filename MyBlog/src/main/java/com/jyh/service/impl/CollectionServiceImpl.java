package com.jyh.service.impl;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.jyh.dao.CollectionDao;
import com.jyh.domain.Collection;
import com.jyh.service.CollectionService;
import com.jyh.utils.JsonConvertUtil;
import com.jyh.utils.PageUtil;

public class CollectionServiceImpl extends CommonServiceImpl<Collection> implements
		CollectionService {
	
	private CollectionDao collectionDao;
	
	public CollectionDao getCollectionDao() {
		return collectionDao;
	}

	public void setCollectionDao(CollectionDao collectionDao) {
		this.collectionDao = collectionDao;
	}


	public String findCollectionsPage(String pageNum,String userId) throws IOException{
		int num = 1;
		if(pageNum != null && !"".equals(pageNum))
			num = Integer.parseInt(pageNum);
		int totalRecords = collectionDao.getCollectionCount(userId);
		PageUtil page = new PageUtil(num, totalRecords);
		
		List<Object[]> collections = collectionDao.findSomeCollections(page.getStartIndex(),page.getPageRecords(),userId);
		page.setRecords(collections);
		String json = JsonConvertUtil.returnJson(page);
		return json;
	}
}
