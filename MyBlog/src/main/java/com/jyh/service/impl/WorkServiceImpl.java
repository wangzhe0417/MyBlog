package com.jyh.service.impl;

import com.jyh.dao.WorkDao;
import com.jyh.domain.Work;
import com.jyh.service.WorkService;

public class WorkServiceImpl extends CommonServiceImpl<Work> implements WorkService{
	private WorkDao workDao;

	public WorkDao getWorkDao() {
		return workDao;
	}

	public void setWorkDao(WorkDao workDao) {
		this.workDao = workDao;
	}
	
}
