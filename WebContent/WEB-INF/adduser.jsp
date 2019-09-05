<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Add Employee</title>
	<link rel="stylesheet" href="CSS/styleadmin.css">
	<script>
	 	window.onload = () => {
			const urlSearchParams = new URLSearchParams(window.location.search);
			const param = urlSearchParams.get('incorrect');
			if (param === 'true')
				document.getElementById('error').textContent = "Employee is already exist!";
		}
	 	function checkpw(){
	 		if (document.getElementById('pw').value === document.getElementById('cpw').value){
	 			document.getElementById('submit').click();
	 		}else{
	 			document.getElementById('error').textContent = "Password doesn't match!";
	 		}
	 	}
	</script>
</head>
<body>
  <div class="content">
	<h1>Employee Information</h1>
	<h4>Please fill the form below</h4>
	<div class="box">
		<form action="admin?action=add" method="post">
			<div class="textbox">
				<p>Job</p>
				<select id="job" name="job" required>
					<c:forEach var = "a" items = "${jobs}">
						<option value=${a.key}>${a.value}</option>
					</c:forEach>
				</select>
			</div>
			<div class="textbox">
				<p>Firstname</p>
				<input type="text" name="fname" required>
			</div>
			<div class="textbox">
				<p>Lastname</p>
				<input type="text" name="lname" required>
			</div>
			<div class="textbox">
				<p>Email</p>
				<input type="email" name="email" required>
			</div>
			<div class="textbox">
				<p>Password</p>
            	<input id="pw" type="password" name="pass" minlength="8" maxlength="32" required>
        	</div>
			<div class="textbox">
				<p>Confirm Password</p>
            	<input id="cpw" type="password" minlength="8" maxlength="32" required>
        	</div>
			<div class="textbox">
				<p>Phone number</p>
				<input class="edit" type="tel" name="phone" pattern="[0-9]{3}-[0-9]{4}-[0-9]{4}" required>
			</div>
			<div class="textbox">
				<input type="text" value="Format: 139-2525-4324" disabled>
			</div>
			<div class="error" id="error"></div>
			<div>
				<input class="btnleft" value="Confirm" type="button" onclick="checkpw()">
				<button id="submit" style="display: none" type="submit"></button>
			</div>
		</form>
	</div>
  </div>
</body>
</html>