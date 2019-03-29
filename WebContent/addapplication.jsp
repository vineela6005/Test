<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
String roleName=request.getParameter("roleName");
%>
	<form method="post" action="WorkFlowController"
		enctype="multipart/form-data">
		<input type="hidden" value="<%=roleName%>" name="roleName">
		<div align="center" style="font-style: normal;">
			<br> <br>
			<h3>APPLICATION FORM</h3>
			<table bordercolor="lightgary;" border="2"
				style="border-style: double; padding: 20px; background-color: gray; border-collapse: separate; border-color: black;">

				<tr>
					<th>First Name<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" value="" name="fname"
						required="required"></td>
				</tr>
				<tr>
					<th>Last Name<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" name="lname" required="required">
					</td>
				</tr>
				<tr>
					<th>Middle Name</th>
					<td><input type="text" value="" name="mname"></td>
				</tr>
				<tr>
					<th>Gender<sup style="color: white;">*</sup>
					</th>
					<td><input type="radio" value="Female" name="gender">Female
						<input type="radio" value="Male" name="gender">Male</td>
				</tr>
				<tr>
					<th>Father Name<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" value="" name="fat" required="required">
					</td>
				</tr>
				<tr>
					<th>Date Of Birth<sup style="color: white;">*</sup>
					</th>
					<td><input type="date" value="" name="dob" required="required"
						min="1900/01/01" max="2999/12/12"></td>
				</tr>
				<tr>
					<th>Pan-No<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" value="" name="pan" required="required">
					</td>
				</tr>

				<tr align="center">
					<td colspan="2">
						<h3>Address Information</h3>
					</td>
				</tr>
				<tr>
					<th>Correspondence Address<sup style="color: white;">*</sup>
					</th>
					<td><textarea name="caddress" required="required"
							style="width: 100%;"></textarea></td>
				</tr>
				<tr>
					<th>WorkPlace Address<sup style="color: white;">*</sup>
					</th>
					<td><textarea name="waddress" required="required"
							style="width: 100%;"></textarea></td>
				</tr>
				<tr>
					<th>Permanent Address<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" name="paddress" required="required"></td>
				</tr>
				<tr align="center">
					<th colspan="2" style="color: black;">
						<h3>Employment Information</h3>
					</th>
				</tr>
				<tr>
					<th>Employment Type<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" value="" name="emptype"
						required="required"></td>
				</tr>
				<tr>
					<th>Experience<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" value="" name="exp" required="required">
					</td>
				</tr>
				<tr>
					<th>Designation<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" value="" name="des" required="required">
					</td>
				</tr>
				<tr>
					<th>Annual Income<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" value="" name="income"
						required="required"></td>
				</tr>
				<tr align="center">
					<th colspan="2" style="color: black;">
						<h3>Documents Required</h3>
					</th>
				</tr>
				<tr>
					<th>Application Form<sup style="color: white;">*</sup>
					</th>
					<td><input type="file" value="" name="applicationform"
						required="required"></td>
				</tr>
				<tr>
					<th>Id Proof<sup style="color: white;">*</sup>
					</th>
					<td><input type="file" value="" name="idproof"
						required="required"></td>
				</tr>
				<tr>
					<th>Supporting Documents<sup style="color: white;">*</sup>
					</th>
					<td><input type="file" value="" name="sdoc"
						required="required"></td>
				</tr>
				<tr>
			</table>
			<br> <input type="submit" value="Add" name="buttonVal"
				required="required"
				style="border-color: black; padding: 10px; background-color: gray; color: black; border-style: none; border-color: black; font-style: italic;">
			<input type="reset" value="Reset" required="required"
				style="border-color: black; padding: 10px; background-color: gray; color: black; border-style: none;; border-color: black; font-style: italic;">

		</div>
	</form>
</body>
</html>