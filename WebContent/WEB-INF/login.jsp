<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Login Form</title>
    <link rel="stylesheet" href="CSS/style.css">
    <script>
        window.onload = () => {
				const urlSearchParams = new URLSearchParams(window.location.search);
				const param = urlSearchParams.get('incorrect');
				if (param === 'true')
					document.getElementById('error').textContent = "Email or password is wrong!";
		}
    </script>
</head>
<body>
<div class="login-box">
    <h1>Login</h1>
    <form id="login" action="login" method="post">
        <div class="textbox">
            <i class="fa fa-user" aria-hidden="true"></i>
            <input type="text" placeholder="Email" name="email">
        </div>
        <div class="textbox">
            <i class="fa fa-lock" aria-hidden="true"></i>
            <input type="password" placeholder="Password" name="pass">
        </div>
        <div class="error" id="error"></div>
        <input class="btnleft" type="submit" value="Submit">
        <input class="btnright" type="reset" value="Reset">
    </form>
</div>
</body>
</html>