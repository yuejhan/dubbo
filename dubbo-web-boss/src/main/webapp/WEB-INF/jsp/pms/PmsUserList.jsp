<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="/pms/listPmsUser" method="post">
	<!-- 分页表单参数 -->
    <%@include file="../inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					用户登录名：<input type="text" name="userNo" value="${queryBean.userNo}" size="30" alt="精确查询"  />
				</td>
				<td>
					用户姓名：<input type="text" name="userName" value="${queryBean.userName}" size="30" alt="模糊查询"  />
				</td>
				<td>状态：</td>
				<td>
					<select name="status" class="combox">
						<option value="">-全部-</option>
						<c:forEach items="${UserStatusEnumList}" var="userStatus">
						<option value="${userStatus.value}"
							<c:if test="${queryBean.status ne null and queryBean.status eq userStatus.value}">selected="selected"</c:if>>
							${userStatus.desc}
						</option>
					</c:forEach>
					</select>
				</td>
				<td>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
						</ul>
					</div>
				</td>
			</tr>
		</table>
	</div>
	</form>
</div>
<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<%--<rc:permission value="pms:user:add">--%>
				<li><a class="add" href="${cxt}/pms/addPmsUserUI" target="dialog" rel="input" title="添加用户"><span>添加用户</span></a></li>
			<%--</rc:permission>--%>
		</ul>
	</div>
	
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="130">
		<thead>
			<tr>
				<th width="30">序号</th>
				<th width="200">用户登录名</th>
				<th>用户姓名</th>
				<th width="120">手机号码</th>
				<th width="70">状态</th>
				<th width="100">类型</th>
				<th width="300">操作</th><!-- 图标列不能居中 -->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${pageData.recordList}" var="temp" varStatus="status">
		    <%--<s:iterator value="recordList" status="st">--%>
		    	<%-- 普通用户看不到超级管理员信息 --%>
		    	<%--<c:if test="${(temp.type eq UserTypeEnum.ADMIN.value and temp.type eq UserTypeEnum.ADMIN.value) or (temp.type eq UserTypeEnum.USER.value)}">--%>
				<tr target="sid_user" rel="${temp.id}">
				    <td>${status.index+1}</td>
					<td>${temp.userNo }</td>
					<td>${temp.userName }</td>
					<td>${temp.mobileNo }</td>
					<td>
						<c:forEach items="${UserStatusEnumList}" var="userStatus">
							<c:if test="${temp.status ne null and temp.status eq userStatus.value}">${userStatus.desc}</c:if>
						</c:forEach>
					</td>
					<td>
						<c:forEach items="${UserTypeEnumList}" var="userTypeEnum">
							<c:if test="${temp.userType ne null and temp.userType eq userTypeEnum.value}">${userTypeEnum.desc}</c:if>
						</c:forEach>
					</td>
					<td>
						[<a href="${cxt}/pms/viewPmsUserUI?id=${temp.id}" title="查看【${temp.userNo }】详情" target="dialog" style="color:blue">查看</a>]
						<%--<c:if test="${temp.type eq UserTypeEnum.USER.value }">--%>
						
						&nbsp;[<a href="${cxt}/pms/editPmsUserUI?id=${temp.id}" title="修改【${temp.userNo }】" target="dialog" rel="userUpdate" style="color:blue">修改</a>]
						<%-- 
						&nbsp;[<a href="pms_resetUserPwdUI.action?id=${id}" title="重置【${userNo }】的密码" target="dialog" width="550" height="300" style="color:blue">重置密码</a>]
						<c:if test="${type eq UserTypeEnum.USER.value && status==UserStatusEnum.ACTIVE.value}">
						&nbsp;[<a href="pms_changeUserStatus.action?id=${id}" title="冻结【${userNo }】" target="ajaxTodo" style="color:blue">冻结</a>]
						</c:if>
						<c:if test="${type eq UserTypeEnum.USER.value &&status==UserStatusEnum.INACTIVE.value}">
						&nbsp;[<a href="pms_changeUserStatus.action?id=${id}" title="激活【${userNo }】" target="ajaxTodo" style="color:blue">激活</a>]
						</c:if> 
						--%>
						<%--<c:if test="${temp.type eq UserTypeEnum.USER.value }">--%>
						&nbsp;[<a href="${cxt}/pms/deleteUserStatus?id=${temp.id}" target="ajaxTodo" title="确定要删除吗？" style="color:blue">删除</a>]
						<%--</c:if>--%>
						<%--</c:if>--%>
					</td>
				</tr>
				<%--</c:if>--%>
		</c:forEach>
			<%--</s:iterator>--%>
		</tbody>
	</table>
     <!-- 分页条 -->
    <%@include file="../inc/pageBar.jsp"%>
</div>