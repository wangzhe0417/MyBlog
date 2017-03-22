<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/blog/frame/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/blog/css/index.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/blog/js/jquery.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/blog/frame/bootstrap/js/bootstrap.js"></script>
<div>
	<div class="head">
		<div class="head_main">
			<div class="logo">
				<img src="/MyBlog/blog/images/icon.jpg"> <a id="gohome"
					href="index.action">JYH博客</a>
			</div>
			<div class="head_search">
				<form action="searchAllArticle.action" method="post" target="_blank">
					<div class="input-group">
						<input id="searchKey" name="queryCondition" type="text" class="form-control" placeholder="请输入搜索内容">
						<span class="input-group-btn">
							<button id="globalSearch" class="btn btn-default" type="submit">搜索</button>
						</span>
					</div>
				</form>
			</div>
			<div class="option">
				<ul class="nav nav-pills">
					<s:if test="#session.user == null">
						<li role="presentation"><a
							href="${pageContext.request.contextPath}/loginView.action">登录/注册</a></li>
					</s:if>
					<s:if test="#session.user != null">
						<li role="presentation"><a
							href="${pageContext.request.contextPath}/myBlogView.action?userId=${sessionScope.user.userId}">我的博客</a></li>
						<li role="presentation"><a
							href="${pageContext.request.contextPath}/personalInfo.action?userId=${sessionScope.user.userId}">个人中心</a></li>
						<li role="presentation"><a
							href="${pageContext.request.contextPath}/blogManage.action">博客管理</a></li>
						<li role="presentation"><a
							href="${pageContext.request.contextPath}/writeArticle.action"
							target="_blank">写博客</a></li>
						<li role="presentation"><a href="${pageContext.request.contextPath}/quit.action">退出</a></li>
					</s:if>
				</ul>
			</div>
		</div>
	</div>
	<div id="main" class="main">