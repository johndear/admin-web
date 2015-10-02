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
		
	   var menus = ${menus };
		$(function(){
 			$("#dt-resAct").datagrid('loadData', menus );
		});
		
		function saveRoleMenu(){
			var actions="";
			//存储所有选择的操作
			var allSelections = new Array();
			//存储所有的资源
			var allResources = new Array();
			
			$("#resActForm").find("input[type='checkbox']").each(function(index){
				//如果name属性里面没有冒号的话，表示是资源，则存入资源数组
				if(this.name.indexOf(":")==-1 ){
					allResources[allResources.length] = this.id;
				}else if(this.checked){
					//不是资源数组并且被选中了，就存入操作数组
					allSelections[allSelections.length] = this.name;
				}
	        });
			//循环遍历资源数组，从操作数组中找出对应的操作依次加入actions变量中
			for(var i=0;i<allResources.length;i++){
				var flag=false;
				for(var j=0;j<allSelections.length;j++){
					//如果操作中包含资源ID，表示当前操作输入该资源，就顺序加入actions中
					if(allSelections[j].indexOf(allResources[i]+":")==0){
						flag=true;
						actions+=allSelections[j]+",";
					}
				}
				if(flag){
					actions=actions.substring(0,actions.length-1)+";";
				}
			}
			
			$("#saveResActInput").val(actions);
			$('#saveResActForm').form('submit',{
					url: '${ctx}/adapter/action',
					onSubmit:function(){return true;},
					success:function(json){
						$('#resAct').dialog('close');
						if(json.success){
							$.messager.show(
								{
									title:'提示',
									msg:'操作成功！',
									showType:'slide'
								}
							);
						}
						if(json.error){
							$.messager.alert('警告','操作失败！','error');
						}
					}
			 });
		}
		
		//操作列的formatter
		function actionsFormatter(value,rowData,rowIndex){
			if(value != undefined && value != ''){
				var actions ='';
				for(var i in value){
					var checkBox = '<input type="checkbox" onchange="" id="'+value[i].id+'" name="'+rowData.id +':'+ value[i].ename+'"/>' + value[i].actionName;
					actions = actions + checkBox + '&nbsp;';
				}
				return actions;
			}
		}
		
		//资源列的formatter
		function resourceFormatter(value,rowData,rowIndex){
			return '<input type="checkbox" onchange="selectCurrentRow(this.checked,this.id);" id="'+rowData.id+'" name="'+rowData.menuNameEn+'"/>'+value;
		}
		
		//当点击第二列的checkbox时，将当前行的所有checkbox全选或全不选
		function selectCurrentRow(select,id){
			//查找id=resActForm下的所有checkbox，并选中或取消选中
			$("#resActForm").find("input[type='checkbox']").each(function(index){
				if(this.name.indexOf(id+":")==0){
					this.checked = select;
				}
	        });
		}
		
		
	</script>

	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	
	<form id="resActForm" method="post">
		<table id="dt-resAct" class="easyui-datagrid" border="false" singleSelect="true" rownumbers="true"  checkbox="true">
			<thead>
				<tr>
					<th field="id" width="100" hidden="true" >系统名称</th>
					<th field="menuName" width="100" formatter="resourceFormatter">资源名称</th>
					<th field="menuNameEn" width="100" hidden="true" >英文名称</th>
					<th field="actionList" width="630" formatter="actionsFormatter">操作</th>
				</tr>
			</thead>
		</table>
	</form>
	
	<form id="saveResActForm" method="post">
		<input name="roleId" value="${roleId }" style="display:none;"/>
		<input type="text" name="roleActions" id="saveResActInput" style="display:none;"/>
	</form>
	
	<shiro:hasPermission name="authc:add">
		authc:add
	</shiro:hasPermission>
	<shiro:hasPermission name="authc:delete">
		authc:delete
	</shiro:hasPermission>
	
	<div class="form-actions">
		<input id="submit_btn" class="btn btn-primary" type="submit" value="确定" onclick="saveRoleMenu()"/>&nbsp;	
		<input id="cancel_btn" class="btn" type="button" value="取消" onclick="closeWindow()"/>
	</div>
	
	
	
	
	
</body>
</html>