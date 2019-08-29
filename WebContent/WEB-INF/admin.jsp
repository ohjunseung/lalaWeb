<html>
<head>
	<meta charset="utf-8">
	<title>Admin Page</title>
	<link rel="stylesheet" href="/CSS/styleadmin.css">
</head>
<body>
	<div class="content">
	<h1>Employee Infomation</h1>
	 <c:forEach var = "i" begin = "1" end = "5">
    <table class="box" border="1px">
		<tr>
			<td rowspan="2" class="pic">A</td>
			<td class="info">ID</td>
		</tr>
		<tr>
			<td class="info">Jobs</td>
		</tr>
		<tr>
			<td colspan="2" class="info">Firstname</td>
		</tr>
		<tr>
			<td colspan="2" class="info">Lastname</td>
		</tr>
		<tr>
			<td colspan="2" class="info">Email</td>
		</tr>
		<tr>
			<td colspan="2" class="info">Phone</td>
		</tr>
		<tr>
			<td colspan="2" class="info">Salary</td>
		</tr>
		<tr>
			<td class="edit">Edit</td>
		</tr>
		 </table> <br/>
   </c:forEach> 
	</div>
</body>
</html>