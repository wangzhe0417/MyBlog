<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title id="t">我的博客</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/blog/css/home.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/blog/css/myblog.css">
	<link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.9.0/styles/default.min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/blog/css/bootstrap.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/blog/js/jquery.js"></script>
	<script type="text/javascript" src = "${pageContext.request.contextPath}/blog/frame/bootstrap/js/bootstrap.js"></script>
	<script src="http://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.9.0/highlight.min.js"></script>
	<script src="${pageContext.request.contextPath}/blog/js/marked.js"></script>
	<script src="${pageContext.request.contextPath}/blog/js/myBlog.js"></script>
	<script src="${pageContext.request.contextPath}/blog/js/comments.js"></script>
	<script type="text/javascript">
	</script>
</head>
<body style="font-family: -webkit-pictograph;">
	<%@ include file="head.jsp"%>
	<div id = "MyBlog">
		<div class = "mytitle">
			<h1>${blogHost.userNickname}的博客</h1>
			<h3>${empty blogHost.introduction?"这个人真懒,什么都没写!":blogHost.introduction}</h3>
		</div>
		<div class="main_content">
			<div class="main_left">
				<ul class="list-group new-group">
					<li class="list-group-item new-item">
						<ul class="list-group">
							<li href="javascript:;" class="list-group-item first">个人资料</li>
						  	<li href="javascript:;" class="list-group-item">
						  		<div class="myinfo">
						  			<div class="myportrait">
										<a href="personalInfo.action?userId=${blogHost.userId}"><img src="${blogHost.userImg}"></a>
									</div>
									<div class="myname">
							  			<a href="personalInfo.action?userId=${blogHost.userId}" id="myName" href="javascript:;">${blogHost.userNickname}</a>
									</div>
						  		</div>
						  	</li>
						  	<li href="javascript:;" class="list-group-item">
						  		<div style="width:100%;height: 30px;">
							  		<span class="blogHostInfo">访问:${blogHost.visitedNum}</span>
							  		<span class="blogHostInfo">文章:<s:property value="#blogHost.articlesForAuthorId.size()"/></span>
						  		</div>
						  		<div style="width:100%;height: 30px;">
						  			<span class="blogHostInfo">关注:<s:property value="#blogHost.attentionsForFollower.size()"/></span>
						  			<span class="blogHostInfo">粉丝:<s:property value="#blogHost.attentionsForUser.size()"/></span>
						  		</div>
						  	</li>
						</ul>
					</li>
					<li class="list-group-item new-item">
						<ul class = "list-group">
							<li href="javascript:;" class="list-group-item first">文章搜索</li>
						  	<li href="javascript:;" class="list-group-item">
								<div class="input-group">
							      <input type="text" id="articleName" class="form-control" placeholder="请输入搜索内容">
							      <span class="input-group-btn">
							        <button id="searchArticle" class="btn btn-default" type="button">搜索</button>
							      </span>
							    </div>
						  	</li>
						</ul>
					</li>
					<li class="list-group-item new-item">
						<ul class = "list-group">
							<li class="list-group-item first">文章分类</li>
							<li class="list-group-item">
								<a href="myBlogView.action?userId=${blogHost.userId}">全部文章</a>
								<span class="badge"><s:property value="#blogHost.articlesForAuthorId.size()"/></span>
							</li>
								
							<s:iterator value="#blogHost.personalClassifications" var="bcl">
								<s:set var="bcl" value="#bcl"></s:set>
								<li class="list-group-item">
									<a class="myArticleClass" href="javascript:;" personalClass="${bcl.classificationId}"><s:property value="#bcl.classificationName"/></a>
									<span class="badge"><s:property value="#bcl.articleClasses.size()"/></span>
									<s:iterator value="#bcl.articleClasses" var="a">
										<s:property value="#a.articleId"/>
									</s:iterator>
								</li>
							</s:iterator>
						</ul>
					</li>
					<li class="list-group-item new-item">
						<ul class = "list-group">
							<li class="list-group-item first">文章存档</li>
							<c:forEach items="${articlesData.dateGroups}" var="d">
								<li class="list-group-item">
									<a href="javascript:;" class="dateClass" myDateGroup="${d.year}-${d.month}">${d.year}年${d.month}月</a>
									<span class="badge">${d.articleNumber}</span>
								</li>
							</c:forEach>
						</ul>
					</li>
					<li class="list-group-item new-item">
						<ul class = "list-group">
							<li href="javascript:;" class="list-group-item first">阅读排行</li>
							<c:forEach items="${articlesData.readNumDesc}" var="rac">
								<li class="list-group-item">
									<a class="articleDetail" articleId="${rac[0]}" href="javascript:;">${rac[1]}</a>
									<span class="badge">${rac[2]}</span>
								</li>
							</c:forEach>
						</ul>
					</li>
					<li class="list-group-item new-item">
						<ul class = "list-group">
							<li href="javascript:;" class="list-group-item first">评论排行</li>
							<c:forEach items="${articlesData.commentDesc}" var="cac">
								<li class="list-group-item">
									<a class="articleDetail" articleId="${cac[0]}" href="javascript:;">${cac[1]}</a>
									<span class="badge">${cac[2]}</span>
								</li>
							</c:forEach>
						</ul>
					</li>
				</ul>
			</div>
			<div id="right" class = "main_right">
				<!-- 两个都引用，哪个里面有内容就显示 -->
				<%@ include file="myBlogList.jsp"%>
				<%@ include file="articleDetail.jsp"%>
			</div>
		</div>
	</div>
	<%@ include file="tail.jsp" %>
</body>
</html>