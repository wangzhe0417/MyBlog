<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/blog/css/home.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/blog/css/myblog.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/blog/css/bootstrap.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/blog/js/jquery.js"></script>
<script type="text/javascript" src = "${pageContext.request.contextPath}/blog/frame/bootstrap/js/bootstrap.js"></script>

<s:if test="#articleList.size() == 0">
	<div class="panel-body"><font color="red" size="20px;">没有搜索到!</font></div>
</s:if>
<s:if test="#articleList != null && #articleList.size() > 0">
	<div id="searchResultList" style="width:1000px;margin-right:auto;margin-left:auto;margin-top:50px;">
		<div>
			<ul class="list-group new-group">
				<s:iterator value="#articleList" var="searchArticle">
					<s:set value="#searchArticle" var="searchArticle"></s:set>
					<li class="list-group-item new-item">
						<div class="panel-body" style="padding:12px">
							<div class="myblogs">
								<div class="blog_title">
									<a href="articleDetail.action?article.articleId=${searchArticle.articleId}"
									 class="myArticleDetail" target="_blank">
										${searchArticle.articleTitle}
									</a>
									<span style="margin-left:20px;">${searchArticle.tag}</span>
								</div>
								<div class="article_content">
									${searchArticle.articleSummary}
								</div>
								<div class="article_info">
									<span>
										<s:date name="#searchArticle.articleDate" format="yyyy-MM-dd HH:mm" timezone="GMT+00:00"/>
									</span>
								</div>
							</div>
						</div>
					</li>
				</s:iterator>
			</ul>
		</div>
	</div>
</s:if>

