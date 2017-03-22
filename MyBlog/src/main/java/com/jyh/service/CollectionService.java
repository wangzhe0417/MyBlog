package com.jyh.service;

import java.io.IOException;

import com.jyh.domain.Collection;
import com.jyh.utils.PageUtil;

public interface CollectionService extends CommonService<Collection> {

	/**
	 * 根据用户id分页获取文章收藏
	 * @param pageNum
	 * @param userId
	 * @return
	 */
	String findCollectionsPage(String pageNum,String userId) throws IOException;
}
