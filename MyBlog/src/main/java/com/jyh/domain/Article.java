package com.jyh.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Article entity. @author MyEclipse Persistence Tools
 */

public class Article implements java.io.Serializable {

	// Fields

	private String articleId;
	private User userByCollectorId;
	private User userByAuthorId;
	private String articleTitle;
	private String articleSummary;
	private String articleContent;
	private Integer commentNum;
	private Date articleDate;
	private Integer readnum;
	private String tag;
	private Integer type;
	private Integer classification;
	private Integer state;
	private Set comments = new HashSet(0);
	private Set articleClasses = new HashSet(0);
	private Set collection = new HashSet(0);
	private Set dynamicUser = new HashSet(0);
	// Constructors

	/** default constructor */
	public Article() {
	}
	
	public Article(String articleId, User userByAuthorId,
			String articleTitle, String articleSummary,Date articleDate, Integer readnum,Set comments, Set collection) {
		this.articleId = articleId;
		this.userByAuthorId = userByAuthorId;
		this.articleTitle = articleTitle;
		this.articleSummary = articleSummary;
		this.articleDate = articleDate;
		this.readnum = readnum;
		this.comments = comments;
		this.collection = collection;
	}

	/** full constructor */
	public Article(User userByCollectorId, User userByAuthorId,
			String articleTitle, String articleSummary, String articleContent,
			Integer commentNum, Date articleDate, Integer readnum,
			String tag, Integer type, Integer classification, Integer state,
			Set comments, Set articleClasses, Set dynamicUser) {
		this.userByCollectorId = userByCollectorId;
		this.userByAuthorId = userByAuthorId;
		this.articleTitle = articleTitle;
		this.articleSummary = articleSummary;
		this.articleContent = articleContent;
		this.commentNum = commentNum;
		this.articleDate = articleDate;
		this.readnum = readnum;
		this.tag = tag;
		this.type = type;
		this.classification = classification;
		this.state = state;
		this.comments = comments;
		this.articleClasses = articleClasses;
		this.dynamicUser = dynamicUser;
	}

	// Property accessors

	public String getArticleId() {
		return this.articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public User getUserByCollectorId() {
		return this.userByCollectorId;
	}

	public void setUserByCollectorId(User userByCollectorId) {
		this.userByCollectorId = userByCollectorId;
	}

	public User getUserByAuthorId() {
		return this.userByAuthorId;
	}

	public void setUserByAuthorId(User userByAuthorId) {
		this.userByAuthorId = userByAuthorId;
	}

	public String getArticleTitle() {
		return this.articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getArticleSummary() {
		return this.articleSummary;
	}

	public void setArticleSummary(String articleSummary) {
		this.articleSummary = articleSummary;
	}

	public String getArticleContent() {
		return this.articleContent;
	}

	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}

	public Integer getCommentNum() {
		return this.commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	public Date getArticleDate() {
		return this.articleDate;
	}

	public void setArticleDate(Date articleDate) {
		this.articleDate = articleDate;
	}

	public Integer getReadnum() {
		return this.readnum;
	}

	public void setReadnum(Integer readnum) {
		this.readnum = readnum;
	}

	public String getTag() {
		return this.tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getClassification() {
		return this.classification;
	}

	public void setClassification(Integer classification) {
		this.classification = classification;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Set getComments() {
		return this.comments;
	}

	public void setComments(Set comments) {
		this.comments = comments;
	}

	public Set getArticleClasses() {
		return this.articleClasses;
	}

	public void setArticleClasses(Set articleClasses) {
		this.articleClasses = articleClasses;
	}

	public Set getCollection() {
		return collection;
	}

	public void setCollection(Set collection) {
		this.collection = collection;
	}

	public Set getDynamicUser() {
		return dynamicUser;
	}

	public void setDynamicUser(Set dynamicUser) {
		this.dynamicUser = dynamicUser;
	}
	
}