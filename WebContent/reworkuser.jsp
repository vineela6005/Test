
<%@page import="com.ibm.xtq.bcel.generic.LADD"%>
<%@page import="java.sql.Date"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
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
	<%
		Object attribute = request.getAttribute("objArray");
		ArrayList<HashMap<String, String>> objArrayList = (ArrayList<HashMap<String, String>>) attribute;
		System.out.println("obj array list  : "+objArrayList);
		Iterator<HashMap<String, String>> iterator = objArrayList.iterator();
		String firstname = null, lastname = null, middlename = null, middlename1=null,pan_no = null, gender = null,
				fathername = null, cor_address = null, work_address = null, per_address = null,
				employment_type = null, experience = null, designation = null;
		String annual_income = null, custid = null,app_no=null;
		String date = null;

		while (iterator.hasNext()) {
			HashMap<String, String> objHashMap = iterator.next();

			firstname = objHashMap.get("firstname");
			lastname = objHashMap.get("lastname");
			middlename1 = objHashMap.get("middlename");
			if (middlename1 == null) {
				middlename = " ";
			}
			else
			{
				middlename=middlename1;
			}
			pan_no = objHashMap.get("pan_no");
			gender = objHashMap.get("gender");
			date = objHashMap.get("dob");
			fathername = objHashMap.get("fathername");
			custid = objHashMap.get("custid");
			System.out.println("custid  : " + custid);

			cor_address = objHashMap.get("cor_address");
			work_address = objHashMap.get("work_address");
			per_address = objHashMap.get("per_address");

			employment_type = objHashMap.get("employment_type");
			experience = objHashMap.get("experience");
			designation = objHashMap.get("designation");
			annual_income = objHashMap.get("annual_income");
			app_no=objHashMap.get("app_no");

		}
	%>
	<form method="post" action="WorkFlowController"
		enctype="multipart/form-data">
		<input type="hidden" value="<%=custid%>" name="custid">
		<div align="center" style="font-style: normal;">
			<br> <br>
			<h3>APPLICATION FORM</h3>
			<table bordercolor="lightgary;" border="2"
				style="border-style: double; padding: 20px; background-color: gray; border-collapse: separate; border-color: black;">
				<tr>
					<th>First Name<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" value="<%=firstname%>" name="fname"></td>
				</tr>
				<tr>
					<th>Last Name<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" name="lname" value="<%=lastname%>"></td>
				</tr>
				<tr>
					<th>Middle Name</th>
					<td><input type="text" name="mname" value="<%=middlename%>"></td>
				</tr>
				<tr>
					<th>Gender<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" name="gender" value="<%=gender%>"></td>
				</tr>
				<tr>
					<th>Father Name<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" name="fat" value="<%=fathername%>"></td>
				</tr>
				<tr>
					<th>Date Of Birth<sup style="color: white;">*</sup>
					</th>
					<td><input type="date" value="<%=date%>" name="dob"></td>
				</tr>
				<tr align="center">
					<td colspan="2">
						<h3>Address Information</h3>
					</td>
				</tr>
				<tr>
					<th>Correspondence Address<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" name="cor_address"
						value="<%=cor_address%>"></td>
				</tr>
				<tr>
					<th>WorkPlace Address<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" name="work_address"
						value="<%=work_address%>"></td>
				</tr>
				<tr>
					<th>Permanent Address<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" name="per_address"
						value="<%=per_address%>"></td>
				</tr>
				<tr align="center">
					<td colspan="2">
						<h3>Employment Information</h3>
					</td>
				</tr>
				<tr>
					<th>Employment Type<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" value="<%=employment_type%>"
						name="emptype"></td>
				</tr>
				<tr>
					<th>Experience<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" value="<%=experience%>" name="exp"></td>
				</tr>
				<tr>
					<th>Designation<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" value="<%=designation%>" name="des"></td>
				</tr>
				<tr>
					<th>Annual Income<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" value="<%=annual_income%>"
						name="income"></td>
				</tr>
				<tr>
					<th>Pan-No<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" value="<%=pan_no%>" name="pan"></td>
				</tr>
				<tr align="center">
					<td colspan="2">
						<h3>Documents Required</h3>
					</td>
				</tr>
				<tr>
					<th>Application Form<sup style="color: white;">*</sup>
					</th>
					<td><br>
					<a
						href="WorkFlowController?buttonVal=app_form&app_no=<%=app_no%>&form=applicationform"
						style="color: white;">Application Form</a><br>
					<br> <input type="file" value="app_form"
						name="applicationform"><br>
					<br></td>
				</tr>
				<tr>
					<th>ID Proof<sup style="color: white;">*</sup>
					</th>
					<td><br>
					<a
						href="WorkFlowController?buttonVal=id_proof&app_no=<%=app_no%>&form=idproof"
						style="color: white;">ID Proof</a> <br>
					<br> <input type="file" value="idproof" name="idproof"><br>
					<br></td>
				</tr>
				<tr>
					<th>Supporting Documents <sup style="color: white;">*</sup>
					</th>
					<td><br> <a
						href="WorkFlowController?buttonVal=sdocs&app_no=<%=app_no%>&form=sdoc"
						style="color: white;">Supporting Documents</a> <br>
					<br> <input type="file" value="sdoc" name="sdoc"><br>
					<br></td>
				</tr>
			</table>
			<br> <input type="submit" value="Submit" name="buttonVal"
				style="border-color: black; padding: 10px; background-color: gray; color: black; border-style: none; border-color: black; font-style: normal;">
		</div>
	</form>
</body>
</html>