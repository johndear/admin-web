<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>用户管理</title>
	<jsp:include page="/static/common.jsp"></jsp:include>
	
	<script>
		
		$(function(){
			$('#menuTree').tree({
				checkbox:true,
				data: ${menuTreeData}
			});
		});
		
		function saveRoleMenu(){
			
		}
		
		
	</script>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	
	<ul id="menuTree"></ul>
	
	<div class="form-actions">
		<input id="submit_btn" class="btn btn-primary" type="submit" value="确定" onclick="saveRoleMenu()"/>&nbsp;	
		<input id="cancel_btn" class="btn" type="button" value="取消" onclick="closeWindow()"/>
	</div>
</body>
</html>