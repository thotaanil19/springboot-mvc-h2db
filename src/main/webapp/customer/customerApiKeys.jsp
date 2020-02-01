<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<HTML>
 <HEAD>
  <TITLE>Customer API Keys</TITLE>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link rel="stylesheet" href="/data-api/css/foundation.css" />
  <link rel="stylesheet" href="/data-api/css/customerCommon.css?lmts=2016-02-08-15-04-50">
  <link rel="stylesheet" href="/data-api/css/customerApiKey.css?lmts=2016-02-08-15-04-50">
 </HEAD>
 <BODY>
 <jsp:include page="../header.jsp"></jsp:include>
 	<div class="row">
		<div class="large-12 medium-12 small-12 marginTop20">
			<div class="admin-dashboard">
				Customer Dashboard
			</div>
		</div>
	</div>
	<div class="row">
		<div class="large-12 medium-12 small-12">
			<section class="top-bar-section marginTop20">
				<ul>
					<li class="small-12"><a href="../customer/profileTab">My Profile</a></li>
					<li class="small-12"><a href="../customer/productsTab">My Products</a></li>
					<li class="active small-12"><a href="../customer/apiKeysTab">My API Keys</a></li>
					<li class="small-12"><a href="../customer/accessLevelsTab">Reports</a></li>
				</ul>
			</section>
		</div>
	</div>
	<div class="row container">
		<div class="large-12 medium-12 small-12 add-customer-api marginTop20">
			<div class="api-text display-block width450">
				Customer API Keys
			</div>
			<div class="api-text display-block">
				<img src="/data-api/images/add-icon.png" id="api-key-icon">
				<span class="paddingLeft6">Generate New API Key</span>
			</div>
		</div>
		<div class="large-12 medium-12 small-12 marginTop20">
			<div class="api-grid">
				<div class="small-12 large-2 medium-2 columns text"><span class="paddingRight10">Select All</span><input type="checkbox" class="selectall paddingLeft10"></div>
				<div class="small-12 large-5 medium-5 columns text text-center">API Key ID</div>
				<div class="small-12 large-1 medium-1 columns text text-center">Active</div>
				<div class="small-12 large-3 medium-3 columns text text-center">Disabled Time</div>
				<div class="small-12 large-2 medium-2 columns disable"></div>
				
			</div>
			<div class="api-key-details">
			</div>
 		</div>
 		<div class="marginTop20 delete-selected">
 			<input type="button" value="Deactivate API keys" id="deactivate-api-key">
 		</div>
	</div>
	
	<jsp:include page="../footer.jsp"></jsp:include>
	
	<script type="text/javascript" src="/data-api/js/prototype.js"></script>
	<script type="text/javascript" src="/data-api/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="/data-api/js/jquery.slimscroll.js"></script>
	<script type="text/javascript" src="/data-api/js/foundation.min.js"></script>
	<script type="text/javascript" src="/data-api/js/customerController.js?lmts=2016-02-08-15-04-50"></script>
	<script type="text/javascript" src="/data-api/js/common.js?lmts=2016-02-08-15-04-50"></script>
	<script type="text/javascript" src="/data-api/js/customerApiKey.js?lmts=2016-02-08-15-04-50"></script>
	<script>
	$(document).foundation();
	</script>
 </BODY>
</HTML>
