package com.jyh.utils;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;

public class PageHibernateCallBackUtil<T> implements HibernateCallback<List<T>> {

	private String hql;
    private Object[] params;
    private int startIndex;
    private int pageSize;
    
    public PageHibernateCallBackUtil(String hql,
            int startIndex, int pageSize, final Object... params) {
        super();
        this.hql = hql;
        this.params = params;
        this.startIndex = startIndex;
        this.pageSize = pageSize;
    }
	
	@SuppressWarnings("unchecked")
	public List<T> doInHibernate(Session session) throws HibernateException {
		Query query = session.createQuery(hql);
        
        if(params != null){
            for(int i = 0 ; i < params.length ; i ++){
                query.setParameter(i, params[i]);
            }
        }
        
        query.setFirstResult(startIndex);
        query.setMaxResults(pageSize);
        
        return query.list();
	}

}
