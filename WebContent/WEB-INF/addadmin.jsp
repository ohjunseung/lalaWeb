<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="CSS/styleadmin.css">
	<script>
	 	window.onload = () => {
			const urlSearchParams = new URLSearchParams(window.location.search);
			const param = urlSearchParams.get('incorrect');
			if (param === 'true')
				document.getElementById('error').textContent = "Email is already exist!";
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
	<h1>Admin Information</h1>
	<h4>Please fill the form below</h4>
	<div class="box">
		<form action="admin?action=add" method="post">
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
