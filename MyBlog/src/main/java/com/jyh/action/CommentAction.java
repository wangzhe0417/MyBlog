package com.jyh.action;

import java.io.IOException;
import java.util.Calendar;

import com.jyh.domain.Article;
import com.jyh.domain.Comment;
import com.jyh.domain.Dynamic;
import com.jyh.domain.User;
import com.jyh.service.CommentService;
import com.jyh.service.DynamicService;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
public class CommentAction extends MyBaseAction {

	private CommentService commentService;
	private Comment comment;
	private DynamicService dynamicService;
	
	public CommentService getCommentService() {
		return commentService;
	}
	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}
	public Comment getComment() {
		return comment;
	}
	public void setComment(Comment comment) {
		this.comment = comment;
	}
	public DynamicService getDynamicService() {
		return dynamicService;
	}
	public void setDynamicService(DynamicService dynamicService) {
		this.dynamicService = dynamicService;
	}
	
	public String saveComment(){
		if(baseUser == null){
			setState("3");
			setMessage("loginView.action");
			return "comment";
		}
		else{
			comment.setUserByCommentUser(baseUser);
			comment.setCommentDate(Calendar.getInstance().getTime());
			comment.setFloor(comment.getFloor()+1);
			commentService.saveEntity(comment);
			//添加动态
			Dynamic dynamic = new Dynamic(comment.getCommentArticle(), baseUser, Calendar.getInstance().getTime(), 1);
			dynamicService.saveEntity(dynamic);
			setState("0");
		}
		return "comment";
	}
	
	/**
	 * 删除评论
	 * @return
	 */
	public String deleteComment(){
		if(baseUser == null){
			setState("3");
			setMessage("loginView.action");
			return "comment";
		}
		if(comment != null && !comment.getCommentId().isEmpty()){
			commentService.deleteEntityById(comment.getCommentId());
			setState("0");
			setMessage("删除成功!");
		}else{
			setState("2");
			setMessage("删除失败!");
		}
		return "comment";
	}
	
	public String findUserComments(){
		if(baseUser == null){
			setState("3");
			setMessage("loginView.action");
			return "comment";
		}
		try {
			setMessage(commentService.findUserComments(baseUser.getUserId()));
			setState("0");
		} catch (IOException e) {
			e.printStackTrace();
			setMessage("出错了!");
			setState("2");
		}
		return "comment";
	}
	
	public String findUserArticlesComments(){
		if(baseUser == null){
			setState("3");
			setMessage("loginView.action");
			return "comment";
		}
		try {
			setMessage(commentService.findUserArticlesComments(baseUser.getUserId()));
			setState("0");
		} catch (IOException e) {
			e.printStackTrace();
			setMessage("出错了!");
			setState("2");
		}
		return "comment";
	}
}
