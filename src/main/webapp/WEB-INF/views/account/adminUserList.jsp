<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>用户管理</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>登录名</th><th>用户名</th><th>注册时间<th>管理</th></tr></thead>
		<tbody>
		<c:forEach items="${users}" var="user">
			<tr>
				<td><a href="${ctx}/admin/user/update/${user.id}">${user.userName}</a></td>
				<td>${user.name}</td>
				<td>
					<%-- <fmt:formatDate value="${user.registerDate}" pattern="yyyy年MM月dd日  HH时mm分ss秒" /> --%>
				</td>
				<td>
					<a href="${ctx}/admin/user/delete/${user.id}">逻辑删除</a>
					<shiro:hasRole name="Admin">
						aaa
					</shiro:hasRole>
					<shiro:hasPermission name="user:edit">
						user:edit
					</shiro:hasPermission>
					<shiro:hasPermission name="test:edit">
						test:edit
					</shiro:hasPermission>
					<shiro:hasPermission name="admin:delete">
						admin:delete
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
