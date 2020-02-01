<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<HTML>
 <HEAD>
  <TITLE>Admin Users Tab</TITLE>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link rel="stylesheet" href="/data-api/css/foundation.css" />
  <link rel="stylesheet" href="/data-api/css/common.css?lmts=2016-11-09-15-04-50">
  <link rel="stylesheet" href="/data-api/css/admin.css?lmts=2016-11-09-15-04-50">
 </HEAD>
 <BODY>
 <jsp:include page="../header.jsp"></jsp:include>
	<div class="row">
		<div class="large-12 medium-12 small-12 marginTop20">
			<div class="admin-dashboard large-6 medium-6 small-12 columns">
				Admin Dashboard
			</div>
			<div class="large-6 medium-6 small-12 columns hide">
			</div>
		</div>
	</div>
	<div class="row">
		<div class="large-12 medium-12 small-12">
			<section class="top-bar-section marginTop20">
				<ul>
					<li class="small-12"><a href="../admin/profileTab">Admin Profile</a></li>
					<li class="active small-12"><a href="../admin/usersTab">Admin Users</a></li>
					<!-- <li class="small-12"><a href="../admin/tracksTab">Tracks</a></li> -->
					<li class="small-12"><a href="../admin/productsTab">Products</a></li>
					<li class="small-12"><a href="../admin/customersTab">Customers</a></li>
					<li class="small-12"><a href="../admin/reportsTab">Reports</a></li>
				</ul>
			</section>
		</div>
	</div>
	
	<div class="container">
		<div class="row">
			<div class="large-12 medium-12 small-12 marginTop20">
				<div class="admin-text">
					Admin User Management
				</div>
			</div>
		</div>
		
		<div class="row">
			<div class="large-12 medium-12 small-12 marginTop20 admin-user-div">
				<img src="/data-api/images/user-icon.png">	
				<div class="admin-user">
					Admin User List
				</div>	
				<div class="right marginTop4 removeDisableAdd" data-reveal-id="addAdminModal">
					<input type="button" value="Add Admin" class="admin-button" id="add-admin-button">
				</div>
				<div class="right marginTop4 disableAdd hide">
					<input type="button" value="Add Admin" class="admin-button disableButton" id="add-admin-button">
				</div>			
			</div>
		</div> 
		
		<div class="row marginTop20">
			<div class="user-grid">
				<div class="small-12 large-3 medium-3 columns text paddingLeft30 sort-filed" filter="asc" sort_type="loginId">
					Login Id
					<img src="/data-api/images/down-arrow.png" class="hide down-arrow"><img src="/data-api/images/up-arrow.png" class="up-arrow">	
				</div>
		  		<div class="small-12 large-3 medium-3 columns text sort-filed" filter="asc" sort_type="name">
		  			Name
		  			<img src="/data-api/images/down-arrow.png" class="hide down-arrow"><img src="/data-api/images/up-arrow.png" class="up-arrow">	
		  		</div>
		  		<div class="small-12 large-2 medium-3 columns text sort-filed" filter="asc" sort_type="accessLevel">
		  			Acess Level
		  			<img src="/data-api/images/down-arrow.png" class="hide down-arrow"><img src="/data-api/images/up-arrow.png" class="up-arrow">
		  		</div>
		  		<div class="small-12 large-2 medium-2 columns text sort-filed" filter="asc" sort_type="isActive">
		  			Is Active
		  			<img src="/data-api/images/down-arrow.png" class="hide down-arrow"><img src="/data-api/images/up-arrow.png" class="up-arrow">
		  		</div>
		  		<div class="small-12 large-2 medium-1 columns sort-filed"></div>
			</div>
	  		<div class="add-users-div">
	  			<!--Here Admin users will add from template  -->
	  		</div>
	  		<div id="adminPagSrch">
				<!--Pagination will come here from js  -->
			</div>
		</div>
	</div>
	
	<jsp:include page="../footer.jsp"></jsp:include>
	

	<div id="addAdminModal" class="reveal-modal" data-reveal aria-labelledby="modalTitle" aria-hidden="true" role="dialog">
	 	<div class="modal-text-add">Add Admin</div>
		<div class="marginTop20">
			<input type="text" placeholder="Login Id" class="add-login-id" maxlength="20" />
		</div>
		<span class="error-msz1 hide">Login Id should not contain spaces.</span>
		<div>
			<input type="text" placeholder="User Name" class="add-user-name" maxlength="50" />
		</div>
		<div>
			<input type="password" placeholder="Password" class="add-user-password password" maxlength="60" />
		</div>
		<div>
			<input type="password" placeholder="Confirm Password" class="add-user-cnfrmPwd password" maxlength="60" />
		</div>
		<div>
			<select class="add-access-level">
				<option value="">Select Level</option>
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
			</select>
			<!-- <input type="text" placeholder="Access Level" class="add-access-level text-field" /> -->
		</div>
		<span class="error-msz hide">Access level should be numbers.</span>
		<div class="right">
			<input type="button" value="Add" / class="add-button" id="addUser-add-button">
  			<a class="close-reveal-modal" aria-label="Close"><input type="button" value="Cancel" id="cancelButton" class="cancel-button" /></a>
		</div>
	</div>
	
	<div id="editAdminModal" class="reveal-modal" data-reveal aria-labelledby="modalTitle" aria-hidden="true" role="dialog">
	 	<div class="modal-text-add">Edit Admin</div>
		<div class="marginTop20">
			<span class="modal-text-add">User Name:</span>
			<input type="text" value="" class="modal-user-name width62" placeholder="User Name" maxlength="50"/>
		</div>
		<!-- <div>
			<input type="text" value=""  class="modal-user-email" />
		</div> -->
		<!-- <div>
			<input type="text" value="" class="modal-acess-level add-access-level" />
		</div> -->
		<div>
			<span class="modal-text-add">Access Level:</span>
			<select class="modal-acess-level edit-access-level width62">
				<option>1</option>
				<option>2</option>
				<option>3</option>
				<option>4</option>
				<option>5</option>
			</select>
			<!-- <input type="text" placeholder="Access Level" class="add-access-level text-field" /> -->
		</div>
		<div>
			<span class="modal-text-add">Admin Active:</span>
			<select class="edit-admin-active width62">
				<option value="Yes">Yes</option>
				<option value="No">No</option>
			</select>
			<!-- <input type="text" placeholder="Access Level" class="add-access-level text-field" /> -->
		</div>
		<div class="right">
			<input type="button" value="Save" / class="update-button" id="admin-update-button">
  			<a class="close-reveal-modal" aria-label="Close"><input type="button" value="Cancel" class="cancel-button" /></a>
		</div>
	</div>
	

	<script type="text/javascript" src="/data-api/js/prototype.js"></script>
	<script type="text/javascript" src="/data-api/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="/data-api/js/foundation.min.js"></script>
	<script type="text/javascript" src="/data-api/js/jquery.bootpag.min.js"></script>
	<script type="text/javascript" src="/data-api/js/controller.js?lmts=2016-02-09-15-04-50"></script>
	<script type="text/javascript" src="/data-api/js/common.js?lmts=2016-02-09-15-04-50"></script>
	<script type="text/javascript" src="/data-api/js/admin.js?lmts=2016-02-09-15-04-50"></script>
	<script>
	$(document).foundation();
	</script>
 </BODY>
</HTML>
