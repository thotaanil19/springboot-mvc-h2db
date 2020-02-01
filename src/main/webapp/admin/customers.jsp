<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<HTML>
 <HEAD>
  <TITLE>Customer Tab</TITLE>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link rel="stylesheet" href="/data-api/css/foundation.css" />
  <link rel="stylesheet" href="/data-api/css/foundation-datepicker.css" />
  <link rel="stylesheet" href="/data-api/css/fonts/css/font-awesome.css" />
   <link rel="stylesheet" href="/data-api/css/common.css?lmts=2016-02-11-15-04-50"> 
  <link rel="stylesheet" href="/data-api/css/customer.css?lmts=2016-04-06-15-04-50">
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
					<li class="active small-12"><a href="../admin/customersTab">Customers</a></li>
					<li class="small-12"><a href="../admin/reportsTab">Reports</a></li>
				</ul>
			</section>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="large-12 medium-12 small-12 marginTop20">
				<div class="customer-text">
					Customer Management
				</div>
			</div>
		</div>
		
		<div class="add-customer hide">
			<jsp:include page="addCustomer.jsp"></jsp:include>
		</div>
		<div class="customer-details ">
			<div class="row">
				<div class="large-12 medium-12 small-12 marginTop20 customer-list-div">
					<img src="/data-api/images/list-icon.png">	
					<div class="customer-list">
						Customer List
					</div>	
				</div>
				<div class="large-12 medium-12 small-12 marginTop10 select-all-div">
					<div class="right marginTop4 removeDisableAdd">
						<input type="button" value="Add Customer" class="customer-button" id="add-customer">
					</div>
					<div class="right marginTop4 disableAdd hide">
						<input type="button" value="Add Customer" class="customer-button disableButton">
					</div>			
					
				</div>
			</div> 
			<div class="row marginTop20">
				<div class="customer-grid">
					<!-- <div class="small-12 large-1 medium-1 columns text">Select</div> -->
			  		<div class="small-12 large-1 medium-1 columns text width10 sort-filed"  filter="asc" sort_type="id">
			  			Customer Id
			  			<img src="/data-api/images/down-arrow.png" class="hide down-arrow"><img src="/data-api/images/up-arrow.png" class="up-arrow">
			  		</div>
			  		<div class="small-12 large-2 medium-2 columns text sort-filed" filter="asc" sort_type="companyName">
			  			Company Name
			  			<img src="/data-api/images/down-arrow.png" class="hide down-arrow"><img src="/data-api/images/up-arrow.png" class="up-arrow">
			  		</div>
			  		<div class="small-12 large-2 medium-2 columns text sort-filed" filter="asc" sort_type="email">
			  			Email
			  			<img src="/data-api/images/down-arrow.png" class="hide down-arrow"><img src="/data-api/images/up-arrow.png" class="up-arrow">
			  		</div>
			  		<div class="small-12 large-1 medium-1 columns text sort-filed" filter="asc" sort_type="isActive">
			  			Is Active
			  			<img src="/data-api/images/down-arrow.png" class="hide down-arrow"><img src="/data-api/images/up-arrow.png" class="up-arrow">
			  		</div>
			  		<div class="small-12 large-1 medium-1 columns text sort-filed width9" filter="asc" sort_type="baseAccessLimit">
			  			Access Limit
			  			<img src="/data-api/images/down-arrow.png" class="hide down-arrow"><img src="/data-api/images/up-arrow.png" class="up-arrow">
			  		</div>
			  		<div class="small-12 large-2 medium-2 columns text sort-filed" filter="asc" sort_type="resetDayOfMonth">
			  			reset day of month
			  			<img src="/data-api/images/down-arrow.png" class="hide down-arrow"><img src="/data-api/images/up-arrow.png" class="up-arrow">
			  		</div>
			  		<div class="small-12 large-1 medium-1 columns text ">Products</div>
			  		<div class="small-12 large-1 medium-1 columns text">API Keys</div>
			  		<div class="small-12 large-1 medium-1 columns text width7"></div>
				</div>
				<div id="customer-all-details">
					<!-- All customer details here -->
		  		</div>
		  		<!-- <div class="marginTop10">
		  			<div class="delete-selected small-12 large-6 medium-6 columns hide">
		  				<input type="button" value="Delete Selected" id="customer-delete"/>
		  			</div> -->
	  			<div class="marginTop20" id="customerPagSrch">
				<!--Pagination will come here from js  -->
				</div>
		  		<!-- </div> -->
		  		
			</div>
		</div>
		
	</div>
	
	<jsp:include page="../footer.jsp"></jsp:include>
	
	<div id="viewProductsModal" class="reveal-modal" data-reveal aria-labelledby="modalTitle" aria-hidden="true" role="dialog">
	 	<div class="add-customer-text">Products for <span class="productCompanyName"></span></div>
	 	<a class="close-reveal-modal" aria-label="Close"><img src="/data-api/images/close-icon.png"></a>
		<div class="all-track-details marginTop20">
			<div class="row view-product-grid  small-12 large-12 medium-12">
				<div class="small-3 large-3 medium-3 columns text paddingLeft20">Product Label</div>
				<div class="small-2 large-2 medium-2 columns text paddingLeft55">Type</div>
				<div class="small-2 large-2 medium-2 columns text text-center">Level</div>
				<div class="small-2 large-2 medium-2 columns text text-center">Is Active</div>
				<div class="small-3 large-3 medium-3 columns text text-center">Tracks</div>
			</div>
			<div id="view-all-products">
			
			</div>
		</div>
	</div>
	<div id="addTracksModal" class="reveal-modal" data-reveal aria-labelledby="modalTitle" aria-hidden="true" role="dialog" product_id="">
	 	<div class="add-customer-text">Add/Remove Tracks</div>
		<div class="">
			<input type="checkbox"  class="selectall" clicked="no" />
			<span class="paddingLeft6 ">Select All</span>
		</div>
		<div class="all-track-details">
			<div class="row track-grid marginTop10 small-12 large-12 medium-12">
				<div class="small-1 large-1 medium-1 columns text">Select</div>
				<div class="small-1 large-1 medium-1 columns text">ID
					<!-- <img src="/data-api/images/down-arrow.png" class="down-arrow"><img src="/data-api/images/up-arrow.png" class="hide up-arrow"> -->
				</div>
				<div class="small-3 large-3 medium-3 columns text">Country
					<!-- <img src="/data-api/images/down-arrow.png" class="down-arrow"><img src="/data-api/images/up-arrow.png" class="hide up-arrow"> -->
				</div>
				<div class="small-2 large-2 medium-2 columns text">Type
					<!-- <img src="/data-api/images/down-arrow.png" class="down-arrow"><img src="/data-api/images/up-arrow.png" class="hide up-arrow"> -->
				</div>
				<div class="small-5 large-5 medium-5 columns text">Name
					<!-- <img src="/data-api/images/down-arrow.png" class="down-arrow"><img src="/data-api/images/up-arrow.png" class="hide up-arrow"> -->
				</div>
			</div>
			<div class="render-all-tracks">
			</div>
		</div>
		<div class="hint-text marginTop20 left"> Saves customer Product and Tracks</div>
		<div class="right">
			<input type="button" value="Save" class="add-button" id="add-tracks-button" />
  			<a class="close-reveal-modal" aria-label="Close"><input type="button" value="Cancel" id="cancel-tracks-button" class="cancel-button" /></a>
		</div>
	</div>
	<div id="trackConfirmModal" class="reveal-modal" data-reveal aria-labelledby="modalTitle" aria-hidden="true" role="dialog">
		<div class="track-confirm-text text-center">
			This will remove all Tracks from this product.</br>
			You will need to manually add tracks in order for the customer to access data.</br>
			Are you sure you want to do this?
		</div>
		<div class="text-center">
			<input type="button" value="Yes" class="add-button" id="track-cnfrm-btn"/>
  			<a class="close-reveal-modal" aria-label="Close"><input type="button" value="No" class="cancel-button" id="track-no-btn"/></a>
		</div>
	</div>
	
	<div id="cancelConfirmModal" class="reveal-modal" data-reveal aria-labelledby="modalTitle" aria-hidden="true" role="dialog">
		<div class="cancel-confirm-text text-center">
			Changes will not be saved unless you click "Save"</br>
			Are you sure you want to discard your changes?
		</div>
		<div class="text-center editCustomerSection">
			<input type="button" value="Save" class="add-button" id="cancel-cnfrm-btn"/>
  			<a class="close-reveal-modal" aria-label="Close"><input type="button" value="Cancel" class="cancel-button" id="cancel-no-btn"/></a>
		</div>
		<div class="text-center addCustomerSection hide">
			<input type="button" value="YES" class="add-button" id="addCustomer-cancel-btn"/>
  			<a class="close-reveal-modal" aria-label="Close"><input type="button" value="No" class="cancel-button"/></a>
		</div>
	</div>
	
	<div id="apiConfirmModal" class="reveal-modal" data-reveal aria-labelledby="modalTitle" aria-hidden="true" role="dialog">
		<div class="cancel-confirm-text text-center">
			Changes will not be saved unless you click "Save"</br>
			Are you sure you want to discard your changes?
		</div>
		<div class="text-center">
			<input type="button" value="Save" class="add-button" id="cancel-cnfrm-btn"/>
  			<a class="close-reveal-modal" aria-label="Close"><input type="button" value="Cancel" class="cancel-button" id="cancel-no-btn"/></a>
		</div>
	</div>
	
	<script type="text/javascript" src="/data-api/js/prototype.js"></script>
	<script type="text/javascript" src="/data-api/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="/data-api/js/foundation.min.js"></script>
	<script type="text/javascript" src="/data-api/js/foundation-datepicker.js"></script>
	<script type="text/javascript" src="/data-api/js/jquery.bootpag.min.js"></script>
	<script type="text/javascript" src="/data-api/js/bootstrap-tooltip.js"></script>
	<script type="text/javascript" src="/data-api/js/jquery.slimscroll.js"></script>
	<script type="text/javascript" src="/data-api/js/controller.js?lmts=2016-02-09-15-04-50"></script>
	<script type="text/javascript" src="/data-api/js/common.js?lmts=2016-02-10-15-04-50"></script>
	<script type="text/javascript" src="/data-api/js/customer.js?lmts=2016-04-27-15-04-50"></script>
	<script>
	$(document).foundation();
	</script>
 </BODY>
</HTML>
