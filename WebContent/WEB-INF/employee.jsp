<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
		<form action="login" method="post">
			<div class="textbox">
				<p>ID</p>
				<input type="text" name="id" readonly value=${employee.id}>
			</div>
			<div class="textbox">
				<p>Job</p>
				<input type="text" name="job" disabled value=${employee.jobName} >
			</div>
			<div class="textbox">
				<p>Firstname</p>
				<input class="edit" type="text" name="fName" disabled value=${employee.fname} >
			</div>
			<div class="textbox">
				<p>Lastname</p>
				<input class="edit" type="text" name="lName" disabled value=${employee.lname} >
			</div>
			<div class="textbox">
				<p>Email</p>
				<input class="edit" type="text" name="email" disabled value=${employee.email}>
			</div>
			<div class="textbox">
				<p>Phone number</p>
				<input class="edit" type="text" name="phone" disabled value=${employee.phone} >
			</div>
			<div class="textbox">
				<p>Salary</p>
				<input type="text" name="salary" disabled value=${employee.salary} >
			</div>
			<div>
				<input class="btnleft" type="button" value="Edit" onclick="btn1()">
				<input class="btnright" type="submit" value="Confirm" onclick="btn2()">
			</div>
		</form>
	</div>
</body>
</html>