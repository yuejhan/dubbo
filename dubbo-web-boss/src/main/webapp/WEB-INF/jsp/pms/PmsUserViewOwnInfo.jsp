<%-- 用户查看自己帐号信息 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../inc/taglib.jsp"%>
<div class="pageContent">
	<form>
		<div class="pageFormContent" layoutH="60">
			<p style="width:99%">
				<label>登录名：</label>
				${userNo }
			</p>
			<p style="width:99%">
				<label>用户名称：</label>
				${userName }
			</p>
			<p style="width:99%">
				<label>创建时间：</label>
				<fmt:formatDate value="${createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</p>
			<p style="width:99%">
				<label>手机号码：</label>
				${mobileNo }
			</p>
			<p style="width:99%">
				<label>状态：</label>
				<c:forEach items="${UserStatusEnumList}" var="userStatus">
					<c:if test="${status ne null and status eq userStatus.value}">${userStatus.desc}</c:if>
				</c:forEach>
			</p>
			<p style="width:99%">
				<label>类型：</label>
				<c:forEach items="${UserTypeEnumList}" var="userTypeEnum">
					<c:if test="${userType ne null and userType eq userTypeEnum.value}">${userTypeEnum.desc}</c:if>
				</c:forEach>
			</p>
			<p style="width:99%">
				<label>最后登录时间：</label>
				<fmt:formatDate value="${lastLoginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</p>
			<p style="width:99%">
				<label>最后输错密码时间：</label>
				<fmt:formatDate value="${pwdErrorTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</p>
			<p style="width:99%">
				<label>已更改过密码：</label>
				<c:choose>
					<c:when test="${isChangedPwd eq true}">是</c:when>
					<c:when test="${isChangedPwd eq false}">否</c:when>
					<c:otherwise>--</c:otherwise>
				</c:choose>
			</p>
			<p style="width:99%;height:50px;">
				<label>描述：</label>
				${remark }
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
			</ul>
		</div>
	</form>
</div>