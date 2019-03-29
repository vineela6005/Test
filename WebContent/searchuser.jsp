
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
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
			<th>Application Number</th>
			<th>Customer Id</th>
			<th>Application Status</th>
			<th>Last Modified Date</th>
		</tr>

		<%
			Object attribute = request.getAttribute("searchObj");
			ArrayList<HashMap<String, String>> objArrayList = (ArrayList<HashMap<String, String>>) attribute;

			System.out.println("in searchuser.jsp....array obj : " + objArrayList);

			Iterator<HashMap<String, String>> iterator = objArrayList.iterator();
			String appNumber = null;
			String appStatus = null;
			String custId = null;
			String lastModifiedDate = null;

			while (iterator.hasNext()) {
				HashMap<String, String> objHashMap = iterator.next();

				appNumber = objHashMap.get("appNumber");
				appStatus = objHashMap.get("appStatus");
				custId = objHashMap.get("custId");
				lastModifiedDate = objHashMap.get("lastModifiedDate");
		%>
		<tr align="center">
			<td><a
				href="SearchController?appNo=<%=appNumber%>&buttonVal=searchform"><%=appNumber%></a>
			</td>
			<td><%=custId%></td>
			<td><%=appStatus%></td>
			<td><%=lastModifiedDate%></td>
		</tr>
		<%
			}
		%>

	</table>


</body>
</html>