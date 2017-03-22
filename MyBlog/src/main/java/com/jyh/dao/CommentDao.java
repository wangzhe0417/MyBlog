package com.jyh.dao;

import java.util.List;

import com.jyh.domain.Comment;

public interface CommentDao extends CommonDao<Comment> {

	/**
	 * 按照文章id获取评论
	 * @param articleId	文章id
	 * @return
	 */
	List<Comment> findCommentsByArticle(String articleId);
	
	/**
	 * 获取某个用户发表的的所有评论
	 * @param userId
	 * @return
	 */
	List<Object[]> findUserComments(String userId);
	
	/**
	 * 获取某个用户所有文章下的评论
	 * @param userId
	 * @return
	 */
	List<Object[]> findUserArticlesComments(String userId);
}
