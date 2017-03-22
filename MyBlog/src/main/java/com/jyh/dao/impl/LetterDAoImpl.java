package com.jyh.dao.impl;


import java.util.List;

import com.jyh.dao.LetterDao;
import com.jyh.domain.Letter;

@SuppressWarnings("all")
public class LetterDAoImpl extends CommonDaoImpl<Letter> implements LetterDao {

	@Override
	public List<Object[]> findLettersBySendUserId(String userId) {
		return (List<Object[]>)getHibernateTemplate().find(
				"select l.letterId,l.letterContent,r.userId,r.userNickname,l.sendDate from Letter l left join l.userByReceiveUser r left join l.userBySendUser s where s.userId = ? order by l.sendDate desc", userId);
	}

	@Override
	public List<Object[]> findLettersByReceiveUserId(String userId) {
		return (List<Object[]>)getHibernateTemplate().find(
				"select l.letterId,l.letterContent,s.userId,s.userNickname,s.userImg,l.sendDate from Letter l left join l.userByReceiveUser r left join l.userBySendUser s where r.userId = ? order by l.sendDate desc", userId);
	}


}
