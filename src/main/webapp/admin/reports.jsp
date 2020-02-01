<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<HTML>
 <HEAD>
  <TITLE>Reports Tab</TITLE>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link rel="stylesheet" href="/data-api/css/foundation.css" />
  <link rel="stylesheet" href="/data-api/css/foundation-datepicker.css" />
  <link rel="stylesheet" href="/data-api/css/fonts/css/font-awesome.css" />
  <link rel="stylesheet" href="/data-api/css/common.css?lmts=2016-02-03-15-04-50">
  <link rel="stylesheet" href="/data-api/css/report.css?lmts=2016-02-03-15-04-50">
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
					<!-- <li class="small-12"><a href="../admin/tracksTab">Tracks</a></li> -->
					<li class="small-12"><a href="../admin/productsTab">Products</a></li>
					<li class="small-12"><a href="../admin/customersTab">Customers</a></li>
					<li class="active small-12"><a href="../admin/reportsTab">Reports</a></li>
				</ul>
			</section>
		</div>
	</div>
	
	<div class="container">
		<div class="row">
			<div class="large-12 medium-12 small-12 marginTop20">
				<div class="report-text">
					Report Log
				</div>
			</div>
			<div class="large-12 medium-12 small-12 columns">
				<div class="large-11 medium-10 small-12 columns report-srch marginTop20">
					<div class="large-2 medium-3 small-4 columns">
						<input id="startDate" type="text" placeholder="Start Date" />
						 <img id="dp4" src="/data-api/images/full-calender-icon.png" data-date-format="mm/dd/yyyy">
					</div>
					<div class="large-2 medium-3 small-4 columns marginLeft50">
						<input id="endDate" type="text" placeholder="End Date"/>
						<img id="dp5" src="/data-api/images/full-calender-icon.png" data-date-format="mm/dd/yyyy">
					</div>
					<div class="large-4 medium-4 small-4 columns marginLeft50">
						<input type="button" value="Search Log History" class="search-log-btn">
					</div>
				</div>
				<div class="large-1 medium-2 small-12 columns reportBtn-div marginTop20 hide">
					<input type="button" value="Export" class="export-btn">
				</div>
			</div>
			<div class="small-12 large-12 medium-12 columns report-grid marginTop20 hide">
		  		<div class="small-12 large-2 medium-2 columns text">Customer ID</div>
		  		<div class="small-12 large-2 medium-2 columns text">Product ID</div>
		  		<div class="small-12 large-2 medium-2 columns text">End Point</div>
		  		<div class="small-12 large-2 medium-2 columns text">Log Date</div>
		  		<div class="small-12 large-4 medium-4 columns text">Description</div>
			</div>
			<div class="report-all-details small-12 large-12 medium-12 columns">
			</div>
			<div class="marginTop20" id="reportPagSrch">
				<!--Pagination will come here from js  -->
			</div>
		  		
		</div>
	</div>
	
	<jsp:include page="../footer.jsp"></jsp:include>
	
	<script type="text/javascript" src="/data-api/js/prototype.js"></script>
	<script type="text/javascript" src="/data-api/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="/data-api/js/foundation.min.js"></script>
	<script type="text/javascript" src="/data-api/js/foundation-datepicker.js"></script>
	<script type="text/javascript" src="/data-api/js/controller.js?lmts=2016-02-03-15-04-50"></script>
	<script type="text/javascript" src="/data-api/js/common.js?lmts=2016-02-03-15-04-50"></script>
	<script type="text/javascript" src="/data-api/js/report.js?lmts=2016-12-03-15-04-50"></script>
	<script>
	$(document).foundation();
	</script>
 </BODY>
</HTML>
