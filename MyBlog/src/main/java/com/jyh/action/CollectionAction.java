package com.jyh.action;

import java.io.IOException;
import java.util.Calendar;

import com.jyh.domain.Article;
import com.jyh.domain.Collection;
import com.jyh.service.CollectionService;


@SuppressWarnings("serial")
public class CollectionAction extends MyBaseAction {
	private CollectionService collectionService;
	private Article article;
	private Collection collection;
	private String pageNum;
	public CollectionService getCollectionService() {
		return collectionService;
	}
	public void setCollectionService(CollectionService collectionService) {
		this.collectionService = collectionService;
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	public Collection getCollection() {
		return collection;
	}
	public void setCollection(Collection collection) {
		this.collection = collection;
	}
	public String getPageNum() {
		return pageNum;
	}
	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}
	/**
	 * 添加收藏文章
	 * @return
	 */
	public String collectArticle(){
		if(article != null && !article.getArticleId().isEmpty()){
			Collection collection = new Collection(baseUser, article, Calendar.getInstance().getTime());
			collectionService.saveEntity(collection);
			setState("0");
			setMessage("收藏成功!");
		}
		else {
			setState("2");
			setMessage("收藏失败!");
		}
		return "collection";
	}
	
	/**
	 * 取消收藏
	 * @return
	 */
	public String cancelCollection(){
		if(collection != null && !collection.getCollectionId().isEmpty()){
			collectionService.deleteEntityById(collection.getCollectionId());
			setState("0");
			setMessage("取消成功!");
		}
		else {
			setState("2");
			setMessage("取消失败!");
		}
		return "collection";
	}
	
	/**
	 * 获取我的收藏
	 */
	public String findMyCollections(){
		if(visitedUser != null && !visitedUser.getUserId().isEmpty()){
			try {
				setMessage(collectionService.findCollectionsPage(pageNum, visitedUser.getUserId()));
				setState("0");
			} catch (IOException e) {
				setMessage("获取失败!");
				setState("2");
			}
		}else {
			setState("2");
			setMessage("获取失败!");
		}
		return "collection";
	}
}
