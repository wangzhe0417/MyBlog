<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<li class="list-group-item first" id="articleComments">查看评论</li>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/blog/frame/bootstrap/css/bootstrap.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/blog/js/jquery.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/blog/frame/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript">
	$(function() {
		commentEvent.init();
	});
</script>
<s:if test="#articleComments.size()==0">
	<div class="panel-body">还没有评论，赶快评论一下吧！</div>
</s:if>
<s:iterator value="#articleComments">
	<s:set var="firstComment" value="key"></s:set>
	<li class="list-group-item" id=${firstComment.floor}>
		<div id="accordion" role="tablist" aria-multiselectable="true">
			<div>
				<div class="pre" style="padding:10px 15px;">
					<h4 class="panel-title">
						<div class="comment">
							<div class="comment_user">
								<a href="myBlogView.action?userId=${firstComment.userByCommentUser.userId}" target="_blank">
									<img src="${firstComment.userByCommentUser.userImg}">
								</a>
								<a href="myBlogView.action?userId=${firstComment.userByCommentUser.userId}" target="_blank"
									style="display:block;text-align:center;">
									${firstComment.userByCommentUser.userNickname} </a>
							</div>
							<div class="comment_content">
								<s:property value="key.commentContent" />
							</div>
							<div class="comment_option">
								<s:if test="#firstComment.userByCommentUser.userId == #session.user.userId">
									<a onclick="commentEvent.deleteComment('${firstComment.commentId}')">
										<span class="glyphicon glyphicon-trash"></span>
									</a>&nbsp;&nbsp;&nbsp;
								</s:if>
								<a href="javascript:;"
									onclick="commentEvent.reply(${firstComment.floor},
									'${firstComment.userByCommentUser.userId}','${firstComment.userByCommentUser.userNickname}');"
									class="reply"> 回复 </a>&nbsp;&nbsp;&nbsp; <span> <s:date
										name="#firstComment.commentDate" format="yyyy-MM-dd HH:mm" />
								</span>
							</div>
						</div>
					</h4>
				</div>
				<div class="isscroll panel-collapse collapse in">
					<!-- <div class="panel-body"> -->
					<ul style="margin-bottom:0px;padding-left:60px;">
						<s:iterator value="value" var="secondComment">
							<s:set value="#secondComment" var="secondComment"></s:set>
							<li class="comments-li">
								<div class="comment">
									<div class="comment_reply">
										<a href="myBlogView.action?userId=${secondComment.userByCommentUser.userId}" target="_blank">
											<img src="${secondComment.userByCommentUser.userImg}">
										</a>
										<a href="javascript:;" href="myBlogView.action?userId=${secondComment.userByCommentUser.userId}" target="_blank"
											style="display: block;text-align: center;">
											${secondComment.userByCommentUser.userNickname} </a>
									</div>
									
									<div
										style="float:left;margin-left:5px;margin-right:5px;margin-top:15px;">
										<span>回复</span>
									</div>
									
									<div class="comment_reply">
										<a href="myBlogView.action?userId=${secondComment.userByCommentUser.userId}" target="_blank">
											<img src="${secondComment.userByReplyUser.userImg}">
										</a>
										<a href="javascript:;" href="myBlogView.action?userId=${secondComment.userByCommentUser.userId}" target="_blank"
											style="display: block;text-align: center;">
											${secondComment.userByReplyUser.userNickname} </a>
									</div>
									<div class="comment_content">
										<s:property value="#secondComment.commentContent" />
									</div>
									<div class="comment_option">
										<s:if test="#secondComment.userByCommentUser.userId == #session.user.userId">
											<a onclick="commentEvent.deleteComment('${secondComment.commentId}')">
												<span class="glyphicon glyphicon-trash"></span>
											</a>&nbsp;&nbsp;&nbsp;
										</s:if>
										<a href="javascript:;" onclick="commentEvent.reply(${secondComment.floor},
										'${secondComment.userByCommentUser.userId}','${firstComment.userByCommentUser.userNickname}');"
											class="reply"> 回复 </a>&nbsp;&nbsp;&nbsp;
										 <span> 
											<s:date name="#secondComment.commentDate" format="yyyy-MM-dd HH:mm" />
										</span>
									</div>
								</div>
							</li>
						</s:iterator>
					</ul>
					<!-- </div> -->
				</div>
			</div>
		</div>
	</li>
</s:iterator>