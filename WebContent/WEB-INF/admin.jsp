<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta charset="utf-8">
	<title>Admin Page</title>
	<link rel="stylesheet" href="CSS/styleadmin.css">
</head>
<body>
	<div class="content">
	<h1>Employee Infomation</h1>
	<c:forEach var = "i" items = "${employeeData}">
    	<div class="box">
		<form>
			<div class="textbox">
				<p>ID</p>
				<input type="text" name="id" disabled value=${i.id}>
			</div>
			<div class="textbox">
				<p>Job</p>
				<input type="text" name="job" disabled value=${i.jobName} >
			</div>
			<div class="textbox">
				<p>Firstname</p>
				<input type="text" name="fName" disabled value=${i.fName} >
			</div>
			<div class="textbox">
				<p>Lastname</p>
				<input type="text" name="lName" disabled value=${i.lName} >
			</div>
			<div class="textbox">
				<p>Email</p>
				<input type="text" name="email" disabled value=${i.email}>
			</div>
			<div class="textbox">
				<p>Phone number</p>
				<input type="text" name="phone" disabled value=${i.phone} >
			</div>
			<div class="textbox">
				<p>Salary</p>
				<input type="text" name="salary" disabled value=${i.salary} >
			</div>
			<div>
				<input class="btn" type="button" value="Edit">
			</div>
		</form>
	</div>
   </c:forEach> 
	</div>
</body>
</html>