<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html lang="en" dir="ltr">
<head>
<meta charset="utf-8">
<title>Login & Signup Form</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
	<div class="wrapper">
		<div class="title-text">
			<div class="title login">EasyReview</div>
			<div class="title signup">Signup</div>
		</div>
		<div class="form-container">
			<div class="slide-controls">
				<input type="radio" name="slide" id="login" checked> <input
					type="radio" name="slide" id="signup"> <label for="login"
					class="slide login">Login</label> <label for="signup"
					class="slide signup">Signup</label>
				<div class="slider-tab"></div>
			</div>
			<div class="form-inner">
				<form action="CheckLogin" class="login" method="post">
					<div class="field">
						<input type="text" placeholder="Username" name="username" required>
					</div>
					<div class="field">
						<input type="password" placeholder="Password" name="password"
							required>
					</div>
					<div class="pass-link">
						<a href="#">Forgot password?</a>
					</div>
					<div class="field btn">
						<div class="btn-layer"></div>
						<input type="submit" value="Login">
					</div>
					<div class="signup-link">
						Not a member? <a href="">Signup now</a>
					</div>
				</form>
				<form action="UserRegistration" class="signup" method="post">
					<div class="field">
						<input type="email" placeholder="Email Address" name="email"
							required>
					</div>
					<div class="field">
						<input type="text" placeholder="Username" name="user" required>
					</div>
					<div class="field">
						 <input type="password" placeholder="Password" name="pass"
							required>
					</div>
					<div class="field">
						<input type="password" placeholder="Confirm password"
							name="confirmpass" required>
					</div>
					<div class="field btn">
						<div class="btn-layer"></div>
						<input type="submit" value="Signup" name="provabutton">
					</div>
					<span style="color: red;">${passwordMatch}</span> <span
						style="color: red;">${registrationError}</span>
				</form>
			</div>
		</div>
	</div>
	<script> 
      const loginText = document.querySelector(".title-text .login");
      const loginForm = document.querySelector("form.login");
      const loginBtn = document.querySelector("label.login");
      const signupBtn = document.querySelector("label.signup");
      const signupLink = document.querySelector("form .signup-link a");
      signupBtn.onclick = (()=>{
        loginForm.style.marginLeft = "-50%";
        loginText.style.marginLeft = "-50%";
      });
      loginBtn.onclick = (()=>{
        loginForm.style.marginLeft = "0%";
        loginText.style.marginLeft = "0%";
      });
      signupLink.onclick = (()=>{
        signupBtn.click();
        return false;
      });
    </script>
</body>
</html>