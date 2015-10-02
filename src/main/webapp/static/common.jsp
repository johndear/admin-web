<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="session"/> 
<%
	String path = request.getContextPath();
	String baseServer = request.getScheme() + "://"
                                			+ request.getServerName() + ":" + request.getServerPort();
    String basePath = baseServer + path + "/";
%>
<link rel="stylesheet" href="${ctx}/static/bootstrap/2.3.2/css/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx}/static/bootstrap/2.3.2/css/bootstrap-responsive.min.css" />

<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/easyui/themes/icon.css">

<script src="${ctx}/static/jquery/jquery-1.9.1.min.js"></script> 
<script src="${ctx}/static/bootstrap/2.3.2/js/bootstrap.min.js"></script> 
<script src="${ctx}/static/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${ctx}/static/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript" ></script>


<script type="text/javascript">
  var contextPath = "${ctx}/";
  var basePath="<%=basePath%>";
  var baseServer="<%=baseServer%>";
  function getRandom(n){return Math.floor(Math.random()*n+1)};
  //关闭当前页面所在的tab页
  function closeSelf(){
      if(window.parent.triggerEvent) {
          var bodyrel = "temp" + getRandom(1000);
          $('body').attr("rel", bodyrel);
          $(window.parent.document).find("iframe").each(function () {
              var tRel = $(this.contentWindow.document).find("body").attr("rel");
              if (tRel == bodyrel) {
                  window.parent.triggerEvent("closeTab", $(this).attr("title"));
              }
          });
      }else{
          window.close();
      }
  }
  /**
  *刷新我的待办
  */
  function refreshMyWork(){
	  //判断父窗口是否为空
	  if(window.parent.triggerEvent){
  	  	window.parent.triggerEvent("addTab", ["我的待办", "process/toWorkItem"]);
	  }
  }
  
   //获取当前页面所在的tab页的title
  function getSelfTitle(){
  		var title = "";
        if(window.parent.triggerEvent) {
          var bodyrel = "temp" + getRandom(1000);
          $('body').attr("rel", bodyrel);
          $(window.parent.document).find("iframe").each(function () {
              var tRel = $(this.contentWindow.document).find("body").attr("rel");
              if (tRel == bodyrel) {
                  title = $(this).attr("title");
              }
          });
        }
        return title;
  }
  
</script>