<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>logout</title>
</head>
<body>
	<%
response.setContentType("text/html");
PrintWriter out1=response.getWriter();

HttpSession session1=request.getSession(false);
session1.getAttribute("employee");
session1.invalidate();

out1.print("You are logged out..");
RequestDispatcher rs=request.getRequestDispatcher("/login.jsp");
rs.forward(request, response);
%>
</body>
</html>