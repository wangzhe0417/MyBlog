package com.jyh.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.jyh.dao.CommonDao;
import com.jyh.domain.Article;
import com.jyh.exceptions.SQLStatementIsErrorException;
import com.jyh.utils.PageHibernateCallBackUtil;

@SuppressWarnings("all")
public class CommonDaoImpl<T> implements CommonDao<T> {

	private Class<?> classt;
	private HibernateTemplate hibernateTemplate;
	
	public CommonDaoImpl() {
		ParameterizedType type = (ParameterizedType)this.getClass().getGenericSuperclass();
		this.classt = (Class<?>) type.getActualTypeArguments()[0];
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public T getById(Serializable id) {
		return (T) hibernateTemplate.get(this.classt, id);
	}

	@Override
	public List<T> findAll() {
		return (List<T>) hibernateTemplate.find("from " + classt.getName());
	}

	@Override
	public void deleteById(Serializable id) {
		T t = (T) hibernateTemplate.get(classt, id);
		hibernateTemplate.delete(t);
	}

	@Override
	public void update(T t) {
		hibernateTemplate.update(t);
	}

	@Override
	public Serializable save(T t) {
		return hibernateTemplate.save(t);
	}

	@Override
	public void saveOrUpdate(T t) {
		hibernateTemplate.saveOrUpdate(t);
	}

	@Override
	public List<T> findAllEntityByCondition(String condition, int startIndex, int dataNumber) {
		List<T> dataList = (List<T>)getHibernateTemplate().execute((HibernateCallback<T>) new PageHibernateCallBackUtil(
				condition, startIndex, dataNumber, null));
		return dataList;
	}
	
}
