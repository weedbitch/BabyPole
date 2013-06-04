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
		
		<%
		for( BabyPoleSubject subject : subjectList ) 
		{
		%>
		<fieldset data-role="controlgroup" data-mini="true">
			<legend><%=subject.getNumber() %>. <%=subject.getSubject() %> :</legend>
			<% 
			List<BabyPoleItem> itemList = subject.getItemList();
			for( BabyPoleItem item : itemList )
			{
				if( item.getType().equals("I") )
				{
			%>
			<input type="radio" name="<%=item.getSubjectSeq() %>" id="<%=item.getSeq() %>" value="<%=item.getValue() %>" />
			<label for="<%=item.getSeq() %>"><%=item.getName() %></label>
			<%	
				} 
				else if( item.getType().equals("K") )
				{
			%>
			<input type="checkbox" name="<%=item.getSubjectSeq() %>" id="<%=item.getSeq() %>" value="<%=item.getValue() %>" />
			<label for="<%=item.getSeq() %>"><%=item.getName() %></label>
			<%		
				}
				else if( item.getType().equals("H") )
				{
					String itemSubject = item.getName().split("=")[0];
					String[] names = item.getName().split("=")[1].split(",");
					
			%>
			    	<select name="<%=item.getSubjectSeq() + "_" + item.getSeq() %>" id="<%=item.getSubjectSeq() + "_" + item.getSeq() %>" data-mini="true">
			    		<% 
			    		int index = 0; 
			    		for( String itemName : names ) {
			    			String value = item.getValue().split(",")[index++].trim();
			    		%>
			         	<option value="<%=value %>"> [ <%=itemSubject %> ]: <%=itemName%> </option>
			         	<% 
			         	}
			         	%>
			    	</select>
			<%			
				}
				else if( item.getType().equals("J") ) 
				{
					String label = item.getName().replaceAll("[$]input[$]", "").trim();
			%>
			<div data-role="fieldcontain">
			 <%=label %> <input type="text" name="<%=item.getSubjectSeq() %>" id="<%=item.getSeq() %>" value=""  />
			<div data-role="fieldcontain">
			<%
				}
			}
			%>
			
		</fieldset>
		</br>
		<%
		}
		%>
	
	<div class="ui-body ui-body-b">
		<fieldset class="ui-grid-a">
				<div class="ui-block-a"><button type="submit" data-theme="d" onclick="window.location.href='/'">Cancel</button></div>
				<div class="ui-block-b"><button type="submit" data-theme="a" onclick="alert("감사합니다.");">Submit</button></div>
		</fieldset>
	</div>

	</div><!-- /content -->
	
</div><!-- /page -->
 
 
 
</body>
</html>