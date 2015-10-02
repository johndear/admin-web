<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>任务列表</title>
<style>
	.datalist{
		font-size:14px;
	}
	table th{ 
		border:1px solid #ccc;
	}
	
	table td{ 
		border:1px solid #ccc;
	}
</style>
<script type="text/javascript" src="<%=basePath%>js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/comet4j.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery-1.2.6.pack.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery.messager.js"></script>
<script>
	<!-- comet4j javascript -->
	$(function(){
	    JS.Engine.on({
	        hello: function(text){  //对应comet4j channel的名称
		        $('#bgsound').html("<bgsound src=\"resources/musics/nglmy.mp3\" loop=\"2\" />");
	    		$('#content').html(text);
			    $.messager.show('<font color=red>消息提醒</font>', '<font color=green style="font-size:14px;font-weight:bold;">' + text + '</font>');
			}
	    });
	 	JS.Engine.start('conn');
	});

	
	function btn_click(self,trigger,group,flag){
		$(self).attr('disabled',true);
		if("暂停"==$(self).val()){
			$(self).next().attr('disabled',false);
		}else if("恢复"==$(self).val()){
			$(self).prev().attr('disabled',false);
		}
		
		$.get("quartz?flag="+flag+"&jobName="+trigger+"&groupName="+group,{rnd:Math.random()},function(data){});
	}
	
	function addJob(){
		location.href="<%=basePath%>testJob?flag=";
	}
	</script>
</head>
<body style="background-color: #fff;">
	<div class="form-actions">
		任务名称：<input type="text"/> &nbsp;&nbsp;任务类型：<input type="text"/>
		<input id="submit_btn" class="btn btn-primary" type="button" value="查询" onclick="queryJob()"/>
		<input id="submit_btn" class="btn btn-primary" type="button" value="添加" onclick="addJob()"/>
	</div>
	
	<table class="datalist" border="0" cellpadding="0" cellspacing="0" width="100%">
		<thead>
			<tr style="background-color:#FDF0F2;height:40px;">
				<!-- <td>ID</td> -->
				<td width="6%">任务组</td>
				<td width="8%">任务名</td>
				<td width="8%">触发器组</td>
				<td width="8%">触发器名</td>
				<td width="8%">触发器状态</td>
				<td width="8%">触发器类型</td>
				<td width="6%">优先级</td>
				<td width="8%">上次执行时间</td>
				<td width="8%">下次执行时间</td>
				<td width="8%">开始时间</td>
				<td width="8%">结束时间</td>
				<td width="12%">操作</td>
			</tr>
		</thead>
		<tbody>
		
		
		<%
			List<Object> list = (List<Object>)request.getAttribute("list");
			for(Object obj : list){
				Map<String,String> map = (Map<String,String>)obj;
				%>
				<tr>
					<!-- <td><img src="resources/images/clock.png" height="30" width="30"/></td> -->
					<td><%=map.get("job_group") %></td>
					<td><%=map.get("job_name") %></td>
					<td><%=map.get("trigger_group") %></td>
					<td><%=map.get("trigger_name") %></td>
					<td><%=map.get("trigger_state") %></td>
					<td><%=map.get("trigger_type") %></td>
					<td><%=map.get("priority") %></td>
					<td><%=map.get("prev_fire_time") %></td>
					<td><%=map.get("next_fire_time") %></td>
					<td><%=map.get("start_time") %></td>
					<td><%=map.get("end_time") %></td>
					<td>
						<% 
						if(map.get("trigger_state").trim().equals("PAUSED")){
						%>
							<input type="button" value="暂停" disabled="disabled" onclick="btn_click(this,'<%=map.get("job_name") %>','<%=map.get("trigger_group") %>','3')"/>	
							<input type="button" value="恢复" onclick="btn_click(this,'<%=map.get("job_name") %>','<%=map.get("trigger_group") %>','4')"/>
						<%
						} else {
						%>
							<input type="button" value="暂停" onclick="btn_click(this,'<%=map.get("job_name") %>','<%=map.get("trigger_group") %>','3')"/>
							<input type="button" value="恢复" disabled="disabled" onclick="btn_click(this,'<%=map.get("job_name") %>','<%=map.get("trigger_group") %>','4')"/>
						<%
						}
						%>
						<input type="button" value="删除" onclick="btn_click(this,'<%=map.get("job_name") %>','<%=map.get("trigger_group") %>','5')"/>
					</td>
					<%-- <td>
						<% 
						if(map.get("trigger_state").trim().equals("PAUSED")){
						%>
							<a disabled="disabled" href="javascript:void(0);" onclick="btn_click(this,'<%=map.get("job_name") %>','<%=map.get("trigger_group") %>','3')">暂停</a> ||
							<a href="javascript:void(0);" onclick="btn_click(this,'<%=map.get("job_name") %>','<%=map.get("trigger_group") %>','4')">恢复</a> ||
						<%
						} else {
						%>
							<a href="javascript:void(0);" onclick="btn_click(this,'<%=map.get("job_name") %>','<%=map.get("trigger_group") %>','3')">暂停</a> ||
							<a disabled="disabled" href="javascript:void(0);" onclick="btn_click(this,'<%=map.get("job_name") %>','<%=map.get("trigger_group") %>','4')">恢复</a> ||
						<%
						}
						%>
						<a href="javascript:void(0);" onclick="btn_click(this,'<%=map.get("job_name") %>','<%=map.get("trigger_group") %>','5')">删除</a>
					</td> --%>
				</tr>
				
				<%
			}
			
		%>
			
		</tbody>
	</table>
	
	<div id="content" style="margin-top:10px;border:1px solid #ccc;height:200px;width:100%;color:gray;font-size:14px;overflow: scroll;">动态消息区域</div>
	<div id="bgsound"></div>
</body>
</html>