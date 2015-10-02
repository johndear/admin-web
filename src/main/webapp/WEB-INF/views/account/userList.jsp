<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>用户管理</title>
<jsp:include page="/static/common.jsp"></jsp:include>
<link rel="stylesheet" href="${ctx}/static/styles/matrix-style.css" />
<script src="${ctx}/static/jquery/jquery.jqprint-0.3.js" type="text/javascript"></script>
<script>
	function preview() {
		$("#showDiv").show();
		$("#shenPi").show();
	}
	
	function preview_close() {
       $("#showDiv").hide();
	   $("#shenPi").hide();
	   $("iframe").remove();
	}
	
	function print(){
		$("#shenPi").jqprint({
			debug : true, //如果是true则可以显示iframe查看效果（iframe默认高和宽都很小，可以再源码中调大），默认是false
			importCSS : true, //true表示引进原来的页面的css，默认是true。（如果是true，先会找$("link[media=print]")，若没有会去找$("link")中的css文件）
			printContainer : false, //表示如果原来选择的对象必须被纳入打印（注意：设置为false可能会打破你的CSS规则）。
			operaSupport : false
		//表示如果插件也必须支持歌opera浏览器，在这种情况下，它提供了建立一个临时的打印选项卡。默认是true
		});
	}
</script>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>

	<div class="form-actions">
		<input id="submit_btn" class="btn btn-primary" type="submit"
			value="打印" onclick="preview()" />&nbsp;
	</div>

	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>登录名</th>
				<th>用户名</th>
				<th>注册时间
				<th>管理</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${users}" var="user">
				<tr>
					<td><a href="${ctx}/admin/user/update/${user.id}">${user.userName}</a></td>
					<td>${user.name}</td>
					<td>
						<%-- <fmt:formatDate value="${user.registerDate}" pattern="yyyy年MM月dd日  HH时mm分ss秒" /> --%>
					</td>
					<td><a href="${ctx}/admin/user/delete/${user.id}">删除</a> <shiro:hasRole
							name="Admin">
						hasRole
					</shiro:hasRole> <shiro:hasPermission name="user:edit">
						hasPermission
					</shiro:hasPermission></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<!-- 弹出窗 -->
	<div class="modal-backdrop hide" id="showDiv"></div>
	<div id="shenPi" class="modal in hide"
		style="z-index: 1000 !important; top: 20px;">

		<div class="buttonDiv bgGray" id="bgGray">
			<button class="btn btn-primary" style="margin-right: 10px;"
				onclick="preview_close();">
				<i class=" icon-cancel"></i> 关 闭
			</button>
			<button class=" btn btn-primary" onclick="print();">
				<i class="icon-print"></i> 打 印
			</button>
			<br class="clearL">
		</div>
		<div class="modal-body modal-body1 row-fluid" style="height: 400px">
			<table class="input_table" width="100%" border="0" cellspacing="0"
				cellpadding="0" id="placeList">
				<tr>
					<th colspan="4"><h4>采购供应商信息审批列表</h4>[2015]SCM-SCM第132号</th>
				</tr>
				<tr>
				<tr>
					<td width="25%">档案编号：</td>
					<td width="25%">[2015]SCM-SCM第162号</td>
					<td width="25%">审批类型：</td>
					<td>更正</td>
				</tr>
				<tr>
					<td>供应商名称：</td>
					<td>领航动力信息系统有限公司</td>
					<td>组织类型：</td>
					<td>企业</td>
				</tr>
				<tr>
					<td colspan="4">
						<table class="noborder" width="100%" border="0" cellspacing="0"
							cellpadding="0">
							<tr>
								<td width="33%"><input type="checkbox" checked="true">供应商通用合作协议</td>
								<td width="33%"><input type="checkbox" checked="true">会员注册认证协议原件</td>
								<td><input type="checkbox" checked="true">组织机构代码证复印件</td>
							</tr>
							<tr>
								<td><input type="checkbox" checked="true">营业执照副本复印件</td>
								<td width="33%"><input type="checkbox">法人授权函原件(非法人签字时须提供)</td>
								<td><input type="checkbox">供应商信息变更函</td>
							</tr>
							<tr>
								<td><input type="checkbox">开户许可证</td>
								<td width="33%"><input type="checkbox">国地税登记证</td>
								<td><input type="checkbox">其他纸质文件</td>
							</tr>
							<tr>
								<td>说明： 有“✔” 无“×”。</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>

		</div>
	</div>
</body>
</html>
