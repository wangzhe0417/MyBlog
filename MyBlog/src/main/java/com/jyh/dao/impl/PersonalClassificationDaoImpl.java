package com.jyh.dao.impl;


import java.util.List;

import com.jyh.dao.PersonalClassificationDao;
import com.jyh.domain.PersonalClassification;

public class PersonalClassificationDaoImpl extends
		CommonDaoImpl<PersonalClassification> implements
		PersonalClassificationDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findClassByUserId(String userId) {
		return (List<Object[]>)getHibernateTemplate().find(
				"select classificationId,classificationName,articleClasses.size as articleCount from PersonalClassification p where p.user.userId = ?", userId);
	}

}
