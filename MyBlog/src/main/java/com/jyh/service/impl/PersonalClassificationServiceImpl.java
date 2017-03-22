package com.jyh.service.impl;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.jyh.dao.PersonalClassificationDao;
import com.jyh.domain.PersonalClassification;
import com.jyh.service.PersonalClassificationService;
import com.jyh.utils.JsonConvertUtil;

public class PersonalClassificationServiceImpl extends CommonServiceImpl<PersonalClassification> 
		implements PersonalClassificationService{

	private PersonalClassificationDao personalClassificationDao;
	
	public PersonalClassificationDao getPersonalClassificationDao() {
		return personalClassificationDao;
	}

	public void setPersonalClassificationDao(
			PersonalClassificationDao personalClassificationDao) {
		this.personalClassificationDao = personalClassificationDao;
	}

	@Override
	public String findUserClass(String userId) throws IOException {
		List<Object[]> personcls = personalClassificationDao.findClassByUserId(userId);
		return JsonConvertUtil.returnJson(personcls);
	}

	@Override
	public void saveUserClass(PersonalClassification pClassification) {
		PersonalClassification p = personalClassificationDao.getById(pClassification.getClassificationId());
		p.setClassificationName(pClassification.getClassificationName());
		personalClassificationDao.update(p);
	}

}
