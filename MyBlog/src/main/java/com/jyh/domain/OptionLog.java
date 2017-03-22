package com.jyh.domain;

import java.util.Date;

/**
 * OptionLog entity. @author MyEclipse Persistence Tools
 */

public class OptionLog implements java.io.Serializable {

	// Fields

	private String optionId;
	private User user;
	private String optionUsername;
	private Date optionDate;
	private String optionMethod;
	private String optionClass;
	private String optionMessage;

	// Constructors

	/** default constructor */
	public OptionLog() {
	}

	/** full constructor */
	public OptionLog(User user, String optionUsername, Date optionDate,
			String optionMethod, String optionClass, String optionMessage) {
		this.user = user;
		this.optionUsername = optionUsername;
		this.optionDate = optionDate;
		this.optionMethod = optionMethod;
		this.optionClass = optionClass;
		this.optionMessage = optionMessage;
	}

	// Property accessors

	public String getOptionId() {
		return this.optionId;
	}

	public void setOptionId(String optionId) {
		this.optionId = optionId;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getOptionUsername() {
		return this.optionUsername;
	}

	public void setOptionUsername(String optionUsername) {
		this.optionUsername = optionUsername;
	}

	public Date getOptionDate() {
		return this.optionDate;
	}

	public void setOptionDate(Date optionDate) {
		this.optionDate = optionDate;
	}

	public String getOptionMethod() {
		return this.optionMethod;
	}

	public void setOptionMethod(String optionMethod) {
		this.optionMethod = optionMethod;
	}

	public String getOptionClass() {
		return this.optionClass;
	}

	public void setOptionClass(String optionClass) {
		this.optionClass = optionClass;
	}

	public String getOptionMessage() {
		return this.optionMessage;
	}

	public void setOptionMessage(String optionMessage) {
		this.optionMessage = optionMessage;
	}

}