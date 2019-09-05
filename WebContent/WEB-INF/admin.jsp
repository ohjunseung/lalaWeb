<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta charset="utf-8">
	<title>Admin</title>
	<link rel="stylesheet" href="CSS/styleadmin.css">
</head>
<body>
	<div class="content">
	<h1>Employee Information</h1>
		<div class="hyperlink-box">
			<a href="?action=add">Add Employee</a>
			<a href="job">Job List</a>
			<a href="user">Edit Password</a>
			<a href="user?action=add">Add Admin</a>
		</div>
	<c:forEach var = "i" items = "${employeeData}" varStatus="loop">
		<script>
		function btn1${loop.index}(){
			 var edits = document.getElementsByClassName("edit${loop.index}"); 
			    for (var i = 0; i < edits.length; i++) {
			        edits[i].removeAttribute("disabled");
				}
			document.getElementById("btnright${loop.index}").style.display = "inline";
			document.getElementById("btndelete${loop.index}").style.display = "inline";
		}
		function btndel${loop.index}(){
			document.getElementById("delete${loop.index}").removeAttribute("disabled");
			document.getElementById("btnright${loop.index}").click();
		}
		</script>
    	<div class="box">
		<form action="admin" method="post">
			<div class="textbox">
				<p>ID</p>
				<input class="edit${loop.index}" type="text" name="id" disabled value=${i.id} required>
				<input type="hidden" name="oldID" value=${i.id} readonly>
			</div>
			<div class="textbox">
				<p>Job</p>
				<select id="job${loop.index}" class="edit${loop.index}" disabled required name="job">
					<c:forEach var = "a" items = "${jobs}">
						<option value=${a.key}>${a.value}</option>
					</c:forEach>
				</select>
				<script>
					document.getElementById("job${loop.index}").value = "${i.jobCode}";
				</script>
			</div>
			<div class="textbox">
				<p>Firstname</p>
				<input type="text" name="fname" disabled value=${i.fname} >
			</div>
			<div class="textbox">
				<p>Lastname</p>
				<input type="text" name="lname" disabled value=${i.lname} >
			</div>
			<div class="textbox">
				<p>Email</p>
				<input type="email" name="email" disabled value=${i.email}>
			</div>
			<div class="textbox">
				<p>Phone number</p>
				<input type="text" name="phone" disabled value=${i.phone} >
			</div>
			<div class="textbox">
				<p>Salary</p>
				<input type="number" name="salary" disabled value=${i.salary} >
			</div>
			<div class="button-box">
				<input class="btnleft" type="button" value="Edit" onclick="btn1${loop.index}()">
				<input id="delete${loop.index}" type="hidden" disabled value="delete" name="action">
				<button id="btndelete${loop.index}" class="btndelete" onclick="btndel${loop.index}()">Delete</button>
				<input id="btnright${loop.index}" class="btnright" type="submit" value="Confirm">
			</div>
		</form>
	</div>
   </c:forEach> 
	</div>
</body>
</html>