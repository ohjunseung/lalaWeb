<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Add user</title>
	<link rel="stylesheet" href="CSS/styleadmin.css">
</head>
<body>
	<div class="box">
		<form action="employee" method="post">
			<div class="textbox">
				<p>ID</p>
				<input type="number" name="id">
			</div>
			<div class="textbox">
				<p>Job</p>
				<input type="text" name="job" >
			</div>
			<div class="textbox">
				<p>Firstname</p>
				<input class="edit" type="text" name="fname" required>
			</div>
			<div class="textbox">
				<p>Lastname</p>
				<input class="edit" type="text" name="lname" required>
			</div>
			<div class="textbox">
				<p>Email</p>
				<input class="edit" type="email" name="email" required>
			</div>
			<div class="textbox">
            	<input type="password" placeholder="Password" name="pass">
        	</div>
			<div class="textbox">
            	<input type="password" placeholder="Confirm Password" name="cpass">
        	</div>
			<div class="textbox">
				<p>Phone number</p>
				<input class="edit" type="text" name="phone" required>
			</div>
			<div>
				<input class="btnleft" type="submit" value="Confirm">
			</div>
		</form>
	</div>
</body>
</html>