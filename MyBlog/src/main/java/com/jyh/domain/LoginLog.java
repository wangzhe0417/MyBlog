package com.jyh.domain;

import java.util.Date;

/**
 * LoginLog entity. @author MyEclipse Persistence Tools
 */

public class LoginLog implements java.io.Serializable {

	// Fields

	private String loginId;
	private User user;
	private String loginUsername;
	private Date loginDate;
	private String loginIp;

	// Constructors

	/** default constructor */
	public LoginLog() {
	}

	/** full constructor */
	public LoginLog(User user, String loginUsername, Date loginDate,
			String loginIp) {
		this.user = user;
		this.loginUsername = loginUsername;
		this.loginDate = loginDate;
		this.loginIp = loginIp;
	}

	// Property accessors

	public String getLoginId() {
		return this.loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLoginUsername() {
		return this.loginUsername;
	}

	public void setLoginUsername(String loginUsername) {
		this.loginUsername = loginUsername;
	}

	public Date getLoginDate() {
		return this.loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getLoginIp() {
		return this.loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

}