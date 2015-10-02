<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>用户管理</title>
	<jsp:include page="/static/common.jsp"></jsp:include>
	<link href="${ctx}/static/jquery/jquery-ui.css" rel="stylesheet">
	<script src="${ctx}/static/jquery/jquery-ui.js"></script> 
</head>

<body style="background-color: #fff;">
	<script>
	
	var availableTags = [
	                 	"ActionScript",
	                 	"AppleScript",
	                 	"Asp",
	                 	"BASIC",
	                 	"C",
	                 	"C++",
	                 	"Clojure",
	                 	"COBOL",
	                 	"ColdFusion",
	                 	"Erlang",
	                 	"Fortran",
	                 	"Groovy",
	                 	"Haskell",
	                 	"Java",
	                 	"JavaScript",
	                 	"Lisp",
	                 	"Perl",
	                 	"PHP",
	                 	"Python",
	                 	"Ruby",
	                 	"Scala",
	                 	"Scheme"
	                 ];

		
		$(function(){
			$( "#word").autocomplete({
				source: '${ctx}/suggest/get'
			});
			
			
		});
		

		
		/* function suggest(){
			$('#content').html('');
			var word = $('#word').val();
			$.get('suggest/get',{word:word, rnd:Math.random()},function(data){
				// alert(data);
				data = JSON.parse(data);
				
				var docs = '';
				for(var i in data.result){
					var doc = data.result[i];
					docs =docs + doc +'<br>';
				}
				
				$('#content').html(docs);
			});
		} */
		
		function query(){
			$('#content_left').html('');
			var word = $('#word').val();
			$.get('search/get',{word:word, rnd:Math.random()},function(data){
				//alert(data);
				data = JSON.parse(data);
				
				var docs = '';
				for(var i in data.result){
					var doc = data.result[i];
					docs =docs + "<span><a href='#' style='text-decoration: underline;'>" + doc.name + '</a><br><span style="font-size:12px;">' + doc.actions + '</span></span><br><br>';
				}
				
				$('#content_left').html(docs);
				$('#count').html(data.counts);
			});
		}
</script>
	
	<div id="container">
		<div id="content_top">
			<input type="text" id="word" onchange="suggest()"/>
			<input id="submit_btn" class="btn btn-primary" type="button" value="站内搜索" onclick="query()"/>
			<span style="font-size:12px;">
				<br><span style="color:#999;">为您找到相关结果约<span id="count"></span>个</span><br><b>以下为您显示“chandler”的搜索结果。</b> 仍然搜索：chander<br><br>
			</span>
		</div>
		<div id="content_left" style="width:70%;height:800px;float:left;">aa</div>
		<div id="content_right" style="width:25%;padding-left:4%;float:right;border-left:1px solid #ccc;">
			morelikethis功能<br><br>
			其他人还搜<br><br>
			相关搜索
		</div>
		
	</div>
				
</body>
</html>