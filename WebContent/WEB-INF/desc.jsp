<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta charset="utf-8">
	<title>Description</title>
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
	<div class="content">
		<h1>What is EIS?</h1>
		<p>The design of our web application for Employee Information System is simple. We use MVC model in Java EE Web Application. It starts with a login page which decide whether you are an employee or an administrator. For employee, they can access their information about what kind of jobs they take and the salary they got. For administrator, they can manipulate all employeeâs information. </p>
	</div>
</body>
</html>