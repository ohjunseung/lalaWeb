<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta charset="utf-8">
	<title>Register</title>
	<link rel="stylesheet" href="CSS/style.css">
</head>
<body>
	<div class="login-box">
		<form action="/register" method="post">
			<h1>Employee Information</h1>
			<div class="container">
				<div class="textbox">
				<input class="textbox" type="text" placeholder="Firstname" name="fName">
				</div>
				<div class="textbox">
				<input class="textbox" type="text" placeholder="Lastname" name="lName">
				</div>
				<div class="textbox">
				<input class="textbox" type="email" placeholder="Email" name="email">
				</div>
				<div class="textbox">
				<input class="textbox" type="text" placeholder="Job" name="job">
				</div>
				<div class="textbox">
				<input class="textbox" type="text" placeholder="Phone Number" name="phone">
				</div>
				<button class="btnleft" type="submit">Submit</button>
			</div>
		</form>
	</div>
</body>
</html>