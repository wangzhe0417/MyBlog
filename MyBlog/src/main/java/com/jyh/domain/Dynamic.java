package com.jyh.domain;

import java.util.Date;

/**
 * Dynamic entity. @author MyEclipse Persistence Tools
 */

public class Dynamic implements java.io.Serializable {

	// Fields

	private String dynamicId;
	private Article article;
	private User user;
	private Date dynamicDate;
	private Integer dynamicState;

	// Constructors

	/** default constructor */
	public Dynamic() {
	}

	/** full constructor */
	public Dynamic(Article article, User user, Date dynamicDate,
			Integer dynamicState) {
		this.article = article;
		this.user = user;
		this.dynamicDate = dynamicDate;
		this.dynamicState = dynamicState;
	}

	// Property accessors

	public String getDynamicId() {
		return this.dynamicId;
	}

	public void setDynamicId(String dynamicId) {
		this.dynamicId = dynamicId;
	}

	public Article getArticle() {
		return this.article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDynamicDate() {
		return this.dynamicDate;
	}

	public void setDynamicDate(Date dynamicDate) {
		this.dynamicDate = dynamicDate;
	}

	public Integer getDynamicState() {
		return this.dynamicState;
	}

	public void setDynamicState(Integer dynamicState) {
		this.dynamicState = dynamicState;
	}

}