package com.jyh.dao;

import java.util.List;

import com.jyh.domain.Attention;

public interface AttentionDao extends CommonDao<Attention> {

	/**
	 * 获取我关注的用户
	 * @param userId	用户id
	 * @return			我关注的人
	 */
	List<Object[]> findMyAttention(String userId);
	
	/**
	 * 获取关注我的人
	 * @param userId	用户id
	 * @return			关注我的人
	 */
	List<Object[]> findFollowMe(String userId);
	
	/**
	 * 获取互相关注的人
	 * @param userId	用户id
	 * @return			互相关注的
	 */
	List<Object[]> findMutualConcern(String userId);
	
	/**
	 * 根据关注者和被关注者的id获取关系
	 * @param attentionUserId		被关注者id
	 * @param followUserId			关注者id
	 * @return
	 */
	Attention findAttentionId(String attentionUserId, String followUserId);
}
