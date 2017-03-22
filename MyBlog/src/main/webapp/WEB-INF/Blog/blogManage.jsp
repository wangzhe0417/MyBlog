<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title id="t">博客管理</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/blog/frame/bootstrapValidator/bootstrapValidator.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/blog/css/blogManage.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/blog/js/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/blog/js/vue.js"></script>
</head>
<body>
	<%@ include file="head.jsp"%>
	<div id="bloManage" class="min-main">
		<div class="main_content">
			<div style="background:#fff">
				<ul id="myTab" class="nav nav-tabs">
					<li class="manageView active">
						<a href="#articleManage" @click="findMyArticles" data-toggle="tab">文章管理</a>
					</li>
					<li class="manageView">
						<a href="#commentManage" @click="findMyComments" data-toggle="tab">评论管理</a>
					</li>
					<li class="manageView">
						<a href="#classManage" @click="findMyClass" data-toggle="tab">类别管理</a>
					</li>
					<li class="manageView">
						<a href="#letterManage" @click="findMyLetter" data-toggle="tab">私信管理</a>
					</li>
					<li class="manageView">
						<a href="#accountSetting" data-toggle="tab">账号设置</a>
					</li>
				</ul>
				<div id="myTabContent" class="tab-content">
					<div class="tab-pane fade in active" id="articleManage">
						<table class="table table-striped table-hover">
							<thead>
								<th style="width:55%">标题</th>
								<th style="text-align:center;width:50px;">阅读</th>
								<th style="text-align:center;width:50px;">评论</th>
								<th style="text-align:center;width:150px;">操作</th>
							</thead>
							<tbody>
								<tr v-for="a in articles">
									<td style="text-align:left"><a :href="gotoArticle(a[0])" target="_blank">{{a[1]}}</a></td>
									<td>{{a[4]}}</td>
									<td>{{a[6]}}</td>
									<td>
										<a :href="gotoWriteArticle(a[0])" target="_blank">编辑</a>&nbsp;&nbsp;|&nbsp;&nbsp;
										<a @click="deleteArticle(a[0])">删除</a>
									</td>
								</tr>
							</tbody>
						</table>
						<ul class="pagination" v-if="page.totalPage > 1">
							<li><a href="javascript:;" @click="goPage(1)">首页</a></li>
							<li><a href="javascript:;" @click="goPage(page.lastPageNum)">上一页</a></li>
							<!-- style="background-color:#eee" -->
							<li v-for="i in getNumInterval()">
								<a v-if="i==page.currentPageNum" style="background-color:#eee" @click="goPage(i)">{{i}}</a>
								<a v-if="i!=page.currentPageNum" @click="goPage(i)">{{i}}</a>
							</li>
							<li><a href="javascript:;" @click="goPage(page.nextPageNum)">下一页</a></li>
							<li><a href="javascript:;" @click="goPage(page.totalPage)">尾页({{page.totalPage}})</a></li>
						</ul>
					</div>
					<div class="tab-pane fade" id="commentManage">
						<div class="tableOption">
							<div class="select">
								<a @click="findMyComments" :class="{'a-focus':classes.f}">我发表的评论</a>&nbsp;|&nbsp;
								<a @click="findMyArticleComments" :class="{'a-focus':classes.s}">我文章的评论</a>
							</div>
						</div>
						<table class="table table-striped table-hover">
							<thead>
								<th style="width:100px;">评论文章</th>
								<th style="text-align:center;">评论内容</th>
								<th style="text-align:center;width:120px;">作者</th>
								<th style="text-align:center;width:150px;">操作</th>
							</thead>
							<tbody>
								<tr v-for="c in comments">
									<td style="text-align:left"><a :href="gotoArticle(c[2])" target="_blank">{{c[3]}}</a></td>
									<td><div>{{c[1]}}</div></td>
									<td><a :href="gotoUser(c[4])" target="_blank">{{c[5]}}</a></td>
									<td><a @click="deleteComment(c[0])">删除</a>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="tab-pane fade" id="classManage">
						<table class="table table-striped table-hover">
							<thead>
								<th>类别</th>
								<th style="text-align:center;width:120px;">文章</th>
								<th style="text-align:center;width:150px;">操作</th>
							</thead>
							<tbody>
								<tr v-for="(c,index) in classes">
									<td style="text-align:left">{{c[1]}}</td>
									<td>{{c[2]}}</td>
									<td><a @click="editMyClass(index)">编辑</a>&nbsp;&nbsp;|&nbsp;&nbsp;
									<a @click="deleteMyClass(c[0])">删除</a></td>
								</tr>
							</tbody>
						</table>
						<!-- 编辑类别弹窗开始 -->
						<div class="modal fade" id="editMyClass" tabindex="-1" role="dialog"
							aria-labelledby="myModalLabel">
							<div class="modal-dialog">
								<div class="modal-content" style="width: 500px;">
									<div class="modal-header" style="margin-bottom:15px;">
										<button data-dismiss="modal" class="close" type="button">
											<span aria-hidden="true">×</span><span class="sr-only">Close</span>
										</button>
										<h4 class="modal-title">我的分类</h4>
									</div>
									<div class="form-horizontal col-lg-offset-2">
						                 <div class="form-group">
						                    <div class="col-lg-9">
						                        <input type="text" v-model="thisClass['pClassification.classificationName']" class="form-control" @keyup.enter="submitMyClass"/>
						                    </div>
						                </div>
						            </div>
									<div class="modal-footer">
										<button data-dismiss="modal" class="btn btn-default"
											type="button">关闭</button>
										<button class="btn btn-primary" @click="submitMyClass()" type="button">提交</button>
									</div>
								</div>
								<!-- /.modal-content -->
							</div>
							<!-- /.modal-dialog -->
						</div>
						<!-- 编辑类别弹窗结束 -->
					</div>
					
					<div class="tab-pane fade" id="letterManage">
						<div class="tableOption">
							<div class="select">
								<a @click="findMySendLetters" :class="{'a-focus':classes.f}">我发送的私信</a>&nbsp;|&nbsp;
								<a @click="findMyReceiveLetters" :class="{'a-focus':classes.s}">我收到的私信</a>
							</div>
						</div>
						<table :class="{'table table-striped table-hover':classes.f,'makeHidden':classes.s}" id="mySendLetters">
							<thead>
								<th style="width:100px;">接受者</th>
								<th style="text-align:center;width:500px;">私信内容</th>
								<th style="text-align:center;width:100px;">发送时间</th>
								<th style="text-align:center;width:100px;">操作</th>
							</thead>
							<tbody>
								<tr v-for="l in mySendLetters">
									<td style="text-align:left">
										<a :href="gotoUser(l[2])" target="_blank">{{l[3]}}</a>
									</td>
									<td><div>{{l[1]}}</div></td>
									<td>{{l[4] | time}}</td>
									<td><a @click="deleteMySendLetter(l[0])">删除</a>
								</tr>
							</tbody>
						</table>
						
						<ul :class="{'list-group':classes.s,'makeHidden':classes.f}" id="myReceiveLetters">
							<li class="list-group-item" v-for="l in myReceiveLetters">
								<div id="accordion" role="tablist" aria-multiselectable="true">
									<div>
										<div class="pre" style="padding:10px 15px;">
											<h4 class="panel-title">
												<div class="comment">
													<div class="comment_user">
														<a :href="gotoUser(l[2])" target="_blank">
															<img :src="l[4]">
														</a>
														<a :href="gotoUser(l[2])" target="_blank"
															style="display:block;text-align:center;">
															{{l[3]}} </a>
													</div>
													<div class="comment_content">
														{{l[1]}}
													</div>
													<div class="comment_option">
														<a class="reply" role="button" data-toggle="collapse" data-parent="#accordion" :href="getH(l[0])" aria-expanded="true" aria-controls="collapseOne"> 回复 </a>&nbsp;&nbsp;&nbsp; 
														<span>{{l[5] | time}}</span>
													</div>
												</div>
											</h4>
										</div>
										<div :id="l[0]" class="isscroll panel-collapse collapse">
											<textarea class="form-control" v-model="letter" rows="4"></textarea>
											<button type="button" class="btn btn-default" style="margin-top:5px;" @click="submitLetter(l[2])">发送</button>
										</div>
									</div>
								</div>
							</li>
						</ul>
					</div>
					<div class="tab-pane fade" id="accountSetting">
						<div class="container">
						    <div class="row">
						        <div class="col-lg-8 col-lg-offset-2">
						            <div class="page-header">
						                <h2>账号设置</h2>
						            </div>
						
						            <form id="accountForm" class="form-horizontal">
						                <div class="form-group">
						                    <label class="col-lg-3 control-label">用户名:</label>
						                    <div class="col-lg-5">
						                        <input type="text" required v-model="user['user.userName']" class="form-control" name="username" />
						                    </div>
						                </div>
						
						                <div class="form-group">
						                    <label class="col-lg-3 control-label">密码:</label>
						                    <div class="col-lg-5">
						                        <input type="password" required v-model="user['user.password']" class="form-control" name="password" />
						                    </div>
						                </div>
						
						                <div class="form-group">
						                    <div class="col-lg-9 col-lg-offset-3">
						                        <button type="submit" @click="submitAccount" class="btn btn-primary">确定</button>
						                    </div>
						                </div>
						            </form>
						        </div>
						    </div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/blog/frame/bootstrapValidator/bootstrapValidator.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/blog/js/blogManage.js"></script>
	<%@ include file="tail.jsp"%>
</body>
</html>