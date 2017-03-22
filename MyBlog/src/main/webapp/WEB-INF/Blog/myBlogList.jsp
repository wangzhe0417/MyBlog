<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/blog/js/jquery.js"></script>
<script type="text/javascript">
	$(function(){
		myBlogListEvent.init();
	});
</script>
<s:if test="#page.records.size() == 0">
	<div class="panel-body"><font color="red" size="20px;">还没有文章!</font></div>
</s:if>
<s:if test="#page.records != null && #page.records.size() > 0">
	<div id="MyBlogsList">
		<div>
			<ul class="list-group new-group">
				<s:iterator value="#page.records" var="userArticle">
					<s:set value="#userArticle" var="userArticle"></s:set>
					<li class="list-group-item new-item">
						<div class="panel panel-default">
							<div class="panel-body" style="padding:12px">
								<div class="myblogs">
									<div class="blog_title">
										<s:if test="#userArticle[5] == 0">
											<span class="label label-success">原</span>&nbsp;&nbsp;
					  					</s:if>
										<s:if test="#userArticle[5] == 1">
											<span class="label label-warning">转</span>&nbsp;&nbsp;
					  					</s:if>
										<s:if test="#userArticle[5] == 2">
											<span class="label label-danger">译</span>&nbsp;&nbsp;
					  					</s:if>
										<a href="javascript:void(0);" class="myArticleDetail" date-id="${userArticle[0]}">
											<s:property value="#userArticle[1]" />
										</a>
									</div>
									<div class="article_content">
										<s:property value="#userArticle[2]" />
									</div>
									<div class="article_info">
										<span>
											<s:date name="#userArticle[3]" format="yyyy-MM-dd HH:mm" timezone="GMT+00:00"/>
										</span>&nbsp;&nbsp;&nbsp;
										<a class="myArticleDetail" date-id="${userArticle[0]}">阅读(${userArticle[4]})</a>&nbsp;&nbsp;&nbsp; 
										<a class="myArticleDetail" date-id="${userArticle[0]}">评论(${userArticle[6]})</a>&nbsp;&nbsp;&nbsp; 
										<c:if test="${!empty sessionScope.user && sessionScope.user.userId eq userArticle[7]}">
											<a href="writeArticle.action?article.articleId=${userArticle[0]}" target="_blank">编辑</a>&nbsp;&nbsp;&nbsp; 
											<a onclick="myBlogEvent.deleteArticle('${userArticle[0]}',0)">删除</a>
										</c:if>
									</div>
								</div>
							</div>
						</div>
					</li>
				</s:iterator>
			</ul>
		</div>
		<%@ include file="page.jsp"%>
	</div>
</s:if>

