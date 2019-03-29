<%@page import="com.mits.creditcard.login.RoleVo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.mits.creditcard.login.UserVo"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body style="background-color: white;">

	<%
	session=request.getSession(false);
String rolename=null;
if(session!=null)
{
	/* ArrayList roleBeanList = (ArrayList)objUserBean.getRoleBeanList();
	if(roleBeanList.size() > 0) {
		RoleBean role = (RoleBean)roleBeanList.get(0);

	 rolename=(String)role.getRoleName();
	System.out.println("rolename	  : "+rolename);
	} */
	
	UserVo bean=(UserVo) session.getAttribute("loginSession");
	ArrayList list=(ArrayList) bean.getRoleBeanList();
	//for(ArrayList list:bean)
	{
	if(list.size()>0)
	{
		RoleVo roleBean=(RoleVo)list.get(0);
		rolename=(String)roleBean.getRoleName();
		System.out.println("rolename  :"+rolename);
		if(rolename.equalsIgnoreCase("dataentry"))
		{
%>
	<br>
	<br>
	<a href="addapplication.jsp?roleName=<%=rolename%>" target="content"
		style="width: 150%; background-color: gray; color: white; font-family: fantasy; font-size: medium; padding: 25px; font-size: large;">Add-Application&nbsp;&nbsp;&nbsp;&nbsp;</a>

	<br>
	<br>
	<br>
	<br>
	<br>

	<a href="WorkFlowController?buttonVal=ReworkLink" target="content"
		style="width: 300%; background-color: gray; color: white; font-family: fantasy; font-size: medium; padding: 25px; font-size: large; white-space: normal;">
		Rework&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
	<br>
	<br>
	<br>
	<br>
	<br>

	<a href="search.jsp" target="content"
		style="width: 300%; background-color: gray; color: white; font-family: fantasy; font-size: medium; padding: 25px; font-size: large; white-space: normal;">Search
		&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
	<%
		}
		else if(rolename.equalsIgnoreCase("authorizer"))
		{
			%>
	<br>
	<br>
	<a href="WorkFlowController?buttonVal=Verify" target="content"
		style="width: 150%; background-color: gray; color: white; font-family: fantasy; font-size: medium; padding: 25px; font-size: large;">Verify&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>

	<br>
	<br>
	<br>
	<br>
	<br>

	<a href="search.jsp" target="content"
		style="width: 300%; background-color: gray; color: white; font-family: fantasy; font-size: medium; padding: 25px; font-size: large; white-space: normal;">Search
		&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
	<%
		}
		else
		{
			out.print("invalid role");
		}
	}
	}	
}
else
{
	request.getRequestDispatcher("login.jsp").include(request,response);
}
%>

</body>
</html>