<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<HTML>
 <HEAD>
  <TITLE>Product Tab</TITLE>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link rel="stylesheet" href="/data-api/css/foundation.css" />
  <link rel="stylesheet" href="/data-api/css/common.css?lmts=2016-02-11-15-04-50">
  <link rel="stylesheet" href="/data-api/css/product.css?lmts=2016-02-11-15-04-50">
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
					<li class="active small-12"><a href="../admin/productsTab">Products</a></li>
					<li class="small-12"><a href="../admin/customersTab">Customers</a></li>
					<li class="small-12"><a href="../admin/reportsTab">Reports</a></li>
				</ul>
			</section>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="large-12 medium-12 small-12 marginTop20">
				<div class="product-text">
					Product Management
				</div>
			</div>
		</div>
		
		<div class="row">
			<div class="large-12 medium-12 small-12 marginTop20 product-list-div">
				<img src="/data-api/images/list-icon.png">	
				<div class="product-list">
					Product List
				</div>	
			</div>
			<div class="large-12 medium-12 small-12 marginTop10 select-all-div">
				<!-- <div class="select-all">
					<input type="checkbox" class="selectall"/>
					Select All
				</div> -->	
				<div class="right marginTop4 removeDisableAdd" data-reveal-id="addProductModal">
					<input type="button" value="Add Product" class="product-button" id="add-product">
				</div>
				<div class="right marginTop4 disableAdd hide">
					<input type="button" value="Add Product" class="product-button disableButton" id="add-product">
				</div>	
			</div>
		</div> 
		
		<div class="row marginTop20">
			<div class="product-grid">
				<!-- <div class="small-12 large-1 medium-1 columns text">Select</div> -->
		  		<div class="small-12 large-3 medium-4 columns text text-center sort-filed" filter="asc" sort_type="label">
		  			Product Label
		  			<img src="/data-api/images/down-arrow.png" class="hide down-arrow"><img src="/data-api/images/up-arrow.png" class="up-arrow">	
		  		</div>
		  		<div class="small-12 large-2 medium-2 columns text text-center sort-filed" filter="asc" sort_type="type">
		  			Type
					<img src="/data-api/images/down-arrow.png" class="hide down-arrow"><img src="/data-api/images/up-arrow.png" class="up-arrow">
				</div>
		  		<div class="small-12 large-2 medium-2 columns text text-center sort-filed" filter="asc" sort_type="level">
		  			Level
		  			<img src="/data-api/images/down-arrow.png" class="hide down-arrow"><img src="/data-api/images/up-arrow.png" class="up-arrow">	
		  		</div>
		  		<div class="small-12 large-5 medium-4 columns text left sort-filed" filter="asc" sort_type="cacheTime">
		  			Time (in minutes) that data is cached
		  			<img src="/data-api/images/down-arrow.png" class="hide down-arrow"><img src="/data-api/images/up-arrow.png" class="up-arrow">	
		  		</div>
		  		<!-- <div class="small-12 large-1 medium-1 columns text"></div> -->
			</div>
			<div class="product-all-details">
	  		</div>
	  		
  			<!-- <div class="delete-selected small-12 large-6 medium-6 columns">
  				<input type="button" value="Delete Selected" id="product-delete"/>
  			</div> -->
			<div id="productPagSrch">
				<!--Pagination will come here from js  -->
			</div>
			
	  		
		</div>
		
	</div>
	
	<jsp:include page="../footer.jsp"></jsp:include>
	
	<div id="addProductModal" class="reveal-modal" data-reveal aria-labelledby="modalTitle" aria-hidden="true" role="dialog">
	 	<div class="modal-text-add">Add Product</div>
		<div class="marginTop20"> 
			<input type="text" placeholder="Product Label" value="" class="modal-product-label text-field" maxlength="50"/>
		</div>
		<div>
			<input type="text" placeholder="Product Type" value="" class="modal-product-type text-field" maxlength="40" />
		</div>
		<div> 
			<input type="text" placeholder="Access Level" value="" class="modal-product-level text-field" maxlength="40" />
		</div>
		<div>
			<input type="text" placeholder="Cache Time(Minutes)" value="" class="modal-cache-time text-field" />
		</div>
		<div class="right">
			<input type="button" value="Add" / class="add-button" id="Product-add-button">
  			<a class="close-reveal-modal" aria-label="Close"><input type="button" value="Cancel" id="cancelButton" class="cancel-button" /></a>
		</div>
	</div>
	
	<div id="editProductModal" class="reveal-modal" data-reveal aria-labelledby="modalTitle" aria-hidden="true" role="dialog">
	 	<div class="modal-text-add edit-modal-product">Edit Product</div>
	 	
		<div class="marginTop20">
			<span class="modal-text-add">Product Label:</span>
			<input type="text" value="" class="edit-product-label text-field width62" placeholder="Product Label" maxlength="50" />
		</div>
		<div>
			<span class="modal-text-add">Product Type:</span>
			<input type="text" value="" class="edit-product-type text-field width62" placeholder="Product Type" maxlength="40" />
		</div>
		<div>
			<span class="modal-text-add">Access Level:</span>
			<input type="text" value="" class="edit-product-level text-field width62" placeholder="Access Level" maxlength="40" />
		</div>
		<div>
			<span class="modal-text-add">Cache Time(Minutes):</span>
			<input type="text" value="" class="edit-product-cache text-field width62" placeholder="Cache Time(Minutes)" />
		</div>
		<div class="right">
			<input type="button" value="Save" / class="update-button" id="edit-product-button">
  			<a class="close-reveal-modal" aria-label="Close"><input type="button" value="Cancel" class="cancel-button" /></a>
		</div>
	</div>
	
	<script type="text/javascript" src="/data-api/js/prototype.js"></script>
	<script type="text/javascript" src="/data-api/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="/data-api/js/foundation.min.js"></script>
	<script type="text/javascript" src="/data-api/js/jquery.bootpag.min.js"></script>
	<script type="text/javascript" src="/data-api/js/controller.js?lmts=2016-02-09-15-04-50"></script>
	<script type="text/javascript" src="/data-api/js/common.js?lmts=2016-02-09-15-04-50"></script>
	<script type="text/javascript" src="/data-api/js/product.js?lmts=2016-02-17-15-04-50"></script>
	<script>
	$(document).foundation();
	</script>
 </BODY>
</HTML>
