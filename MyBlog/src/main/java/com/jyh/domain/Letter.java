package com.jyh.domain;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Letter entity. @author MyEclipse Persistence Tools
 */

public class Letter implements java.io.Serializable {

	// Fields

	private String letterId;
	private User userBySendUser;
	private User userByReceiveUser;
	private Date sendDate;
	private String letterContent;

	// Constructors

	/** default constructor */
	public Letter() {
	}

	/** full constructor */
	public Letter(User userBySendUser, User userByReceiveUser,
			Date sendDate, String letterContent) {
		this.userBySendUser = userBySendUser;
		this.userByReceiveUser = userByReceiveUser;
		this.sendDate = sendDate;
		this.letterContent = letterContent;
	}

	// Property accessors

	public String getLetterId() {
		return this.letterId;
	}

	public void setLetterId(String letterId) {
		this.letterId = letterId;
	}

	public User getUserBySendUser() {
		return this.userBySendUser;
	}

	public void setUserBySendUser(User userBySendUser) {
		this.userBySendUser = userBySendUser;
	}

	public User getUserByReceiveUser() {
		return this.userByReceiveUser;
	}

	public void setUserByReceiveUser(User userByReceiveUser) {
		this.userByReceiveUser = userByReceiveUser;
	}

	public Date getSendDate() {
		return this.sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getLetterContent() {
		return this.letterContent;
	}

	public void setLetterContent(String letterContent) {
		this.letterContent = letterContent;
	}

}