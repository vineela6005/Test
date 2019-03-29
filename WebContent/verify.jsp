<%@page import="java.util.Date"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<table bordercolor="lightgary;" border="2"
		style="border-style: double; padding: 30px; background-color: white; border-collapse: collapse; border-color: black;"
		align="center">
		<tr align="center">
			<th>Application No</th>
			<th>Customer Id</th>
			<th>Pan No</th>
			<th>FirstName</th>
			<th>LatName</th>
			<th>Application Status</th>
			<th>Last Modified Date</th>
		</tr>
		<%
			Object attribute = request.getAttribute("arrayObject");
			ArrayList<HashMap<String, String>> objArrayList = (ArrayList<HashMap<String, String>>) attribute;
			System.out.println("inside verify.jsp " + objArrayList);
			Iterator<HashMap<String, String>> iterator = objArrayList.iterator();
			String firstname = null, lastname = null, pan_no = null, custid = null, app_no = null, app_status = null;
			String objDate = null;

			while (iterator.hasNext()) {
				HashMap<String, String> objHashMap = iterator.next();
				firstname = objHashMap.get("firstname");
				lastname = objHashMap.get("lastname");
				app_no = objHashMap.get("app_no");
				pan_no = objHashMap.get("pan_no");
				custid = objHashMap.get("custid");
				app_status = objHashMap.get("app_status");
				objDate = objHashMap.get("last_modifiedDate");
		%>
		<tr align="center">
			<td><a
				href="WorkFlowController?app-no=<%=app_no%>&buttonVal=verify_user"><%=app_no%></a>
			</td>
			<td><%=custid%></td>
			<td><%=pan_no%></td>
			<td><%=firstname%></td>
			<td><%=lastname%></td>
			<td><%=app_status%></td>
			<td><%=objDate%></td>
		</tr>
		<%
			}
		%>
	</table>


</body>
</html>