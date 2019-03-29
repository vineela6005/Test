
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

		Iterator<HashMap<String, String>> iterator = objArrayList.iterator();
		String firstname = null, lastname = null, middlename = null, middlename1 = null, pan_no = null,
				gender = null, fathername = null, cor_address = null, work_address = null, per_address = null,
				employment_type = null, experience = null, designation = null;
		String annual_income = null, custid = null, app_no = null;
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
			app_no = objHashMap.get("app_no");

		}
	%>
	<form method="post" action="WorkFlowController">
		<input type="hidden" value="<%=custid%>" name="custid">
		<div align="center" style="font-style: normal;">
			<br> <br>
			<h3>APPLICATION FORM</h3>
			<table bordercolor="lightgary;" border="2"
				style="border-style: double; padding: 20px; background-color: gray; border-collapse: separate; border-color: black;">
				<tr>
					<th>First Name<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" value="<%=firstname%>" name="fname"
						readonly="readonly"></td>
				</tr>
				<tr>
					<th>Last Name<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" name="lname" readonly="readonly"
						value="<%=lastname%>"></td>
				</tr>
				<tr>
					<th>Middle Name</th>
					<td><input type="text" name="mname" readonly="readonly"
						value="<%=middlename%>"></td>
				</tr>
				<tr>
					<th>Gender<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" name="gender" readonly="readonly"
						value="<%=gender%>"></td>
				</tr>
				<tr>
					<th>Father Name<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" name="fat" readonly="readonly"
						value="<%=fathername%>"></td>
				</tr>
				<tr>
					<th>Date Of Birth<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" value="<%=date%>" name="dob"
						readonly="readonly"></td>
				</tr>
				<tr>
					<th>Correspondence Address<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" name="cor_address" readonly="readonly"
						value="<%=cor_address%>"></td>
				</tr>
				<tr>
					<th>WorkPlace Address<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" name="work_address" readonly="readonly"
						value="<%=work_address%>"></td>
				</tr>
				<tr>
					<th>Permanent Address<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" name="per_address" readonly="readonly"
						value="<%=per_address%>"></td>
				</tr>
				<tr>
					<th>Employment Type<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" value="<%=employment_type%>"
						name="emptype" readonly="readonly"></td>
				</tr>
				<tr>
					<th>Experience<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" value="<%=experience%>" name="exp"
						readonly="readonly"></td>
				</tr>
				<tr>
					<th>Designation<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" value="<%=designation%>" name="des"
						readonly="readonly"></td>
				</tr>
				<tr>
					<th>Annual Income<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" value="<%=annual_income%>"
						name="income" readonly="readonly"></td>
				</tr>
				<tr>
					<th>Pan-No<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" value="<%=pan_no%>" name="pan"
						readonly="readonly"></td>
				</tr>
				<tr>
					<th>Card Type<sup style="color: white;">*</sup>
					</th>
					<td><select name="card_type" required="required">
							<option>Select</option>
							<option>Platinum</option>
							<option>Gold</option>
							<option>Silver</option>
					</select></td>
				</tr>
				<tr>
					<th>Credit Risk<sup style="color: white;">*</sup>
					</th>
					<td><select name="credit_risk" required="required">
							<option>Select</option>
							<option>High</option>
							<option>Low</option>
							<option>Medium</option>
					</select></td>
				</tr>
				<tr>
					<th>KYC check<sup style="color: white;">*</sup>
					</th>
					<td><input type="radio" value="" name="kyc_check"
						required="required">Yes <input type="radio" value=""
						name="kyc_check">No</td>
				</tr>
				<tr>
					<th>Card Details<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" value="" name="card_details"
						required="required"></td>
				</tr>
				<tr>
					<th>Credit Limit<sup style="color: white;">*</sup>
					</th>
					<td><input type="text" value="" name="credit_limit"
						required="required"></td>
				</tr>
				<tr>
					<th>Application Form<sup style="color: white;">*</sup>
					</th>
					<td><a
						href="WorkFlowController?buttonVal=app_form&app_no=<%=app_no%>&form=applicationform"
						style="color: white;">Application Form</a></td>
				</tr>
				<tr>
					<th>ID Proof<sup style="color: white;">*</sup>
					</th>
					<td><a
						href="WorkFlowController?buttonVal=id_proof&app_no=<%=app_no%>&form=idproof"
						style="color: white;">ID Proof</a></td>
				</tr>
				<tr>
					<th>Supporting Documents <sup style="color: white;">*</sup>
					</th>
					<td><a
						href="WorkFlowController?buttonVal=sdocs&app_no=<%=app_no%>&form=sdoc"
						style="color: white;">Supporting Documents</a></td>
				</tr>
			</table>
			<br>
			<%--  <a
				href="WorkFlowController?buttonVal=approve&custid=<%=custid%>&cardtype=<%= %>"
				style="border-color: black; padding: 10px; background-color: gray; color: black; 
				border-style: none; border-color: black; font-style: normal;">Approve</a> --%>

			<input type="submit" value="Approve" name="buttonVal"
				style="border-color: black; padding: 10px; background-color: gray; color: black; border-style: none;; border-color: black; font-style: normal;">

			<input type="submit" value="Rework" name="buttonVal"
				style="border-color: black; padding: 10px; background-color: gray; color: black; border-style: none;; border-color: black; font-style: normal;">
			<%-- 		
			<a href="WorkFlowController?buttonVal=rework&custid=<%=custid%>"
				style="border-color: black; padding: 10px; background-color: gray; color: black; 
				border-style: none;; border-color: black; font-style: normal;">Rework</a> --%>

			<a href="WorkFlowController?buttonVal=reject&custid=<%=custid%>"
				style="border-color: black; padding: 9px; background-color: gray; color: black; border-style: none;; border-color: black; font-style: normal;">Reject</a>
		</div>
	</form>
</body>
</html>