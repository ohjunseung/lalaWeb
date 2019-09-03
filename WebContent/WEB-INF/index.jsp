<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	<div>
		<a href="login">Login</a>
	</div>
</body>
</html>