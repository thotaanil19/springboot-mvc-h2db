<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<HTML>
 <HEAD>
  <TITLE>Customer Reports</TITLE>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link rel="stylesheet" href="/data-api/css/foundation.css" />
  <link rel="stylesheet" href="/data-api/css/foundation-datepicker.css" />
  <link rel="stylesheet" href="/data-api/css/fonts/css/font-awesome.css" />
  <link rel="stylesheet" href="/data-api/css/customerCommon.css?lmts=2016-12-08-15-04-50">
  <link rel="stylesheet" href="/data-api/css/customerAccessLevel.css?lmts=2016-02-08-15-04-50">
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
					<li class="small-12"><a href="../customer/apiKeysTab">My API Keys</a></li>
					<li class="active small-12"><a href="../customer/accessLevelsTab">Reports</a></li>
				</ul>
			</section>
		</div>
	</div>
	
	<div class="container marginTop50">
		<div class="row">
			<div class="large-12 medium-12 small-12 columns">
				<div class="large-2 medium-2 small-12 columns">
					<input type="radio" checked class="api-radio get-current-month-limits">
					<span class="normal-text">Current Month</span>
				</div>
				<div class="large-8 medium-8 small-12 columns date-icons-div">
					<div class="large-1 medium-1 small-1 columns">
						<input type="radio" class="date-checker-radio api-radio">
					</div>
					<div class="large-4 medium-4 small-5 columns">
						<input id="startDate" type="text" placeholder="Start Date" readonly class="large-10 medium-10 small-10 columns"/>
						<img id="dp4" src="/data-api/images/full-calender-icon.png" data-date-format="mm-dd-yyyy" class="large-1 medium-1 small-1">
					</div>
					<div class="large-1 medium-1 small-1 columns normal-text">to</div>
					<div class="large-4 medium-4 small-5 columns">
						<input id="endDate" type="text" placeholder="End Date"/ readonly class="large-10 medium-10 small-10 columns">
						<img id="dp5"  src="/data-api/images/full-calender-icon.png" data-date-format="mm-dd-yyyy" class="large-1 medium-1 small-1">
					</div>
					<div class="large-2 medium-2 small-2 columns">
						<input type="button" value="Get Limits" class="get-limits">
					</div>
					
				</div>
				<div class="large-2 medium-2 small-12 columns">
					<input type="radio" class="api-radio get-allTime-limits">
					<span class="normal-text">All Time</span>
				</div>
			</div>
			<div class="marginTop100">
				<div class="large-12 medium-12 small-12 columns ">
					<div class="marginLeft10 api-hits"><div class="api-hits-text">Total API Hits</div><span class="totalHits"></span></div>
					<div class="marginLeft10 api-hits"><div class="api-hits-text">Used API Hits</div><span class="usedHits"></span></div>
					<div class="marginLeft10 api-hits"><div class="api-hits-text">Remaining API Hits</div><span class="remainingHits"></span></div>
				</div>
			</div>
			
		</div> 
	</div>
	
	<jsp:include page="../footer.jsp"></jsp:include>
	
	<script type="text/javascript" src="/data-api/js/prototype.js"></script>
	<script type="text/javascript" src="/data-api/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="/data-api/js/foundation.min.js"></script>
	<script type="text/javascript" src="/data-api/js/foundation-datepicker.js"></script>
	<script type="text/javascript" src="/data-api/js/customerController.js?lmts=2016-02-08-15-04-50"></script>
	<script type="text/javascript" src="/data-api/js/common.js?lmts=2016-02-08-15-04-50"></script>
	<script type="text/javascript" src="/data-api/js/customerAccessLevel.js?lmts=2016-02-08-15-04-50"></script>
	<script>
	$(document).foundation();
	</script>
 </BODY>
</HTML>
