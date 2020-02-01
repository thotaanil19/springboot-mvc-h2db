<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<HTML>
 <HEAD>
  <TITLE>Admin Profile Tab</TITLE>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link rel="stylesheet" href="/data-api/css/foundation.css?lmts=2016-02-03-15-04-50"" />
  <link rel="stylesheet" href="/data-api/css/common.css?lmts=2016-02-08-15-04-50">
  <link rel="stylesheet" href="/data-api/css/admin.css?lmts=2016-02-08-15-04-50">
 </HEAD>
 <BODY>
	<jsp:include page="../header.jsp"></jsp:include>
	<div class="row">
		<div class="large-12 medium-12 small-12 marginTop20">
			<div class="admin-dashboard large-6 medium-6 small-12 columns">
				Admin Dashboard
			</div>
			<div class="changePwd large-6 medium-6 small-12 columns" id="change-pwd">
				Change Password
			</div>
		</div>
	</div>
	<div class="row">
		<div class="large-12 medium-12 small-12">
			<section class="top-bar-section marginTop20">
				<ul>
					<li class="active small-12"><a href="../admin/profileTab">Admin Profile</a></li>
					<li class="small-12"><a href="../admin/usersTab">Admin Users</a></li>
					<!-- <li class="small-12"><a href="../admin/tracksTab">Tracks</a></li> -->
					<li class="small-12"><a href="../admin/productsTab">Products</a></li>
					<li class="small-12"><a href="../admin/customersTab">Customers</a></li>
					<li class="small-12"><a href="../admin/reportsTab">Reports</a></li>
				</ul>
			</section>
		</div>
	</div>
	<div class="container">
		<div class="row ">
			<div class="large-6 medium-6 small-12 columns admin-profile-text marginTop20">
				<div class="admin-details">
					Admin Profile Details
				</div>	
				<div class= "admin-user-details" id="adminProfile">
					
				</div>
			</div>
			<div class="large-6 medium-6 small-12 columns change-pwd-text hide" id="change-pwd-div">
				<div class="admin-details text-center">
					Change Password
				</div>
				 <div class="pwd-success hide text-center"></div>	
				 <div class="pwd-error error-msz hide">
				 	Passwords should require a minimum of 12 characters and at least 3 of the following</br>
					1.An upper case letter 2.A lower case letter 3.A digit 4.Any of the following !$%@#*_
				 </div>	
				 <div class="pwd-rules">
				 	Passwords should require a minimum of 12 characters and at least 3 of the following</br>
					1.An upper case letter 2.A lower case letter 3.A digit 4.Any of the following !$%@#*_
				 </div>	
				<div class= "change-pwd-details">
					<div class="password-div">
						<input type="password" placeholder="Password" name="password" id="password-new" class="text-field" maxlength="60" / >
						<img src="/data-api/images/password-icon.png">
					</div>
					<div class="password-div">
						<input type="password" placeholder="Confirm Password" name="confirmpassword" id="cnfrm-password" class="text-field" maxlength="60" />
						<img src="/data-api/images/password-icon.png">
					</div>
				</div>
				<div class="buttons-div right">
					<input type="button" value="Save" class="update-button" id="pwd-update-button">
					<input type="button" value="Cancel" class="cancel-button" id="pwd-cancel-button">
				</div>
			</div>
		</div> 
	</div>
	
	<jsp:include page="../footer.jsp"></jsp:include>
	
	<script type="text/javascript" src="/data-api/js/prototype.js"></script>
	<script type="text/javascript" src="/data-api/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="/data-api/js/foundation.min.js"></script>
	<script type="text/javascript" src="/data-api/js/controller.js?lmts=2016-02-08-15-04-50"></script>
	<script type="text/javascript" src="/data-api/js/common.js?lmts=2016-02-08-15-04-50"></script>
	<script type="text/javascript" src="/data-api/js/admin.js?lmts=2016-02-08-15-04-50"></script>
	<script>
	$(document).foundation();
	</script>
 </BODY>
</HTML>
