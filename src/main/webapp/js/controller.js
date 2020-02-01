/**
* controller.js
*/
(function($) {
	// api calls
	
	$.extend({
		//Ajax call for logout
		logout: function(callback){
			var params = $.extend({
				type: "GET",
				url: "/data-api/login/logout?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				cache: false
				
			}, callback);
			$.ajax(params);
		},
		
		addAdminUsers: function(criteria, callback){
			var params = $.extend({
				type: "POST",
				data: criteria,
				url: "/data-api/admin/createAdmin?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json",
				contentType: "application/json"
			}, callback);
			$.ajax(params);
		},
		
		renderPaginationUsers: function(criteria, pageNum, pageSize, callback){
			var params = $.extend({
				type: "POST",
				data: criteria,
				url: "/data-api/admin/getAdminUsers/"+pageNum+"/"+pageSize+"?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json",
				contentType: "application/json"
			}, callback);
			$.ajax(params);
		},
		changePassword: function(criteria, callback){
			var params = $.extend({
				type: "POST",
				data: criteria,
				url: "/data-api/admin/changePassword?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json",
				contentType: "application/json"
			}, callback);
			$.ajax(params);
		},
		getAdminDeatils: function(callback){
			var params = $.extend({
				type: "GET",
				url: "/data-api/admin/adminProfile?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json",
			}, callback);
			$.ajax(params);
		},
		editAdminUser: function(criteria, callback){
			var params = $.extend({
				type: "POST",
				data: criteria,
				url: "/data-api/admin/editAdmin?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json",
				contentType: "application/json"
			}, callback);
			$.ajax(params);
		},
		renderAllProducts: function(callback){
			var params = $.extend({
				type: "GET",
				url: "/data-api/product/getAllProducts?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json"
			}, callback);
			$.ajax(params);
		},
		renderSingleProduct: function(id,callback){
			var params = $.extend({
				type: "GET",
				url: "/data-api/product/getProduct/"+id+"?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json"
			}, callback);
			$.ajax(params);
		},
		addProduct: function(criteria, callback){
			var params = $.extend({
				type: "POST",
				data: criteria,
				url: "/data-api/product/addProduct?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json",
				contentType: "application/json"
			}, callback);
			$.ajax(params);
		},
		deleteProduct: function(criteria, callback){
			var params = $.extend({
				type: "POST",
				data: criteria,
				url: "/data-api/product/deleteProducts?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json",
				contentType: "application/json"
			}, callback);
			$.ajax(params);
		},
		editProduct: function(criteria, callback){
			var params = $.extend({
				type: "POST",
				data: criteria,
				url: "/data-api/product/editProduct?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json",
				contentType: "application/json"
			}, callback);
			$.ajax(params);
		},
		renderPaginationProducts: function(criteria, pageNum, pageSize, callback){
			var params = $.extend({
				type: "POST",
				data: criteria,
				url: "/data-api/product/getProducts/"+pageNum+"/"+pageSize+"?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json",
				contentType: "application/json"
			}, callback);
			$.ajax(params);
		},
		renderPaginationTracks: function(pageNum, pageSize,callback){
			var params = $.extend({
				type: "GET",
				url: "/data-api/tracks/getTracks/"+pageNum+"/"+pageSize+"?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json"
			}, callback);
			$.ajax(params);
		},
		renderSortTracks: function(criteria,callback){
			var params = $.extend({
				type: "POST",
				data: criteria,
				url: "/data-api/tracks/getTracks?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json",
				contentType: "application/json"
			}, callback);
			$.ajax(params);
		},
		saveProductTracks: function(criteria,callback){
			var params = $.extend({
				type: "POST",
				data: criteria,
				url: "/data-api/customer/updateCustomerProductTracks?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json",
				contentType: "application/json"
			}, callback);
			$.ajax(params);
		},
		renderSortingTracks: function(sortType, sorting,callback){
			var params = $.extend({
				type: "GET",
				url: "/data-api/tracks/getSortedTracks/"+sortType+"/"+sorting+"?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json"
			}, callback);
			$.ajax(params);
		},
		renderAllTracks: function(callback){
			var params = $.extend({
				type: "GET",
				url: "/data-api/tracks/getAllTracks?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json"
			}, callback);
			$.ajax(params);
		},
		renderAllCustomers: function(callback){
			var params = $.extend({
				type: "GET",
				url: "/data-api/customer/getAllCustomers?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json"
			}, callback);
			$.ajax(params);
		},
		viewAllcustomers: function(id, callback){
			var params = $.extend({
				type: "GET",
				url: "/data-api/customer/"+id+"/getCustomerProducts?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json"
			}, callback);
			$.ajax(params);
		},
		viewProductLink: function(id, callback){
			var params = $.extend({
				type: "GET",
				url: "/data-api/customer/"+id+"/getCustomerProducts?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json"
			}, callback);
			$.ajax(params);
		},
		viewCustomerProductLink: function(id, callback){
			var params = $.extend({
				type: "GET",
				url: "/data-api/customer/"+id+"/getCustomerProducts?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json"
			}, callback);
			$.ajax(params);
		},
		renderPaginationCustomers: function(criteria, pageNum, pageSize, callback){
			var params = $.extend({
				type: "POST",
				data: criteria,
				url: "/data-api/customer/getCustomers/"+pageNum+"/"+pageSize+"?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json",
				contentType: "application/json"
			}, callback);
			$.ajax(params);
		},
		updateCustomerApiLimits: function(criteria, callback){
			var params = $.extend({
				type: "POST",
				data: criteria,
				url: "/data-api/customer/updateCustomerApiLimits?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json",
				contentType: "application/json"
			}, callback);
			$.ajax(params);
		},
		renderAPIKey: function(callback){
			var params = $.extend({
				type: "GET",
				url: "/data-api/customer/generateNewApiKey?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json"
			}, callback);
			$.ajax(params);
		},
		addCustomer: function(criteria,callback){
			var params = $.extend({
				type: "POST",
				data: criteria,
				url: "/data-api/customer/addCustomer?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json",
				contentType: "application/json"
			}, callback);
			$.ajax(params);
		},
		deleteCustomer: function(criteria, callback){
			var params = $.extend({
				type: "POST",
				data: criteria,
				url: "/data-api/customer/deleteCustomers?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json",
				contentType: "application/json"
			}, callback);
			$.ajax(params);
		},

		getCustomerApiKeys: function(id, callback){
			var params = $.extend({
				type: "GET",
				url: "/data-api/customer/"+id+"/getCustomerApiKeys?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json"
			}, callback);
			$.ajax(params);
		},

		getCustomerCurrentMonthApiLimits: function(id, callback){
			var params = $.extend({
				type: "GET",
				url: "/data-api/customer/"+id+"/getCustomerCurrentMonthApiLimits?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json"
			}, callback);
			$.ajax(params);
		},
		editCustomer: function(criteria, callback){
			var params = $.extend({
				type: "POST",
				data: criteria,
				url: "/data-api/customer/editCustomer?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json",
				contentType: "application/json"
			}, callback);
			$.ajax(params);
		},
		changeCustomerPassword: function(criteria, callback){
			var params = $.extend({
				type: "POST",
				data: criteria,
				url: "/data-api/customer/changePassword?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json",
				contentType: "application/json"
			}, callback);
			$.ajax(params);
		},
		renderPaginationReports: function(criteria, callback){
			var params = $.extend({
				type: "POST",
				data: criteria,
				url: "/data-api/reports/getReports?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json",
				contentType: "application/json"
			}, callback);
			$.ajax(params);
		},
	});
	
})(jQuery);


