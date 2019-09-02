<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--TODO Type check on form--%>
<html>
<head>
	<meta charset="utf-8">
	<title>Index</title>
	<link rel="stylesheet" href="CSS/styleindex.css">
	<script>
		function btn1(){
			 var edits = document.getElementsByClassName("edit"); 
			    for (var i = 0; i < edits.length; i++) { 
			        edits[i].removeAttribute("disabled");  
				}
			 document.getElementsByClassName("btnright")[0].style.display = "inline";
		}
		function btn2(){
			document.getElementByClassName("btnright")[0].style.display = "none";
		}
	</script>
</head>
<body>
	<div class="navbar">
		<h2 id="Name">Company Name</h2>
		<div class="menu">
			<a href="${pageContext.request.contextPath}">Home</a>
			<a href="${pageContext.request.contextPath}/desc">Description</a>
			<a href="${pageContext.request.contextPath}/aboutus">About</a>
		</div>
	</div>
	<div class="box">
		<form action="employee" method="post">
			<div class="textbox">
				<p>ID</p>
				<input type="number" name="id" readonly value=${employee.id}>
			</div>
			<div class="textbox">
				<p>Job</p>
				<input type="text" name="job" disabled value=${employee.jobName} >
			</div>
			<div class="textbox">
				<p>Firstname</p>
				<input class="edit" type="text" name="fname" disabled value=${employee.fname} required>
			</div>
			<div class="textbox">
				<p>Lastname</p>
				<input class="edit" type="text" name="lname" disabled value=${employee.lname} required>
			</div>
			<div class="textbox">
				<p>Email</p>
				<input class="edit" type="email" name="email" disabled value=${employee.email} required>
			</div>
			<div class="textbox">
				<p>Phone number</p>
				<input class="edit" type="text" name="phone" disabled value=${employee.phone} required>
			</div>
			<div class="textbox">
				<p>Salary</p>
				<input type="number" name="salary" disabled value=${employee.salary} >
			</div>
			<div>
				<input class="btnleft" type="button" value="Edit" onclick="btn1()">
				<input class="btnright" type="submit" value="Confirm" onclick="btn2()">
			</div>
		</form>
	</div>
</body>
</html>