package com.jyh.service;

import java.io.IOException;
import java.util.List;

import com.jyh.domain.Comment;

public interface CommentService extends CommonService<Comment>{
	
	/**
	 * 通过文章id获取评论
	 * @param articleId	文章id
	 * @return
	 */
	List<Comment> findCommentsByArticle(String articleId);

	/**
	 * 获取某个用户发表的的所有评论
	 * @param userId
	 * @return
	 */
	String findUserComments(String userId) throws IOException;
	
	/**
	 * 获取某个用户所有文章下的评论
	 * @param userId
	 * @return
	 */
	String findUserArticlesComments(String userId) throws IOException;
}
