<%@page import="com.wdfall.beans.BabyPoleItem"%>
<%@page import="com.wdfall.dao.BabyPoleDao"%>
<%@page import="com.wdfall.beans.BabyPoleSubject"%>
<%@page import="java.util.List"%>
<%@page import="com.wdfall.utils.ClientUtils"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="/jquerymobile/jquery.mobile-1.3.1.css" />
	
	<script src="/jquerymobile/jquery-1.7.1.min.js"></script>
	<script src="/jquerymobile/jquery.mobile-1.3.1.js"></script>
	
	<title>Online Baby Pole</title>
	
<%
String clientIP = ClientUtils.getClientIPAddr(request);
List<BabyPoleSubject> subjectList = BabyPoleDao.getInstance().getSubjectList();

%>

</head>


<body>

<div data-role="page">

	<div data-role="header">
		<h1>Online Baby Pole</h1>
	</div><!-- /header -->

	<div data-role="content">
	
	<button onclick="window.location.href='./pole.jsp'">설문에 참여하기</button>

	</div><!-- /content -->
	
</div><!-- /page -->
 
 
 
</body>
</html>