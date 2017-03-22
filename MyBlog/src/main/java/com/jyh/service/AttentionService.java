package com.jyh.service;

import java.io.IOException;
import java.util.List;

import com.jyh.dao.AttentionDao;
import com.jyh.domain.Attention;

public interface AttentionService extends CommonService<Attention>{

	/**
	 * 获取我关注的用户
	 * @param userId	用户id
	 * @return			我关注的人
	 */
	String findMyAttention(String userId) throws IOException;
	
	/**
	 * 获取关注我的人
	 * @param userId	用户id
	 * @return			关注我的人
	 */
	String findFollowMe(String userId) throws IOException;
	
	/**
	 * 获取互相关注的人
	 * @param userId	用户id
	 * @return			互相关注的
	 */
	String findMutualConcern(String userId) throws IOException;
	
	/**
	 * 判断两个用户之间有没有关注
	 * @param attentionUserId		被关注者id
	 * @param followUserId			关注者id
	 * @return
	 */
	String findAttentionId(String attentionUserId, String followUserId);
	
}
