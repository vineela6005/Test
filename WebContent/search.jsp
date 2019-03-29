<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Search</title>
</head>
<body>
	<form method="post" action="SearchController">
		<div align="center">
			<table
				style="border-style: double; background-color: gray; border-collapse: separate;">
				<tr>
					<th>Application Number</th>
					<td><input type="text" name="app_no" style="width: 100%;"></td>
				</tr>
				<tr>
					<th>CustId</th>
					<td><input type="text" name="custid" style="width: 100%;"></td>
				</tr>
				<tr>
					<th>Application Status</th>
					<td><select name="app_status" style="width: 100%;">
							<option>All</option>
							<option>new</option>
							<option>rework</option>
							<option>approved</option>
							<option>verify</option>
							<option>rejected</option>
					</select></td>
				</tr>
				<tr>
					<th>From Date</th>
					<td><input type="text" name="from_date"
						placeholder="dd-month-yyyy" style="width: 100%;"></td>
				</tr>
				<tr>
					<th>To Date</th>
					<td><input type="text" name="to_date"
						placeholder="dd-month-yyyy" style="width: 100%;"></td>
				</tr>
			</table>
			<br> <input type="Submit" name="buttonVal" value="Search"
				style="background-color: black; color: white; padding: 10px;">
		</div>
	</form>
</body>
</html>