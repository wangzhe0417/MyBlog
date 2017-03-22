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

	<%@ include file="head.jsp"%>
<head>
<title id="t">个人中心</title>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/blog/frame/bootstrap/css/bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/blog/css/personalInfo.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/blog/frame/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/blog/frame/layer/skin/layer.css"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/blog/js/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/blog/js/bootstrap.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/blog/frame/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/blog/frame/bootstrap-datetimepicker/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/blog/frame/layer/layer.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/blog/frame/layer/laytpl.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/blog/js/imgUpload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/blog/js/vue.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/blog/js/province_city.js"></script>
<style type="text/css">
.datetimepicker  td, .datetimepicker th{
    text-align: center;
    width: 36px;
    height: 20px;
    -webkit-border-radius: 4px;
    -moz-border-radius: 4px;
    border-radius: 4px;
    border: none;
}
</style>
</head>
<body>
	<input id="thisUserId" value="${sessionScope.user.userId}" style="display:none"/>
	<div id="personal" class="min-main">
		<div class="main_content">
			<ul class="list-group new-group">
				<li class="list-group-item new-item">
					<div class="panel panel-default"  id="userInfo">
						<div class="panel-body" style="padding:12px">
							<div class="user">
								<div v-if="validateUser()" @click="uploadImg" class="userimg">
									<a id="my-img" href="#"><img v-bind:src="user.userImg"></a>
								</div>
								<div v-if="!validateUser()" class="userimg">
									<a id="my-img" href="#"><img v-bind:src="user.userImg"></a>
								</div>
								<div class="fansandfollow">
									<button v-if="attentionId != 1 && !validateUser()" @click="cancelAttention" class="btn btn-default">取消关注</button>
									<button v-if="attentionId == 1 && !validateUser()" @click="attentionUser" class="btn btn-default">关注</button>
									<button v-if="!validateUser()" class="btn btn-default" @click="sendLetter">私信</button>
									<button v-if="validateUser()" class="btn btn-default">
										<a href="${pageContext.request.contextPath}/blogManage.action">博客管理</a>
									</button>
								</div>
							</div>
							<!-- 发送私信弹窗开始 -->
							<div class="modal fade" id="sendLetter" tabindex="-1" role="dialog"
								aria-labelledby="myModalLabel">
								<div class="modal-dialog">
									<div class="modal-content" style="width: 450px;">
										<div class="modal-header" style="margin-bottom:15px;">
											<button data-dismiss="modal" class="close" type="button">
												<span aria-hidden="true">×</span><span class="sr-only">Close</span>
											</button>
											<h4 class="modal-title">发送私信</h4>
										</div>
										<form class="form-horizontal col-lg-offset-0">
							                <div class="form-group">
						                        <div class="col-lg-offset-2 col-lg-8">
						                            <textarea class="form-control" name="introduction" v-model="letter" rows="6"></textarea>
						                        </div>
						                    </div>
							            </form>
										
										<div class="modal-footer">
											<button data-dismiss="modal" class="btn btn-default"
												type="button">关闭</button>
											<button class="btn btn-primary" @click="submitLetter()" type="button">提交</button>
										</div>
									</div>
									<!-- /.modal-content -->
								</div>
								<!-- /.modal-dialog -->
							</div>
							<!-- 发送私信弹窗结束 -->
							
							<div class="basicdata">
								<a href="javascript:;" v-if="validateUser()" v-on:click="baseInfoEdit">
									<span class="glyphicon glyphicon-edit"></span>
								</a>
								<div class="nickname">
									
									<h2>{{user.userNickname}}</h2>
								</div>
								<div class="userdata">
									<h4>
										<span>实名:</span><span>{{user.userTruename}}</span>|
										<span>出生年月:</span><span>{{user.birthday}}</span>|
										<span>性别:</span><span>{{user.gender==0?'女':'男'}}</span>
									</h4>
									<h4>
										<span>住址:</span><span>{{user.myProvince}}-{{user.myCity}}</span>| 
										<span>行业:</span><span>{{user.industry}}</span>|
										<span>职位:</span><span>{{user.career}}</span>
									</h4>
								</div>
								<div class="brief">{{user.introduction}}</div>
							</div>
							
							<!-- 编辑基本信息弹窗开始 -->
							<div class="modal fade" id="editUserInfo" tabindex="-1" role="dialog"
								aria-labelledby="myModalLabel">
								<div class="modal-dialog">
									<div class="modal-content" style="width: 650px;">
										<div class="modal-header" style="margin-bottom:15px;">
											<button data-dismiss="modal" class="close" type="button">
												<span aria-hidden="true">×</span><span class="sr-only">Close</span>
											</button>
											<h4 class="modal-title">编辑基本信息</h4>
										</div>
										<form class="form-horizontal col-lg-offset-0" style="padding-right: 60px;">
							                <div class="form-group">
							                	<div class="group">
						                            <label class="col-sm-2 control-label">昵称:</label>
						                            <div class="col-sm-4">
						                                <input type="text" required class="form-control" v-model="user.userNickname" name="userNickname"/>
						                            </div>
						                        </div>
						                        <div class="group">
						                            <label class="col-sm-2 control-label">实名:</label>
						                            <div class="col-sm-4">
						                                <input type="text" required class="form-control" v-model="user.userTruename" name="userTruename"/>
						                            </div>
						                        </div>
							                </div>
							                <div class="form-group">
						                        <div class="group">
						                            <label class="col-sm-2 control-label">性别:</label>
						                            <div class="col-sm-4">
						                                <label>
					                                        <input type="radio" value="0" v-model="user.gender" name="gender"/> 女
					                                    </label>
					                                    <label>
					                                        <input type="radio" value="1" v-model="user.gender" name="gender"/> 男
					                                    </label>
						                            </div>
						                        </div>
						                        <div class="group">
						                            <label class="col-sm-2 control-label">出生日期:</label>
						                            <div class="col-sm-4">
						                                <div v-on:click="shwoTime" class="input-group date" id="datetimePicker">
															<input  @change="birthdayChange" type="text" id="birthday" :value="user.birthday" class="form-control"/> 
															<span class="input-group-addon">
																<span class="glyphicon glyphicon-calendar"></span>
															</span>
														</div>
						                            </div>
						                        </div>
							                </div>
							                <div class="form-group">
							                	<div class="group">
						                            <label class="col-sm-2 control-label">省:</label>
						                            <div class="col-sm-4">
						                            	<select required class="form-control" v-model="user.myProvince" @change="selectProvince()">
					                                        <option :value="p.pv" :id="p.pk" v-for="p in pros">{{p.pv}}</option>
					                                    </select>
						                            </div>
						                        </div>
						                        <div class="group">
						                            <label class="col-sm-2 control-label">市:</label>
						                            <div class="col-sm-4">
						                                 <select required class="form-control" v-model="user.myCity">
					                                        <option :value="c.cv" :id="c.ck" v-for="c in citys">{{c.cv}}</option>
					                                    </select>
						                            </div>
						                        </div>
							                </div>
							                <div class="form-group">
							                	 <div class="group">
						                            <label class="col-sm-2 control-label">行业:</label>
						                            <div class="col-sm-4">
						                                 <input required type="text" class="form-control" name="industry" v-model="user.industry"/>
						                            </div>
						                        </div>
						                        <div class="group">
						                            <label class="col-sm-2 control-label">职位:</label>
						                            <div class="col-sm-4">
						                                 <input required type="text" class="form-control" v-model="user.career"/>
						                            </div>
						                        </div>
							                </div>
							                 <div class="form-group">
						                        <label class="col-lg-2 control-label">简介:</label>
						                        <div class="col-lg-7">
						                            <textarea required class="form-control" name="introduction" v-model="user.introduction" rows="5"></textarea>
						                        </div>
						                    </div>
							            </form>
										
										<div class="modal-footer">
											<button data-dismiss="modal" class="btn btn-default"
												type="button">关闭</button>
											<button class="btn btn-primary" @click="submitBaseInfo()" type="button">提交</button>
										</div>
									</div>
									<!-- /.modal-content -->
								</div>
								<!-- /.modal-dialog -->
							</div>
							<!-- 编辑基本信息弹窗结束 -->
							
						</div>
					</div>
				</li>
				<li class="list-group-item new-item"
					style="margin-top:10px;font-size:17px">
					
					<!-- 中间的导航按钮开始 -->
					<ul id="myTab" class="nav nav-tabs">
						<li class="active"><a href="#myData" data-toggle="tab">我的资料</a></li>
						<li><a href="#myDynamic" @click="getMyDynamic" data-toggle="tab">我的动态</a></li>
						<li><a href="#myRelationship" @click="getMyRelationship" data-toggle="tab">我的关系</a></li>
						<li><a href="#myCollection" @click="getMyCollection" data-toggle="tab">我的收藏</a></li>
					</ul>
					<!-- 中间的导航按钮结束 -->
					
					<!-- 下方的主要信息展示开始 -->
					<div id="myTabContent" class="tab-content">
						<!-- 我的基本信息开始 -->
						<div class="tab-pane fade in active" id="myData">
							<ul class="list-group new-group">
								<li class="list-group-item first" style="border-top: 0px;">联系方式</li>
								<li class="list-group-item contact">
									<a href="javascript:;" class="first-edit" v-if="validateUser()" v-on:click="contactEdit">
										<span class="glyphicon glyphicon-edit"></span>
									</a>
									<div class="panel-body">
										<div>
											<span>电子邮箱:</span><span>{{user.email}}</span>
										</div>
										<div>
											<span>手机号码:</span><span>{{user.telphone}}</span>
										</div>
									</div>
									<div class="panel-body">
										<div>
											<span>qq号码:</span><span>{{user.qq}}</span>
										</div>
										<div>
											<span>微信账号:</span><span>{{user.wechat}}</span>
										</div>
									</div>
								</li>
								<li class="list-group-item first">熟悉的领域</li>
								<li class="list-group-item field">
									<div class="panel-body">
										<div class="labelDiv" v-if="k != ''" v-for="(k,index) in user.knowField" >
											<span class="myLabel" href="#">{{k}}</span> 
											<a class="del" v-on:click='delField(index)' v-if="validateUser()">
												<span class="glyphicon glyphicon-trash"></span>
											</a>
										</div>
										<div class="labelDiv" v-if="validateUser()" v-on:click="addField">
						      				<span class="myLabel labeladd">+</span>
						      			</div>
									</div>
								</li>
								<li class="list-group-item first">专业技能</li>
								<li class="list-group-item professional">
									<div class="panel-body">
										<div class="labelDiv" v-if="p != ''" v-for="(p,index) in user.professionalSkill" >
											<span class="myLabel">{{p}}</span> 
											<a class="del" v-on:click="delProfessional(index)" v-if="validateUser()">
												<span class="glyphicon glyphicon-trash"></span>
											</a>
										</div>
										<div class="labelDiv" v-if="validateUser()" v-on:click="addProfessional">
						      				<span class="myLabel labeladd">+</span>
						      			</div>
									</div>
								</li>
								<li class="list-group-item first">教育背景
								<a class="add" v-if="validateUser()" v-on:click="addOreditEducation(-1)">
									<span class="glyphicon glyphicon-plus"></span></a>
								</li>
								<li class="list-group-item">
									<ul class="list-group">
										<li class="list-group-item" v-for="(e,index) in educations">
											<div class="panel-body education">
												<div class="info">
													<span>时间:</span><span>{{e.startDate | date}}到{{e.endDate | date}}</span>&nbsp;&nbsp;|&nbsp;
													<span>学历:</span><span>{{e.education}}</span>&nbsp;&nbsp;|&nbsp; 
													<span>学校:</span><span>{{e.school}}</span>&nbsp;&nbsp;|&nbsp;
													<span>专业:</span><span>{{e.professional}}</span>
												</div>
												<div class="myinfo-option">
													<a class="delete" v-if="validateUser()" v-on:click="delEducation(e.educationId)">
														<span class="glyphicon glyphicon-trash"></span>
													</a>
													<a class="edit" v-if="validateUser()" v-on:click="addOreditEducation(index)">
														<span class="glyphicon glyphicon-edit"></span>
													</a>
												</div>
											</div>
										</li>
									</ul>
								</li>
								<li class="list-group-item first">工作经历
								<a class="add" v-if="validateUser()" v-on:click="addOrEditWork(-1)">
									<span class="glyphicon glyphicon-plus"></span>
								</a>
								</li>
								<li class="list-group-item">
									<ul class="list-group">
										<li class="list-group-item" v-for="(w,index) in works">
											<div class="panel-body work">
												<div class="info">
													<span>时间:</span><span>{{w.startDate | date}}到{{w.endDate | date}}</span>&nbsp;&nbsp;|&nbsp;
													<span>公司:</span><span>{{w.company}}</span>&nbsp;&nbsp;|&nbsp; 
													<span>职位:</span><span>{{w.position}}</span><br>
													<span>简述:</span><span>{{w.briefing}}</span>
												</div>
												<div class="myinfo-option">
													<a class="delete" v-if="validateUser()" v-on:click="delWork(w.workId)">
														<span class="glyphicon glyphicon-trash"></span>
													</a> 
													<a class="edit" v-if="validateUser()" v-on:click="addOrEditWork(index)">
														<span class="glyphicon glyphicon-edit"></span>
													</a>
												</div>
											</div>
										</li>
									</ul>
								</li>
							</ul>
							
							<!-- 我的基本信息中的弹框开始 -->
							<div>
								<!-- 编辑联系信息弹窗开始 -->
								<div class="modal fade" id="editContact" tabindex="-1" role="dialog"
									aria-labelledby="myModalLabel">
									<div class="modal-dialog">
										<div class="modal-content" style="width: 500px;">
											<div class="modal-header" style="margin-bottom:15px;">
												<button data-dismiss="modal" class="close" type="button">
													<span aria-hidden="true">×</span><span class="sr-only">Close</span>
												</button>
												<h4 class="modal-title">联系方式</h4>
											</div>
											
											<div class="form-horizontal col-lg-offset-2">
								                 <div class="form-group">
								                    <label class="col-lg-3 control-label">电子邮箱:</label>
								                    <div class="col-lg-6">
								                        <input type="text" name="email" type="email" 
								                        class="form-control" v-model="user.email"/>
								                    </div>
								                </div>
								                <div class="form-group">
								                    <label class="col-lg-3 control-label">手机号:</label>
								                    <div class="col-lg-6">
								                        <input type="text" class="form-control" name="telphone" v-model="user.telphone"/>
								                    </div>
								                </div>
								                <div class="form-group">
								                    <label class="col-lg-3 control-label">qq号:</label>
								                    <div class="col-lg-6">
								                        <input type="text" class="form-control" name="qq" v-model="user.qq"/>
								                    </div>
								                </div>
								                <div class="form-group">
								                    <label class="col-lg-3 control-label">微信号:</label>
								                    <div class="col-lg-6">
								                        <input type="text" class="form-control" name="wechat" v-model="user.wechat"/>
								                    </div>
								                </div>
								            </div>
											
											<div class="modal-footer">
												<button data-dismiss="modal" class="btn btn-default"
													type="button">关闭</button>
												<button class="btn btn-primary" @click="submitContact()" type="button">提交</button>
											</div>
										</div>
										<!-- /.modal-content -->
									</div>
									<!-- /.modal-dialog -->
								</div>
								<!-- 编辑联系信息弹窗结束 -->
								
								<!-- 编辑熟悉的领域弹窗开始 -->
								<div class="modal fade" id="addField" tabindex="-1" role="dialog"
									aria-labelledby="myModalLabel">
									<div class="modal-dialog">
										<div class="modal-content" style="width: 500px;">
											<div class="modal-header" style="margin-bottom:15px;">
												<button data-dismiss="modal" class="close" type="button">
													<span aria-hidden="true">×</span><span class="sr-only">Close</span>
												</button>
												<h4 class="modal-title">熟悉的领域</h4>
											</div>
											<div class="form-horizontal col-lg-offset-2">
								                 <div class="form-group">
								                    <div class="col-lg-9">
								                        <input type="text" v-model="field" class="form-control" @keyup.enter="submitField"/>
								                    </div>
								                </div>
								            </div>
											<div class="modal-footer">
												<button data-dismiss="modal" class="btn btn-default"
													type="button">关闭</button>
												<button class="btn btn-primary" @click="submitField()" type="button">提交</button>
											</div>
										</div>
										<!-- /.modal-content -->
									</div>
									<!-- /.modal-dialog -->
								</div>
								<!-- 编辑熟悉的领域弹窗结束 -->
								
								<!-- 编辑专业技能弹窗开始 -->
								<div class="modal fade" id="addProfessional" tabindex="-1" role="dialog"
									aria-labelledby="myModalLabel">
									<div class="modal-dialog">
										<div class="modal-content" style="width: 500px;">
											<div class="modal-header" style="margin-bottom:15px;">
												<button data-dismiss="modal" class="close" type="button">
													<span aria-hidden="true">×</span><span class="sr-only">Close</span>
												</button>
												<h4 class="modal-title">专业技能</h4>
											</div>
											<div class="form-horizontal col-lg-offset-2">
								                 <div class="form-group">
								                    <div class="col-lg-9">
								                        <input type="text" v-model="professional" v-on:keyup.enter="submitProfessional"
								                         class="form-control"/>
								                    </div>
								                </div>
								            </div>
											<div class="modal-footer">
												<button data-dismiss="modal" class="btn btn-default"
													type="button">关闭</button>
												<button class="btn btn-primary" @click="submitProfessional" type="button">提交</button>
											</div>
										</div>
										<!-- /.modal-content -->
									</div>
									<!-- /.modal-dialog -->
								</div>
								<!-- 编辑专业技能弹窗结束 -->
							</div>
							<!-- 我的基本信息中的弹框结束 -->
							
						</div>
						<!-- 我的基本信息结束 -->
						
						<!-- 编辑教育背景弹窗开始 -->
						<div class="modal fade" id="addOrEditEducation" tabindex="-1" role="dialog"
							aria-labelledby="myModalLabel">
							<div class="modal-dialog">
								<div class="modal-content" style="width: 500px;">
									<div class="modal-header" style="margin-bottom:15px;">
										<button data-dismiss="modal" class="close" type="button">
											<span aria-hidden="true">×</span><span class="sr-only">Close</span>
										</button>
										<h4 class="modal-title">教育背景</h4>
									</div>
									
									<div class="form-horizontal col-lg-offset-2">
						                 <div class="form-group">
						                    <label class="col-lg-3 control-label">开始时间:</label>
						                    <div class="col-lg-6">
						                    	<div v-on:click="shwoStartTime" class="input-group date" id="educatoin_start">
													<input readonly type="text" v-model="e['education.startDate']" class="form-control"/> 
													<span class="input-group-addon">
														<span class="glyphicon glyphicon-calendar"></span>
													</span>
												</div>
						                    </div>
						                </div>
						                <div class="form-group">
						                    <label class="col-lg-3 control-label">结束时间:</label>
						                    <div class="col-lg-6">
						                        <div v-on:click="shwoEndTime" class="input-group date" id="educatoin_end">
													<input readonly type="text" v-model="e['education.endDate']" class="form-control"/> 
													<span class="input-group-addon">
														<span class="glyphicon glyphicon-calendar"></span>
													</span>
												</div>
						                    </div>
						                </div>
						                <div class="form-group">
						                    <label class="col-lg-3 control-label">学历:</label>
						                    <div class="col-lg-6">
						                        <input type="text" class="form-control" v-model="e['education.education']"/>
						                    </div>
						                </div>
						                <div class="form-group">
						                    <label class="col-lg-3 control-label">学校:</label>
						                    <div class="col-lg-6">
						                        <input type="text" class="form-control" v-model="e['education.school']"/>
						                    </div>
						                </div>
						                 <div class="form-group">
						                    <label class="col-lg-3 control-label">专业:</label>
						                    <div class="col-lg-6">
						                        <input type="text" class="form-control" v-model="e['education.professional']"/>
						                    </div>
						                </div>
						            </div>
									
									<div class="modal-footer">
										<button data-dismiss="modal" class="btn btn-default"
											type="button">关闭</button>
										<button class="btn btn-primary" @click="submitEducation()" type="button">提交</button>
									</div>
								</div>
								<!-- /.modal-content -->
							</div>
							<!-- /.modal-dialog -->
						</div>
						<!-- 编辑教育背景弹窗结束 -->
						
						<!-- 编辑工作经历弹窗开始 -->
						<div class="modal fade" id="addOrEditWork" tabindex="-1" role="dialog"
							aria-labelledby="myModalLabel">
							<div class="modal-dialog">
								<div class="modal-content" style="width: 500px;">
									<div class="modal-header" style="margin-bottom:15px;">
										<button data-dismiss="modal" class="close" type="button">
											<span aria-hidden="true">×</span><span class="sr-only">Close</span>
										</button>
										<h4 class="modal-title">工作经历</h4>
									</div>
									
									<div class="form-horizontal col-lg-offset-2">
						                 <div class="form-group">
						                    <label class="col-lg-3 control-label">开始时间:</label>
						                    <div class="col-lg-6">
						                    	<div v-on:click="shwoStartTime" class="input-group date" id="work_start">
													<input readonly type="text" v-model="w['work.startDate']" class="form-control"/> 
													<span class="input-group-addon">
														<span class="glyphicon glyphicon-calendar"></span>
													</span>
												</div>
						                    </div>
						                </div>
						                <div class="form-group">
						                    <label class="col-lg-3 control-label">结束时间:</label>
						                    <div class="col-lg-6">
						                        <div v-on:click="shwoEndTime" class="input-group date" id="work_end">
													<input readonly type="text" v-model="w['work.endDate']" class="form-control"/> 
													<span class="input-group-addon">
														<span class="glyphicon glyphicon-calendar"></span>
													</span>
												</div>
						                    </div>
						                </div>
						                <div class="form-group">
						                    <label class="col-lg-3 control-label">公司:</label>
						                    <div class="col-lg-6">
						                        <input type="text" class="form-control" v-model="w['work.company']"/>
						                    </div>
						                </div>
						                <div class="form-group">
						                    <label class="col-lg-3 control-label">职位:</label>
						                    <div class="col-lg-6">
						                        <input type="text" class="form-control" v-model="w['work.position']"/>
						                    </div>
						                </div>
						                 <div class="form-group">
						                    <label class="col-lg-3 control-label">简述:</label>
						                    <div class="col-lg-6">
						                    	<textarea class="form-control" v-model="w['work.briefing']" rows="5">
						                    	</textarea>
						                    </div>
						                </div>
						            </div>
									
									<div class="modal-footer">
										<button data-dismiss="modal" class="btn btn-default"
											type="button">关闭</button>
										<button class="btn btn-primary" @click="submitWork()" type="button">提交</button>
									</div>
								</div>
								<!-- /.modal-content -->
							</div>
							<!-- /.modal-dialog -->
						</div>
						<!-- 编辑工作经历弹窗结束 -->
						
						
						<!-- 我的动态开始 -->
						<div class="tab-pane fade" id="myDynamic">
							<ul class="list-group">
								<li class="list-group-item" style="border-top:0px;">
									<a :class="{'a-focus':classes[0]}" @click="getMyDynamic(0)">我自己的</a>&nbsp;&nbsp;&nbsp;&nbsp;
									<a :class="{'a-focus':classes[1]}" @click="getMyDynamic(1)">我关注的</a>
								</li>
								
								<li class="list-group-item new-item">
									<ul class="list-group">
										<li v-for="d in dynamic" class="list-group-item">
											<span v-if="d[5]==1">
												<a :href="gotoUser(d[0])" target="_blank">{{d[1]}}</a>在
											</span>
											<span v-if="d[5]==0">
												<a :href="gotoUser(d[0])" target="_blank">{{d[1]}}</a>发布了
											</span>
											
											<a :href="gotoArticle(d[2],d[5])" target="_blank">{{d[3]}}</a>
											
											<span v-if="d[5]==1">发表了评论</span>
											<span class="right-time">{{d[4] | time}}</span>
										</li>
									</ul> 
								</li>
							</ul>
						</div>
						<!-- 我的动态结束 -->
						
						<!-- 我的关系开始 -->
						<div class="tab-pane fade" id="myRelationship">
							<ul class="list-group">
								<li class="list-group-item" style="border-top:0px;">
									<a :class="{'a-focus':classes[0]}" @click="getMyAttention(0)">我关注的</a> &nbsp;&nbsp;&nbsp;
									<a :class="{'a-focus':classes[1]}" @click="getMyAttention(1)">关注我的</a>&nbsp;&nbsp;&nbsp;&nbsp;
									<a :class="{'a-focus':classes[2]}" @click="getMyAttention(2)">互相关注的</a>
								</li>
								<li class="list-group-item new-item">
									<ul class="list-group">
										<li class="list-group-item">
											<div class="panel-body">
												<div class="users" v-for="a in attentions">
													<a :href="gotoUser(a[1])" target="_blank" class="user-img"> 
														<img :src="a[3]">
													</a> 
													<a :href="gotoUser(a[1])" target="_blank">{{a[2]}}</a>
												</div>
											</div>
										</li>
									</ul>
								</li>
							</ul>
						</div>
						<div class="tab-pane fade" id="myCollection">
							<ul class="list-group">
								<li class="list-group-item" v-for="c in collections" style="border-top: 0px;">
									<a :href="gotoArticle(c[0])" target="_blank">{{c[1]}}</a>
									<div class="myinfo-option">
										<a v-if="validateUser()" class="delete" @click="deleteCollection(c[2])"><span class="glyphicon glyphicon-trash"></span></a> 
									</div> 
									<span class="right-time">{{c[3] | time}}</span>
								</li>
							</ul>
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
					</div>
				</li>
			</ul>
		</div>
	</div>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/blog/js/personalInfo.js"></script>
	
	<%@ include file="tail.jsp"%>
	</body>
</html>