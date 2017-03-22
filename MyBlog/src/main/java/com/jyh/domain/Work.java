package com.jyh.domain;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Work entity. @author MyEclipse Persistence Tools
 */
@JsonIgnoreProperties(value={"user"})
public class Work implements java.io.Serializable {

	// Fields

	private String workId;
	private User user;
	private Date startDate;
	private Date endDate;
	private String company;
	private String position;
	private String briefing;

	// Constructors

	/** default constructor */
	public Work() {
	}

	/** full constructor */
	public Work(User user, Date startDate, Date endDate, String company,
			String position, String briefing) {
		this.user = user;
		this.startDate = startDate;
		this.endDate = endDate;
		this.company = company;
		this.position = position;
		this.briefing = briefing;
	}

	// Property accessors

	public String getWorkId() {
		return this.workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getBriefing() {
		return this.briefing;
	}

	public void setBriefing(String briefing) {
		this.briefing = briefing;
	}

}