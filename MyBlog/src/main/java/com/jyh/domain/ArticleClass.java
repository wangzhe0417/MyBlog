package com.jyh.domain;

/**
 * ArticleClass entity. @author MyEclipse Persistence Tools
 */

public class ArticleClass implements java.io.Serializable {

	// Fields

	private String caId;
	private Article article;
	private PersonalClassification personalClassification;

	// Constructors

	/** default constructor */
	public ArticleClass() {
	}

	/** full constructor */
	public ArticleClass(Article article,
			PersonalClassification personalClassification) {
		this.article = article;
		this.personalClassification = personalClassification;
	}

	// Property accessors

	public String getCaId() {
		return this.caId;
	}

	public void setCaId(String caId) {
		this.caId = caId;
	}

	public Article getArticle() {
		return this.article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public PersonalClassification getPersonalClassification() {
		return this.personalClassification;
	}

	public void setPersonalClassification(
			PersonalClassification personalClassification) {
		this.personalClassification = personalClassification;
	}

}