package com.jyh.service;

import java.util.List;
import java.util.Map;

import javax.mail.search.SearchException;

import com.jyh.domain.Article;
import com.jyh.domain.Comment;
import com.jyh.utils.PageUtil;

public interface ArticleService extends CommonService<Article>{

	/**
	 * 通过文章状态获取文章
	 */
	Article getArticleByState(Integer sate);
	
	/**
	 * 分页获取文章
	 * @param pageNum
	 * @return
	 */
	PageUtil findArticlesPage(String pageNum);
	
	/**
	 * 获取我的博客页面中文章相关的所有数据
	 * @return
	 */
	Map<String,Object> getMyBlogArticleData(String userId);
	
	/**
	 * 获取文章的日期分类
	 * @return
	 */
	List<Map<String, String>> getArticleDateGroup(String userId);
	
	/**
	 * 根据评论倒序查找文章
	 * @return
	 */
	List<Object[]> findArticleByCommentDesc(String userId);
	
	/**
	 * 根据阅读数倒序查找文章
	 * @return
	 */
	List<Object[]> findArticleByReadNumDesc(String userId);
	
	/**
	 * 根据用户id分页获取文章
	 * @param userId	用户id
	 * @param pageNum	页数
	 * @return
	 */
	PageUtil findUserArticlesPage(String userId, String pageNum);
	
	/**
	 * 根据文章分类分页获取文章
	 * @param articleClass	文章分类
	 * @param pageNum		文章分页
	 * @return
	 */
	PageUtil findArticlesPageByClass(Integer articleClass, String pageNum);
	
	/**
	 * 根据搜索输入的文章标题获取不同用户的文章
	 * @param userId		用户id
	 * @param articleTitle	文章标题
	 * @param pageNum		页码
	 * @return
	 */
	PageUtil findUserArticlesPageByTitle(String userId, String articleTitle, String pageNum);
	
	/**
	 * 查找用户不同分类下的文章
	 * @param userId		用户id
	 * @param articleClass	用户的分类
	 * @param pageNum		页码
	 * @return
	 */
	PageUtil findUserArticlesPageByClass(String userId, String articleClass, String pageNum);
	
	/**
	 * 按照日期分类查找用户的文章
	 * @param userId		用户id
	 * @param dateClass		用户的时间分类
	 * @param pageNum		页码
	 * @return
	 */
	PageUtil findUserArticlesGroupByDate(String userId, String dateClass, String pageNum);
	
	/**
	 * 处理一篇文章下的评论
	 * @param articleId	文章id
	 * @return
	 */
	Map<Comment, List<Comment>> handleCommentsForArticle(String articleId);
	
}
