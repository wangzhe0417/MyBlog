package com.jyh.dao.impl;


import java.util.List;

import org.springframework.orm.hibernate4.HibernateCallback;

import com.jyh.dao.ArticleDao;
import com.jyh.domain.Article;
import com.jyh.domain.User;
import com.jyh.exceptions.SQLStatementIsErrorException;
import com.jyh.utils.PageHibernateCallBackUtil;
@SuppressWarnings("all")
public class ArticleDaoImpl extends CommonDaoImpl<Article> implements ArticleDao {

	@Override
	public Article getArticleByState(Integer state) {
		List<Article> articles = (List<Article>)getHibernateTemplate().find("from Article article where article.state = ?", state);
		if(articles == null || articles.size() == 0)
			return null;
		return articles.get(0);
	}

	public Integer getArticleCount(String condition) {
		return ((Long)getHibernateTemplate().find(condition).listIterator().next()).intValue();
	}

	public List<Article> findSomeArticles(int startIndex, int articleNum) {
		List<Article> articles = (List<Article>)getHibernateTemplate().execute((HibernateCallback<Article>) new PageHibernateCallBackUtil(
				"from Article article order by article.articleDate desc ", startIndex, articleNum, null));
		if(articles == null || articles.size() == 0)
			return null;
		return articles;
	}

	public List<Object[]> findArticleFieldList(String condition, int startIndex, int dataNumber) {
		List<Object[]> dataList = (List<Object[]>)getHibernateTemplate().execute((HibernateCallback<Object>) new PageHibernateCallBackUtil(
				condition, startIndex, dataNumber, null));
		return dataList;
	}

	public Integer getUserArticlesCount(String userId) {
		return ((Long)getHibernateTemplate().find("select count(*) from Article a left join a.userByAuthorId u where u.userId='"+userId+"'").listIterator().next()).intValue();
	}

}
