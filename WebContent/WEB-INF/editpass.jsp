<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Admin Info</title>
    <link rel="stylesheet" href="CSS/styleadmin.css">
    <script>
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
	<h1>Edit Admin</h1>
	<div class="box">
		<form action="user" method="post">
			<div class="textbox">
				<p>New Email</p>
				<input type="email" name="email" required>
			</div>
			<div class="textbox">
				<p>New Password</p>
            	<input id="pw" type="password" name="pass" minlength="8" maxlength="32" required>
        	</div>
			<div class="textbox">
				<p>Confirm Password</p>
            	<input id="cpw" type="password" minlength="8" maxlength="32" required>
        	</div>
			<div class="error" id="error"></div>
			<div>
				<input class="btnleft" value="Confirm" type="button" onclick="checkpw()">
				<button id="submit" style="display:none" type="submit"></button>
			</div>
		</form>
	</div>
  </div>
</body>
</html>
