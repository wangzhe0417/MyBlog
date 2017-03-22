package com.jyh.domain;

import java.util.Date;

/**
 * Comment entity. @author MyEclipse Persistence Tools
 */

public class Comment implements java.io.Serializable {

	// Fields

	private String commentId;
	private User userByReplyUser;
	private User userByCommentUser;
	private Article commentArticle;
	private Integer floor;
	private String commentContent;
	private Date commentDate;

	// Constructors

	/** default constructor */
	public Comment() {
	}

	/** full constructor */
	public Comment(User userByReplyUser, User userByCommentUser,
			Article commentArticle, Integer floor, String commentContent, Date commentDate) {
		this.userByReplyUser = userByReplyUser;
		this.userByCommentUser = userByCommentUser;
		this.commentArticle = commentArticle;
		this.floor = floor;
		this.commentContent = commentContent;
		this.commentDate = commentDate;
	}

	// Property accessors

	public String getCommentId() {
		return this.commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public User getUserByReplyUser() {
		return this.userByReplyUser;
	}

	public void setUserByReplyUser(User userByReplyUser) {
		this.userByReplyUser = userByReplyUser;
	}

	public User getUserByCommentUser() {
		return this.userByCommentUser;
	}

	public void setUserByCommentUser(User userByCommentUser) {
		this.userByCommentUser = userByCommentUser;
	}

	public Article getCommentArticle() {
		return this.commentArticle;
	}

	public void setCommentArticle(Article commentArticle) {
		this.commentArticle = commentArticle;
	}

	public Integer getFloor() {
		return this.floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public String getCommentContent() {
		return this.commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

}