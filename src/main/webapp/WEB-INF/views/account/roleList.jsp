<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>用户管理</title>
	<jsp:include page="/static/common.jsp"></jsp:include>
</head>

<body>
	<script>
		function openMenuWindow(title, view, params){
			var roleId = $('input[type=radio]:checked').val();
			if(roleId==undefined || roleId==''){
				$.messager.alert('提示', '请选择角色再授权！', 'warning');
				return;
			}
			params = "roleId=" + roleId;
			$("#windowTitle")[0].innerHTML = title;
			$('#showDiv').load('${ctx}/adapter/' + view +'?'+ params);
			$("#windowDiv").show();
			$("#modalDiv").show();
		}
		
		function closeWindow(){
			$("#windowDiv").hide();
			 $("#modalDiv").hide();
		}
	</script>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>选择</th>
				<th field="name" width="100" sortable="true">角色名称</th>
				<th field="status" width="100" sortable="true" formatter="statusFormatter">状态</th>
				<th field="memo" width="250">备注</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${roles}" var="role">
			<tr>
				<td><input type="radio" name="inlineRadioOptions" id="inlineRadio1" value="${role.id}"></td>
				<td><a href="${ctx}/admin/user/update/${role.id}">${role.roleName}</a></td>
				<td>${role.roleName}</td>
				<td></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<div class="form-actions">
		<input id="submit_btn" class="btn btn-primary" type="submit" value="分配菜单" onclick="openMenuWindow('菜单列表', 'menu', '')"/>&nbsp;	
		<input id="cancel_btn" class="btn btn-primary" type="button" value="分配操作" onclick="openMenuWindow('操作列表', 'action', '')"/>
	</div>
	
	<!-- 弹出窗 -->
	<div id="windowDiv" class="modal hide in">
		 <div class="modal-header">
			<button class="close" type="button" onclick="closeWindow()">×</button>
			<h3><span id="windowTitle"></span></h3>
  			</div>
  			<div id="showDiv" style="overflow:auto;height:100%;">
  			</div>
		<div style="overflow:auto;height:20px;">
  			</div>
	</div>
	<div id="modalDiv" class="modal-backdrop hide"></div>
				
</body>
</html>