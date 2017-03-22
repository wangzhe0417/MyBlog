package com.jyh.domain;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Education entity. @author MyEclipse Persistence Tools
 */
@JsonIgnoreProperties(value={"user"})
public class Education implements java.io.Serializable {

	// Fields

	private String educationId;
	private User user;
	private Date startDate;
	private Date endDate;
	private String education;
	private String school;
	private String professional;

	// Constructors

	/** default constructor */
	public Education() {
	}

	/** full constructor */
	public Education(User user, Date startDate, Date endDate, String education,
			String school, String professional) {
		this.user = user;
		this.startDate = startDate;
		this.endDate = endDate;
		this.education = education;
		this.school = school;
		this.professional = professional;
	}

	// Property accessors

	public String getEducationId() {
		return this.educationId;
	}

	public void setEducationId(String educationId) {
		this.educationId = educationId;
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

	public String getEducation() {
		return this.education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getSchool() {
		return this.school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getProfessional() {
		return this.professional;
	}

	public void setProfessional(String professional) {
		this.professional = professional;
	}

}