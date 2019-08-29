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
			<a href="index.jsp">Home</a>
			<a href="desc.jsp">Description</a>
			<a href="aboutus.jsp">About</a>
		</div>
	</div>
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
		</table>
</body>
</html>