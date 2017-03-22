package com.jyh.service.impl;

import com.jyh.dao.EducationDao;
import com.jyh.domain.Education;
import com.jyh.service.EducationService;

public class EducationServiceImpl extends CommonServiceImpl<Education> implements EducationService{

	private EducationDao educationDao;

	public EducationDao getEducationDao() {
		return educationDao;
	}

	public void setEducationDao(EducationDao educationDao) {
		this.educationDao = educationDao;
	}
	
}
