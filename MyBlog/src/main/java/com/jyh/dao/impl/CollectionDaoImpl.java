package com.jyh.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate4.HibernateCallback;

import com.jyh.dao.CollectionDao;
import com.jyh.domain.Collection;
import com.jyh.utils.PageHibernateCallBackUtil;

@SuppressWarnings("all")
public class CollectionDaoImpl extends CommonDaoImpl<Collection> implements
		CollectionDao {

	public List<Object[]> findSomeCollections(int startIndex, int num, String userId) {
		List<Object[]> collections = (List<Object[]>)getHibernateTemplate().execute((HibernateCallback<Object>) new PageHibernateCallBackUtil(
				"select a.articleId,a.articleTitle,c.collectionId,c.collectDate from Article a left join a.collection c left join c.user u where u.userId = ? order by c.collectDate desc ", startIndex, num, userId));
		if(collections == null || collections.size() == 0)
			return null;
		return collections;
	}

	@Override
	public Integer getCollectionCount(String userId) {
		return ((Long)getHibernateTemplate().find("select count(*) from Collection c where c.user.userId = ?",userId).listIterator().next()).intValue();
	}
}
