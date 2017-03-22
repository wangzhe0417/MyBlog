package com.jyh.dao.impl;

import java.util.List;

import com.jyh.dao.AttentionDao;
import com.jyh.domain.Attention;

public class AttentionDaoImpl extends CommonDaoImpl<Attention> implements AttentionDao{

	@Override
	public List<Object[]> findMyAttention(String userId) {
		return (List<Object[]>)getHibernateTemplate().find("select a.attentionId,u.userId,u.userNickname,u.userImg from Attention a left join a.userByUser u left join a.userByFollower f where f.userId = ? order by u.userNickname", userId);
		
	}

	@Override
	public List<Object[]> findFollowMe(String userId) {
		return (List<Object[]>)getHibernateTemplate().find("select a.attentionId,f.userId,f.userNickname,f.userImg from Attention a left join a.userByUser u left join a.userByFollower f where u.userId = ? order by f.userNickname", userId);
	}

	@Override
	public List<Object[]> findMutualConcern(String userId) {
		return (List<Object[]>)getHibernateTemplate().find("select mua.attentionId,mu.userId,mu.userNickname,mu.userImg from User m left join m.attentionsForFollower ma left join ma.userByUser mu left join mu.attentionsForFollower mua left join mua.userByUser muu where m.userId = ? and muu.userId = ?", userId,userId);
	}

	@Override
	public Attention findAttentionId(String attentionUserId, String followUserId) {
		List<Attention> attentions = (List<Attention>)getHibernateTemplate().find("from Attention where userByUser.userId = ? and userByFollower.userId = ?", attentionUserId,followUserId);
		if(attentions != null && attentions.size() > 0)
			return attentions.get(0);
		return null;
	}

}
