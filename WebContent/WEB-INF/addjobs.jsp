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
				document.getElementById('error').textContent = "Job is already exist!";
	 	}
	</script>
</head>
<body>
	<div class="content">
	<h1>Admin Information</h1>
	<h4>Please fill the form below</h4>
	<div class="box">
		<form action="job?action=add" method="post">
			<div class="textbox">
				<p>Job Code</p>
				<input type="text" name="jobCode" length="6" required>
			</div>
			<div class="textbox">
				<p>Job Name</p>
            	<input type="text" name="jobName" required>
        	</div>
			<div class="textbox">
				<p>Job Salary</p>
            	<input type="number" name="jobSalary" step="0.01" required>
        	</div>
			<div class="error" id="error"></div>
			<div>
				<input class="btnleft" value="Confirm" type="button">
				<button id="submit" style="display:none" type="submit"></button>
			</div>
		</form>
	</div>
  </div>
</body>
</html>
