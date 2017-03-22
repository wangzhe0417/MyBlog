package com.jyh.service.impl;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.jyh.dao.CommentDao;
import com.jyh.domain.Comment;
import com.jyh.service.CommentService;
import com.jyh.utils.JsonConvertUtil;
import com.jyh.utils.PageUtil;

public class CommentServiceImpl extends CommonServiceImpl<Comment> implements CommentService{

	private CommentDao commentDao;

	public CommentDao getCommentDao() {
		return commentDao;
	}

	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}

	@Override
	public List<Comment> findCommentsByArticle(String articleId) {
		List<Comment> comments = commentDao.findCommentsByArticle(articleId);
		
		return comments;
	}

	@Override
	public String findUserComments(String userId) throws IOException {
		List<Object[]> comments = commentDao.findUserComments(userId);
		String json = JsonConvertUtil.returnJson(comments);
		return json;
	}

	@Override
	public String findUserArticlesComments(String userId) throws IOException {
		List<Object[]> comments = commentDao.findUserArticlesComments(userId);
		String json = JsonConvertUtil.returnJson(comments);
		return json;
	}

}
