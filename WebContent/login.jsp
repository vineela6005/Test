<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
</head>
<script src="Js/login.js">
</script>
<body style="background-color: white;">

	<jsp:include page="frameheader.jsp">
	</jsp:include>

	<form method="post" action="LoginServlet" name="loginform" onsubmit='return formValidation()'>
		<div align="center" style="font-style: normal;">
			<br> <br>
			<h3>LOGIN FORM</h3>
			<table style="border-style: double; padding: 15px;">
				<tr>
					<th>UserId</th>
					<td><input type="text" value="" name="id"></td>
				</tr>
				<tr>
					<th>Password</th>
					<td><input type="password" name="upass"></td>
				</tr>
			</table>
			<br> <input type="submit" value="Submit"
				style="padding: 10px; background-color: gray; color: black; border-style: none;; border-color: black; font-style: italic;">

		</div>
	</form>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<jsp:include page="framefooter.jsp">
	</jsp:include>
</body>
</html>