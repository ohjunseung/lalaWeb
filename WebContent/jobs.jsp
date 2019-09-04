<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Job List</title>
<link rel="stylesheet" href="CSS/styleadmin.css">
</head>
<body>
	<div class="content">
	<h1>Employee Information</h1>
		<div class="hyperlink-box">
			<a href="job?action=add">Add Job</a>
			<a href="admin">Home</a>
		</div>
	<c:forEach var = "i" items = "${jobs}" varStatus="loop">
		<script>
		function btn1${loop.index}(){
			 var edits = document.getElementsByClassName("edit${loop.index}"); 
			    for (var i = 0; i < edits.length; i++) {
			        edits[i].removeAttribute("disabled");
				}
			document.getElementById("btnright${loop.index}").style.display = "inline";
		}
		</script>
    	<div class="box">
		<form action="admin" method="post">
			<div class="textbox">
				<p>Job Code</p>
				<input type="text" name="jobCode" value=${i.jobCode} disabled>
			</div>
			<div class="textbox">
				<p>Job Name</p>
            	<input type="text" name="jobName" value=${i.jobName} disabled>
        	</div>
			<div class="textbox">
				<p>Job Salary</p>
            	<input type="number" name="jobSalary" step="0.01" value=${i.jobSalary} disabled>
        	</div>
			<div>
				<input class="btnleft" type="button" value="Edit" onclick="btn1${loop.index}()">
				<input id="btnright${loop.index}" class="btnright" type="submit" value="Confirm">
			</div>
		</form>
	</div>
   </c:forEach> 
	</div>
</body>
</html>