<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="./taglib.jsp"%>
<div class="panelBar">
	<div class="pages">
		<span>查到&nbsp;${pageData.totalCount}&nbsp;条记录，每页</span>
		<select class="combox" name="numPerPage" value="${pageData.numPerPage}" onchange="navTabPageBreak({numPerPage:this.value})">
		    <option value="2" <c:if test="${pageData.numPerPage eq 2 }">selected='selected'</c:if>>2</option>
		    <option value="5" <c:if test="${pageData.numPerPage eq 5 }">selected='selected'</c:if>>5</option>
		    <option value="15" <c:if test="${pageData.numPerPage eq 15 }">selected='selected'</c:if>>15</option>
			<option value="20" <c:if test="${pageData.numPerPage eq 20 }">selected='selected'</c:if>>20</option>
			<option value="30" <c:if test="${pageData.numPerPage eq 30 }">selected='selected'</c:if>>30</option>
			<option value="50" <c:if test="${pageData.numPerPage eq 50 }">selected='selected'</c:if>>50</option>
			<option value="100" <c:if test="${pageData.numPerPage gt 50 }">selected='selected'</c:if>>100</option>
		</select>
		<span>条，&nbsp;${pageData.currentPage}/${pageData.pageCount}&nbsp;页</span>
	</div>
	<div class="pagination" 
		targetType="navTab" 
		totalCount="${pageData.totalCount}"
		numPerPage="${pageData.numPerPage}"
		pageNumShown="10" 
		currentPage="${pageData.currentPage}">
	</div>
</div>