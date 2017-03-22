package com.jyh.service.impl;

import java.io.Serializable;
import java.util.List;

import com.jyh.dao.CommonDao;
import com.jyh.service.CommonService;

@SuppressWarnings("all")
public class CommonServiceImpl<T> implements CommonService<T> {

	private CommonDao<T> commonDao;
	
	public CommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	@Override
	public Serializable saveEntity(T t) {
		return commonDao.save(t);
	}

	@Override
	public void updateEntity(T t) {
		commonDao.update(t);
	}

	@Override
	public void deleteEntityById(Serializable id) {
		commonDao.deleteById(id);
	}

	@Override
	public List<T> findAllEntity() {
		return commonDao.findAll();
	}

	@Override
	public T getEntityById(Serializable id) {
		return (T) commonDao.getById(id);
	}

	@Override
	public void saveOrUpdate(T t) {
		commonDao.saveOrUpdate(t);
	}

}
