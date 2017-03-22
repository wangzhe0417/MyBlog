package com.jyh.dao;

import java.util.List;

import com.jyh.domain.Collection;

public interface CollectionDao extends CommonDao<Collection> {

	/**
	 * 分页获取收藏
	 * @param startIndex
	 * @param num
	 * @return
	 */
	 List<Object[]> findSomeCollections(int startIndex, int num, String userId);
	 
	 /**
	  * 获取收藏文章数目
	  * @param userId
	  * @return
	  */
	 Integer getCollectionCount(String userId);
}
