package com.jyh.service;

import java.io.IOException;
import java.util.List;

import com.jyh.domain.Dynamic;

public interface DynamicService extends CommonService<Dynamic>{

	/**
	 * 获取我自己的动态
	 * @param userId	用户id
	 * @return			动态列表的json数据
	 */
	String findMyDynamic(String userId) throws IOException;
	
	/**
	 * 获取我关注的动态
	 * @param userId	用户id
	 * @return			动态列表
	 */
	String findMyAttentionDynamic(String userId) throws IOException;
}
