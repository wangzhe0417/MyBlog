package com.jyh.dao;

import java.util.List;

import com.jyh.domain.Dynamic;

public interface DynamicDao extends CommonDao<Dynamic> {
	
	/**
	 * 获取我自己的动态
	 * @param userId	用户id
	 * @return			动态列表
	 */
	List<Object[]> findMyDynamic(String userId);
	
	/**
	 * 获取我关注的动态
	 * @param userId	用户id
	 * @return			动态列表
	 */
	List<Object[]> findMyAttentionDynamic(String userId);
}
