<%-- 权限模块:用户管理:添加或修改页面 --%>
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
	<form id="form" method="post" action="/pms/addPmsUser" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
		    <input type="hidden" name="navTabId" value="listPmsUser">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			
			<p style="width:99%">
				<label>用户姓名：</label>
				<input type="text" name="userName" class="required" minlength="2" maxlength="45" size="30" />
			</p>
			<p style="width:99%">
				<label>用户登录名：</label>
				<input type="text" name="userNo" class="required" maxlength="30" size="30" />
			</p>

			<p style="width:99%">
				<label>密码：</label>
				<input type="password" name="userPwd" class="required" maxlength="20" size="30" />
				<span class="info"></span>
			</p>

			<p style="width:99%">
				<label>手机号码：</label>
				<input type="text" name="mobileNo" class="required mobile"  maxlength="12" size="30" />
			</p>
			<p style="width:99%">
				<label>状态：</label>
				<select name="status" class="required combox">
					<option value="">-请选择-</option>
					<c:forEach items="${UserStatusEnumList}" var="userStatus">
						<option value="${userStatus.value}">
							${userStatus.desc}
						</option>
					</c:forEach>
				</select>
				<font color="red">*</font>
			</p>
			<p style="width:99%">
				<label>用户类型：</label>
				普通用户
			</p>
			<p style="width:99%;height:50px;">
				<label>描述：</label>
				<textarea name="desc" class="required" maxlength="100" rows="3" cols="30"></textarea>
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