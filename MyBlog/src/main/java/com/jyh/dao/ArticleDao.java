package com.jyh.dao;

import java.util.List;

import com.jyh.domain.Article;
import com.jyh.exceptions.SQLStatementIsErrorException;

public interface ArticleDao extends CommonDao<Article> {

	/**
	 * 根据state字段获取文章
	 */
	Article getArticleByState(Integer state);
	
	/**
	 * 按条件获取文章数目
	 * @param condition 查询条件(sql语句)
	 * @return
	 */
	Integer getArticleCount(String condition);
	
	/**
	 * 分页获取文章
	 * @param startIndex 起始下标
	 * @param articleNum 文章数目
	 * @return
	 */
	List<Article> findSomeArticles(int startIndex, int articleNum);
	
	/**
	 * 获取文章的一些字段集合
	 * @param condition		查询条件
	 * @param startIndex	开始下标
	 * @param dataNumber	数据数目
	 * @return
	 * @throws  
	 */
	List<Object[]> findArticleFieldList(String condition, int startIndex, int dataNumber);
	
	/**
	 * 获取某个用户的文章数目
	 * @return
	 */
	Integer getUserArticlesCount(String userId);
	
}
