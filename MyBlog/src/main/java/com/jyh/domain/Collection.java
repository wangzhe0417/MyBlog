package com.jyh.domain;

import java.util.Date;

/**
 * Collection entity. @author MyEclipse Persistence Tools
 */

public class Collection implements java.io.Serializable {

	// Fields

	private String collectionId;
	private User user;
	private Article article;
	private Date collectDate;

	// Constructors

	/** default constructor */
	public Collection() {
	}

	/** full constructor */
	public Collection(User user, Article article, Date collectDate) {
		this.user = user;
		this.article = article;
		this.collectDate = collectDate;
	}

	// Property accessors

	public String getCollectionId() {
		return this.collectionId;
	}

	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Article getArticle() {
		return this.article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public Date getCollectDate() {
		return this.collectDate;
	}

	public void setCollectDate(Date collectDate) {
		this.collectDate = collectDate;
	}

}