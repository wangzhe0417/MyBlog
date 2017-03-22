package com.jyh.domain;

/**
 * Attention entity. @author MyEclipse Persistence Tools
 */

public class Attention implements java.io.Serializable {

	// Fields

	private String attentionId;
	private User userByUser;
	private User userByFollower;

	// Constructors

	/** default constructor */
	public Attention() {
	}

	/** full constructor */
	public Attention(User userByUser, User userByFollower) {
		this.userByUser = userByUser;
		this.userByFollower = userByFollower;
	}

	// Property accessors

	public String getAttentionId() {
		return this.attentionId;
	}

	public void setAttentionId(String attentionId) {
		this.attentionId = attentionId;
	}

	public User getUserByUser() {
		return this.userByUser;
	}

	public void setUserByUser(User userByUser) {
		this.userByUser = userByUser;
	}

	public User getUserByFollower() {
		return this.userByFollower;
	}

	public void setUserByFollower(User userByFollower) {
		this.userByFollower = userByFollower;
	}

}