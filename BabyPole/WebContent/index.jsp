<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.wdfall.beans.BabyPoleItem"%>
<%@page import="com.wdfall.dao.BabyPoleDao"%>
<%@page import="com.wdfall.beans.BabyPoleSubject"%>
<%@page import="java.util.List"%>
<%@page import="com.wdfall.utils.ClientUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	
<%
String clientIP = ClientUtils.getClientIPAddr(request);
String referId = StringUtils.stripToEmpty( request.getParameter("referId") );

List<BabyPoleSubject> subjectList = BabyPoleDao.getInstance().getSubjectList();
%>

<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="/jquerymobile/jquery.mobile-1.3.1.css" />
	
	<script src="/jquerymobile/jquery-1.7.1.min.js"></script>
	<script src="/jquerymobile/jquery.mobile-1.3.1.js"></script>
	<script src="/js/pole.js"></script>
	<script>
	function submitPole() {
		//confirm
		var subjectValues = "";
		<%
		for( BabyPoleSubject subject : subjectList ) 
		{
		%>
			//alert("<%=subject.getSubject()%>");
			var subjectId = "subject_<%=subject.getSeq()%>";
			var inputNames = getSubjectInputNames(subjectId);
			var itemValues = "";
			for (var inputName in inputNames) {
				itemValues += ( "'" + getInputValueByType(inputName, inputNames[inputName]) + "'," );
			   //alert( inputName  + ' type=' + inputNames[inputName] );
			}
			subjectValues +=  "<%=subject.getNumber()%>" +"={" + removeLastDelimiter(itemValues, ",") + "},";
		<%
		}
		%>
		subjectValues = removeLastDelimiter(subjectValues, ",");
		$(result).val( subjectValues );
		
		var $form = $(poleForm);
		$.ajax({
		  type: "POST",
		  url: "/proc/poleProcess.jsp",
		  data: $form.serialize(),
		  success: function(data){ 
	          alert("success!!");
	      }
		
		});
		
	}
	</script>
	
	<title>신생아 육아 패턴 설문 </title>

</head>


<body>

<div data-role="page">

	<div data-role="header">
		<img alt="baby icon" width="36" height="36" src="/images/icon.png" style="position: absolute; float: left; margin-left: 15px;">
		<h1>신생아 육아 패턴 설문</h1>
	</div><!-- /header -->
	<div data-role="content">
		
		<%
		for( BabyPoleSubject subject : subjectList ) 
		{
		%>
		<fieldset data-role="controlgroup" data-mini="true" id="subject_<%=subject.getSeq()%>">
			<legend><%=subject.getNumber() %>. <%=subject.getSubject() %> </legend>
			<% 
			List<BabyPoleItem> itemList = subject.getItemList();
			for( BabyPoleItem item : itemList )
			{
				if( item.getType().equals("I") || item.getType().equals("B") )
				{
			%>
			<input type="radio" name="<%=item.getSubjectSeq() %>" id="<%=item.getSeq() %>" value="<%=item.getValue() %>" onclick="focusInput(this, '<%=item.getType() %>', 'input_<%=item.getSubjectSeq() %>')" />
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
				<fieldset class="ui-grid-a">
					<div class="ui-block-a" style="width: 30%;" > <b>[ <%=itemSubject %> ]:</b></div>
					<div class="ui-block-b" style="width: 70%" >
				    	<select name="<%=item.getSubjectSeq() + "_" + item.getSeq() %>" id="<%=item.getSubjectSeq() + "_" + item.getSeq() %>" data-mini="true">
				    		<% 
				    		int index = 0; 
				    		for( String itemName : names ) {
				    			String value = item.getValue().split(",")[index++].trim();
				    		%>
				         	<option value="<%=value %>"><%=itemName%> </option>
				         	<% 
				         	}
				         	%>
				    	</select>
				    	
				    </div>
			    </fieldset>
			<%			
				}
				else if( item.getType().equals("J") ) 
				{
					String label = item.getName().replaceAll("[$]input[$]", "").trim();
			%>
			<div data-role="fieldcontain">
			 <%=label %> <input type="text" name="input_<%=item.getSubjectSeq() %>" id="<%=item.getSeq() %>" value=""  />
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
		<button type="submit" data-theme="b" onclick="submitPole();">완료</button>
	</div>

	</div><!-- /content -->
	
</div><!-- /page -->
 
<form name="poleForm" id="poleForm">
	<input type="hidden" name="host" id="host" value="<%=clientIP %>" />
	<input type="hidden" name="referId" id="referId" value="<%=referId %>" />
	<input type="hidden" name="result" id="result" />
</form>
 
</body>
</html>