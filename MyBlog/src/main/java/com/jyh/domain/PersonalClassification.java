package com.jyh.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * PersonalClassification entity. @author MyEclipse Persistence Tools
 */

public class PersonalClassification implements java.io.Serializable {

	// Fields

	private String classificationId;
	private User user;
	private String classificationName;
	private Set articleClasses = new HashSet(0);
	private Integer defaultSetting;

	// Constructors

	/** default constructor */
	public PersonalClassification() {
	}

	/** full constructor */
	public PersonalClassification(User user, String classificationName,
			Set articleClasses) {
		this.user = user;
		this.classificationName = classificationName;
		this.articleClasses = articleClasses;
	}

	// Property accessors

	public String getClassificationId() {
		return this.classificationId;
	}

	public void setClassificationId(String classificationId) {
		this.classificationId = classificationId;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getClassificationName() {
		return this.classificationName;
	}

	public void setClassificationName(String classificationName) {
		this.classificationName = classificationName;
	}

	public Set getArticleClasses() {
		return this.articleClasses;
	}

	public void setArticleClasses(Set articleClasses) {
		this.articleClasses = articleClasses;
	}

	public Integer getDefaultSetting() {
		return defaultSetting;
	}

	public void setDefaultSetting(Integer defaultSetting) {
		this.defaultSetting = defaultSetting;
	}

}