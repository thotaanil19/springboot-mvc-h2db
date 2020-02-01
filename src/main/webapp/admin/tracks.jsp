<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<HTML>
 <HEAD>
  <TITLE>Tracks Tab</TITLE>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link rel="stylesheet" href="/data-api/css/foundation.css" />
  <link rel="stylesheet" href="/data-api/css/common.css?lmts=2016-02-03-15-04-50">
  <link rel="stylesheet" href="/data-api/css/track.css?lmts=2016-02-03-15-04-50">
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
					<li class="small-12"><a href="../admin/usersTab">Admin Users</a></li>
					<!-- <li class="active small-12"><a href="../admin/tracksTab">Tracks</a></li> -->
					<li class="small-12"><a href="../admin/productsTab">Products</a></li>
					<li class="small-12"><a href="../admin/customersTab">Customers</a></li>
					<li class="small-12"><a href="../admin/reportsTab">Reports</a></li>
				</ul>
			</section>
		</div>
	</div>
	
	<div class="container track-container">
		<div class="track-grid marginTop20">
	  		<div class="small-2 large-2 medium-2 columns text paddingLeft40">ID</div>
	  		<div class="small-2 large-2 medium-2 columns text paddingLeft20" >Country</div>	
	  		<div class="small-2 large-2 medium-2 columns text paddingLeft20">Type</div>
	  		<div class="small-4 large-4 medium-4 columns text paddingLeft20">Name</div>
	  		<div class="small-2 large-2 medium-2 columns"></div>
	  		
		</div>
		<div class="add-tracks-div">
  		</div>
  		
  		<div id="trackPagSrch">
				<!--Pagination will come here from js  -->
		</div>
	</div>
	
	<jsp:include page="../footer.jsp"></jsp:include>
	
	<script type="text/javascript" src="/data-api/js/prototype.js"></script>
	<script type="text/javascript" src="/data-api/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="/data-api/js/foundation.min.js"></script>
	<script type="text/javascript" src="/data-api/js/jquery.bootpag.min.js"></script>
	<script type="text/javascript" src="/data-api/js/controller.js?lmts=2016-02-03-15-04-50"></script>
	<script type="text/javascript" src="/data-api/js/common.js?lmts=2016-02-03-15-04-50"></script>
	<script type="text/javascript" src="/data-api/js/track.js?lmts=2016-02-03-15-04-50"></script>
	<script>
	$(document).foundation();
	</script>
 </BODY>
</HTML>
