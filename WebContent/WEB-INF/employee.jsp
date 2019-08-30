<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta charset="utf-8">
	<title>Index</title>
	<link rel="stylesheet" href="CSS/styleindex.css">
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
		<form>
			<div class="textbox">
				<p>ID</p>
				<input type="text" name="id" disabled value=${employee.id}>
			</div>
			<div class="textbox">
				<p>Job</p>
				<input type="text" name="job" disabled value=${employee.jobName} >
			</div>
			<div class="textbox">
				<p>Firstname</p>
				<input type="text" name="fName" disabled value=${employee.fName} >
			</div>
			<div class="textbox">
				<p>Lastname</p>
				<input type="text" name="lName" disabled value=${employee.lName} >
			</div>
			<div class="textbox">
				<p>Email</p>
				<input type="text" name="email" disabled value=${employee.email}>
			</div>
			<div class="textbox">
				<p>Phone number</p>
				<input type="text" name="phone" disabled value=${employee.phone} >
			</div>
			<div class="textbox">
				<p>Salary</p>
				<input type="text" name="salary" disabled value=${employee.salary} >
			</div>
			<div>
				<input class="btn" type="button" value="Edit">
			</div>
		</form>
	</div>
</body>
</html>