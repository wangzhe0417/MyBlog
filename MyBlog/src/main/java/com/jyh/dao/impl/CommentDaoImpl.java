package com.jyh.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate4.HibernateCallback;

import com.jyh.dao.CommentDao;
import com.jyh.domain.Article;
import com.jyh.domain.Comment;
import com.jyh.utils.PageHibernateCallBackUtil;

@SuppressWarnings("all")
public class CommentDaoImpl extends CommonDaoImpl<Comment> implements
		CommentDao {

	public List<Comment> findCommentsByArticle(String articleId) {
		return (List<Comment>)getHibernateTemplate().find(
				"from Comment c left join c.commentArticle a where a.articleId=? order by commentDate desc", articleId);
	}

	public List<Object[]> findUserComments(String userId) {
		List<Object[]> comments = (List<Object[]>)getHibernateTemplate().find(
				"select c.commentId,c.commentContent,a.articleId,a.articleTitle,u.userId,u.userNickname from Comment c left join c.commentArticle a left join c.userByCommentUser u where u.userId = ?", userId);
		if(comments == null || comments.size() == 0)
			return null;
		return comments;
	}

	public List<Object[]> findUserArticlesComments(String userId) {
		List<Object[]> comments = (List<Object[]>)getHibernateTemplate().find(
				"select c.commentId,c.commentContent,a.articleId,a.articleTitle,u.userId,u.userNickname from Comment c left join c.commentArticle a left join a.userByAuthorId u where u.userId = ?", userId);
		if(comments == null || comments.size() == 0)
			return null;
		return comments;
	}

}
