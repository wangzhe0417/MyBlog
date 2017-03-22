package com.jyh.service;

import java.io.Serializable;
import java.util.List;


public interface CommonService<T> {
	
	void saveOrUpdate(T t);

	Serializable saveEntity(T t);
	
	void updateEntity(T t);
	
	void deleteEntityById(Serializable id);
	
	List<T> findAllEntity();
	
	T getEntityById(Serializable id);
 }
