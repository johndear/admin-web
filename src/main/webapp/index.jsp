<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script>
//word上传时参考的word模板
function wordTempDownload(){
	window.location.href = basePath + "download/请购-基本情况概述模板.docx";
}
</script>
</head>
<body>
<a href="javascript:void(0)" onclick="wordTempDownload()">下载静态资源文件</a>

</body>
</html>