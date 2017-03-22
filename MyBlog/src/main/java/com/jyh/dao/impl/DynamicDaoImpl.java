package com.jyh.dao.impl;


import java.util.List;

import com.jyh.dao.DynamicDao;
import com.jyh.domain.Dynamic;

@SuppressWarnings("all")
public class DynamicDaoImpl extends CommonDaoImpl<Dynamic> implements DynamicDao {

	@Override
	public List<Object[]> findMyDynamic(String userId) {
		
		return (List<Object[]>)getHibernateTemplate().find(
				"select u.userId,u.userNickname,a.articleId,a.articleTitle,d.dynamicDate,d.dynamicState from Dynamic d "
				+ "left join d.user u left join d.article a where u.userId = ? order by d.dynamicDate desc", userId);
	}


	@Override
	public List<Object[]> findMyAttentionDynamic(String userId) {
		return (List<Object[]>)getHibernateTemplate().find(
				"select u.userId,u.userNickname,a.articleId,a.articleTitle,d.dynamicDate,d.dynamicState from Dynamic d "
				+"inner join d.article a inner join d.user u inner join u.attentionsForUser at where at.userByFollower.userId = ? order by d.dynamicDate desc", userId);
	}


}
