/**
* Custometr controller.js
*/
(function($) {
	// api calls
	
	$.extend({
		
		logout: function(callback){
			var params = $.extend({
				type: "GET",
				url: "/data-api/login/logout?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				cache: false
				
			}, callback);
			$.ajax(params);
		},
		
		renderCustomerProfile: function(id, callback){
			var params = $.extend({
				type: "GET",
				url: "/data-api/customer/"+id+"/customerProfile?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json"
			}, callback);
			$.ajax(params);
		},
		
		viewAllcustomerProducts: function(id, pageNum, pageSize, callback){
			var params = $.extend({
				type: "GET",
				url: "/data-api/customer/"+id+"/getCustomerProducts/"+pageNum+"/"+pageSize+"?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json"
			}, callback);
			$.ajax(params);
		},
			
		renderProductTracks: function(customerId, productId, callback){
			var params = $.extend({
				type: "GET",
				url: "/data-api/customer/"+customerId+"/"+productId+"/getCustomerProductTracks?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json"
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
		
		renderAPIKey: function(callback){
			var params = $.extend({
				type: "GET",
				url: "/data-api/customer/generateNewApiKey?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json"
			}, callback);
			$.ajax(params);
		},
		saveAPIKey: function(criteria, callback){
			var params = $.extend({
				type: "POST",
				data: criteria,
				url: "/data-api/customer/saveApiKey?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json",
				contentType: "application/json"
			}, callback);
			$.ajax(params);
		},
		deleteCustomerApiKeys: function(id, criteria, callback){
			var params = $.extend({
				type: "POST",
				data: criteria,
				url: "/data-api/customer/"+id+"/deleteCustomerApiKeys?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
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
		
		getCustomerApiLimits: function(id,startDateStr,endDateStr, callback){
			var params = $.extend({
				type: "GET",
				url: "/data-api/customer/"+id+"/getCustomerApiLimits/"+startDateStr+"/"+endDateStr+"?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json"
			}, callback);
			$.ajax(params);
		},
		
		getCustomerAllTimeApiLimits: function(id, callback){
			var params = $.extend({
				type: "GET",
				url: "/data-api/customer/"+id+"/getCustomerAllTimeApiLimits?random=" + Math.floor(Math.random() * (new Date()).getTime() + 1),
				dataType: "json"
			}, callback);
			$.ajax(params);
		},
	});
})(jQuery);


