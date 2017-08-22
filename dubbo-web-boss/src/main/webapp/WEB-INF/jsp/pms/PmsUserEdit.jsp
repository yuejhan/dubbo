<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../inc/taglib.jsp"%>
<style>
<!--
.pageFormContent fieldset label{
	width: 200px;
}
-->
</style>
<div class="pageContent">
	<form id="form" method="post" action="/pms/editPmsUser" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
		    <input type="hidden" name="navTabId" value="listPmsUser">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			
			<input type="hidden" id="userId" name="userId" value="${user.id}"/>
			<p style="width:99%">
				<label>用户姓名：</label>
				<input type="text" name="userName" class="required" value="${user.userName}" minlength="2" maxlength="15" size="30" />
			</p>
			<p style="width:99%">
				<label>用户登录名：</label>
				<input type="text" name="userNo" class="required" value="${user.userNo}" readonly="true" minlength="3" maxlength="30" size="30" />
			</p>
			<p style="width:99%">
				<label>手机号码：</label>
				<input type="text" name="mobileNo" value="${user.mobileNo}" class="required mobile"  maxlength="12" size="30" />
			</p>
			<p style="width:99%">
				<label>状态：</label>
				<c:choose>
					<c:when test="${user.status eq UserStatusEnum.ACTIVE.value}">激活</c:when>
					<c:when test="${user.status eq UserStatusEnum.INACTIVE.value}">冻结</c:when>
					<c:otherwise>--</c:otherwise>
				</c:choose>
			</p>
			<p style="width:99%">
				<label>用户类型：</label>
				<c:choose>
					<c:when test="${user.userType eq UserTypeEnum.ADMIN.value }">普通用户</c:when>
					<c:when test="${user.userType eq UserTypeEnum.SUPER_ADMIN.value }">超级管理员</c:when>
					<c:otherwise>--</c:otherwise>
				</c:choose>
			</p>
			<p style="width:99%;height:50px;">
				<label>描述：</label>
				<textarea name="desc" maxlength="100" rows="3" cols="30">${user.remark}</textarea>
			</p>
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="submitForm()">保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">
	function submitForm() {
		$("#form").submit();
	}
	
</script>