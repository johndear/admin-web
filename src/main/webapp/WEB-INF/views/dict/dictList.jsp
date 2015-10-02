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
		
		$(function(){
			$('#contentTable').datagrid({
				title: '字典列表',
				toolbar: [{
					iconCls: 'icon-add',
					handler: function(){alert('add')}
				},'-',{
					iconCls: 'icon-edit',
					handler: function(){alert('edit')}
				},'-',{
					iconCls: 'icon-cancel',
					handler: function(){alert('delete')}
				}],
				width:'auto',
				singleSelect: true,
				url: '${ctx}/dict/all',
				pagination:true
			});
		});
	</script>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th field="id" width="100">选择</th>
				<th field="dictType" width="200" sortable="true">字典类型</th>
				<th field="dictCode" width="150" sortable="true" formatter="">字典编码</th>
				<th field="dictName" width="150" sortable="true" formatter="">字典名称</th>
				<th field="dictMemo" width="100">备注</th>
			</tr>
		</thead>
	</table>
	
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