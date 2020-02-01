<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<HTML>
 <HEAD>
  <TITLE>Customer Products</TITLE>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link rel="stylesheet" href="/data-api/css/foundation.css" />
  <link rel="stylesheet" href="/data-api/css/customerCommon.css?lmts=2016-12-08-15-04-50">
  <link rel="stylesheet" href="/data-api/css/customerProduct.css?lmts=2016-02-08-15-04-50">
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
					<li class="active small-12"><a href="../customer/productsTab">My Products</a></li>
					<li class="small-12"><a href="../customer/apiKeysTab">My API Keys</a></li>
					<li class="small-12"><a href="../customer/accessLevelsTab">Reports</a></li>
				</ul>
			</section>
		</div>
	</div>
	
	<div class="container">
		<div class="row marginTop20">
			<div class="large-12 medium-12 small-12 columns customer-profile-text customer-details">
					Customer Profile Details
			</div>
		</div> 
		
		<div class="row marginTop20">
			<div class="product-grid">
		  		<div class="small-12 large-3 medium-3 columns text ">Product Label</div>
		  		<div class="small-12 large-2 medium-2 columns text">Type</div>
		  		<div class="small-12 large-2 medium-2 columns text">Level</div>
		  		<div class="small-12 large-2 medium-2 columns text">Cache Time</div>
		  		<div class="small-12 large-2 medium-2 columns text">Tracks</div>
		  		<div class="large-1 medium-1 columns empty-one"></div>
			</div>
			<div class="product-all-details">
	  		</div>
			<div id="productPagSrch marginTop10">
				<!--Pagination will come here from js  -->
			</div>
	  		
		</div>
	</div>
	
	<jsp:include page="../footer.jsp"></jsp:include>
	
	<div id="viewProductTracksModal" class="reveal-modal" data-reveal aria-labelledby="modalTitle" aria-hidden="true" role="dialog" product_id="">
	 	<div class="add-customer-text">Tracks for <span class="productNameForTrack"></span></div>
	 	<a class="close-reveal-modal right" aria-label="Close"><img src="/data-api/images/close-icon.png"></a>
		<div class="all-track-details">
			<div class="row track-grid marginTop10 small-12 large-12 medium-12">
				<div class="small-2 large-2 medium-2 columns text ">Track ID</div>
				<div class="small-4 large-4 medium-4 columns text">Country</div>
				<div class="small-6 large-6 medium-6 columns text">Name</div>
			</div>
			<div class="render-all-tracks">
			</div>
		</div>
	</div>
	
	<script type="text/javascript" src="/data-api/js/prototype.js"></script>
	<script type="text/javascript" src="/data-api/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="/data-api/js/foundation.min.js"></script>
	<script type="text/javascript" src="/data-api/js/jquery.bootpag.min.js"></script>
	<script type="text/javascript" src="/data-api/js/jquery.slimscroll.js"></script>
	<script type="text/javascript" src="/data-api/js/customerController.js?lmts=2016-02-08-15-04-50"></script>
	<script type="text/javascript" src="/data-api/js/common.js?lmts=2016-02-08-15-04-50"></script>
	<script type="text/javascript" src="/data-api/js/customerProduct.js?lmts=2016-02-11-15-04-50"></script>
	<script>
	$(document).foundation();
	</script>
 </BODY>
</HTML>
