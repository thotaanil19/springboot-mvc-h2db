<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<HTML>
 <HEAD>
  <TITLE>Equibase-DataAPI-Login</TITLE>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link rel="stylesheet" href="/data-api/css/foundation.css" />
  <link rel="stylesheet" href="/data-api/css/common.css?lmts=2016-02-11-15-04-50">
  <link rel="stylesheet" href="/data-api/css/login.css?lmts=2016-02-11-15-04-50">
 </HEAD>
 <BODY>
	<jsp:include page="header.jsp"></jsp:include>
	<form id="login" class="login-form-eq1 form-eq1-lg paddingTop100 container" name='login' action="/data-api/j_spring_security_check" method='POST'>
		<div class="container">
			<div class="login-div">
				<span class="logout-msz hide">${message}</span>
				<span class="invalid-error hide">${error}</span>
				<div class="large-12">
					<div class="large-4 paddingTop25">
						<input type="text" placeholder="User Name" name="username" class="login-error" />
						<img src="/data-api/images/user.png">
					</div>
					<div class="large-4">
						<input type="password" placeholder="Password" name="password" class="login-error"/>
						<img src="/data-api/images/password.png">
					</div>
					<div class="large-4">
						<span class="right forgot-password" data-reveal-id="forgotPasswordModal">Forgot Password?</span>
						<input type="submit" value="Log In" class="login-button">
					</div>
				</div>
			</div>
		</div>
	</form>
	
	<jsp:include page="footer.jsp"></jsp:include>
	
	<div id="forgotPasswordModal" class="reveal-modal" data-reveal aria-labelledby="modalTitle" aria-hidden="true" role="dialog">
	 	<div class="forgot-text">Forgot Password</div>
	 	<a class="close-reveal-modal" aria-label="Close"><img src="/data-api/images/close-icon.png"></a>
		<div class="forgot-content marginTop20">
			If you have forgotten your password, please contact the Online Help Department at 
			(XXX) XXX-XXX for help.
		</div>
	</div>

	<script type="text/javascript" src="/data-api/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="/data-api/js/foundation.min.js"></script>
	<script type="text/javascript" src="/data-api/js/controller.js?lmts=2016-02-03-15-04-50"></script>
	<script type="text/javascript" src="/data-api/js/common.js?lmts=2016-02-03-15-04-50"></script>
	<script type="text/javascript" src="/data-api/js/login.js?lmts=2016-02-03-15-04-50"></script>
	<script>
	$(document).foundation();
	</script>
 </BODY>
</HTML>
