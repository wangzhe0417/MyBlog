package com.jyh.dao;

import java.io.Serializable;
import java.util.List;

import com.jyh.exceptions.SQLStatementIsErrorException;

public interface CommonDao<T> {
	
	/**
	 * 通过id获取数据
	 * @param id
	 * @return
	 */
	T getById(Serializable id);

	/**
	 * 无条件查询所有数据
	 * @return
	 */
	List<T> findAll();
	
	/**
	 * 有条件查询多条数据
	 * @param condition 	查询条件
	 * @param startIndex 	起始下标
	 * @param dataNumber 	数据数目
	 * @return
	 */
	List<T> findAllEntityByCondition(String condition, int startIndex, int dataNumber);
	
	/**
	 * 根据id删除数据
	 * @param id
	 */
	void deleteById(Serializable id);
	
	/**
	 * 删除数据
	 * @param t
	 */
	void update(T t);
	
	/**
	 * 保存数据
	 * @param t
	 */
	Serializable save(T t);

	/**
	 * 更新数据
	 * @param t
	 */
	void saveOrUpdate(T t);
}
