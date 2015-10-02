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
				//data: ${menuTreeData}
				url: '${ctx}/loadMenuTree'
			});
		});
		
		function openAddOpr(){
			var roleId = $('input[type=radio]:checked').val();
			if(roleId==undefined || roleId==''){
				$.messager.alert('提示', '请选择资源再授权！', 'warning');
				return;
			}
			$('#dlg1').dialog('setTitle','添加操作').dialog('open');
		}
		
		
	</script>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	
	<div>
		<ul id="menuTree" style="width:20%;float:left;"></ul>
		<table id="contentTable" class="table table-striped table-bordered table-condensed" style="width:79%;">
			<thead>
				<tr>
					<th>选择</th>
					<th field="name" width="100"  sortable="true">资源名称</th>
					<th field="enname" width="100" sortable="true">英文名称</th>
					<th field="parentName" width="100" >上级资源</th>
					<th field="systemName" width="100" sortable="true">系统名称</th>
					<th field="resourcetype" width="100" sortable="true">资源类型</th>
					<th field="link" width="100" sortable="true">链接</th>
					<th field="icon" width="100" sortable="true">图标</th>
					<th field="iconopen" width="70" sortable="true" formatter="openFormatter">打开图标</th>
					<th field="isopen" width="70" sortable="true" formatter="openFormatter">是否打开</th>
					<th field="isleaf" width="70" sortable="true" formatter="leafFormatter">是否节点</th>
					<th field="status" width="50" sortable="true" formatter="statusFormatter">状态</th>
					<th field="orderid" width="50" sortable="true">排序</th>
					<th field="memo" width="250">备注</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${menus}" var="menu">
				<tr>
					<td><input type="radio" name="inlineRadioOptions" id="inlineRadio1" value="${menu.id}"></td>
					<td><a href="${ctx}/admin/user/update/${menu.id}">${menu.menuName}</a></td>
					<td>${menu.menuNameEn}</td>
					<td>${menu.pId}</td>
					<td>${menu.appId}</td>
					<td>${menu.menuLevel }</td>
					<td>${menu.menuUrl}</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td>${menu.displayOrder}</td>
					<td>
						${menu.memo }
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
	
	<div id="dlg1" class="easyui-dialog" style="width:350px;height:200px;"
			closed="true" modal="true" buttons="#dlg-buttons">
		<div align="center" style="padding-top:20px;padding-bottom: 50px">添加操作：<input  id="addactions" name="addactions" value="添加、删除、导出"/></div>
		<div id="dlg-buttons1" style="text-align:center">
			<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveAction()">保存</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg1').dialog('close')">关闭</a>
		</div>
	</div>
	
	<div class="form-actions">
		<input id="submit_btn" class="btn btn-primary" type="submit" value="新增" onclick="openMenuWindow('菜单列表', 'menu', '')"/>&nbsp;
		<input id="cancel_btn" class="btn btn-primary" type="button" value="添加操作" onclick="openAddOpr()"/>
	</div>
</body>
</html>