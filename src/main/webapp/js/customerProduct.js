(function($) {
	
	var pageSize=10,noOfPages='',totalNumberOfResults='',startPage=1;
	
	renderAllCustomerProductsTemplate = new Template(['<div class="product-details-grid product_#{id}" product_id=#{id}>',
			                                          	'<div class="small-12 large-3 medium-3 columns text">#{label}</div>',
			                                          	'<div class="small-12 large-2 medium-2 columns text">#{type}</div>',
			                                          	'<div class="small-12 large-2 medium-2 columns text">#{level}</div>',
			                                          	'<div class="small-12 large-2 medium-2 columns text">#{cacheTime}</div>',
			                                          	'<div class="small-12 large-2 medium-2 columns text"><a product_id=#{id} id="view-tracks-link" class="#{hide}" data-reveal-id="viewProductTracksModal">',
			                                          	'View Tracks</a>#{allTracks}</div><div class="large-1 medium-1 columns empty-one"></div></div>'].join(''));
	
	renderAllProductTracksTemplate = new Template(['<div class="row track-details-grid small-12 large-12 medium-12"><div class="small-2 large-2 medium-2 columns text text-center">',
			                                        	'#{id}</div><div class="small-4 large-4 medium-4 columns text text-center">#{country}</div>',
			                                        	'<div class="small-6 large-6 medium-6 columns text text-center track-name">#{name}</div></div>'].join(''));
	
	var id = $('#logout').attr('login_id');
	id = parseInt(id);
	var displayPaginationProducts = function(pageNum, pageSize){
		$.viewAllcustomerProducts(id, pageNum, pageSize,{
			success: function (data) {
				$(".product-all-details").html('');
				if(data != null && data.results != null){
					for(var idx =0; idx<data.results.length; idx++){
						var id = data.results[idx].product.id;
						var obj = data.results[idx];
						noOfPages = data.totalNumberOfPages;
						totalNumberOfResults = data.totalNumberOfResults;
						data.results[idx].label = obj.product.label; 
						data.results[idx].type = obj.product.type;
						data.results[idx].level = obj.product.level; 
						data.results[idx].cacheTime = obj.product.cacheTime; 
						data.results[idx].id = obj.product.id;
						if(data.results[idx].allTracks == true){
							data.results[idx].allTracks = "All Tracks";
							data.results[idx].hide = "hide";
						}else{
							data.results[idx].allTracks = "";
							data.results[idx].hide = "";
						}
						$(".product-all-details").append(renderAllCustomerProductsTemplate.evaluate(obj));
						
					}
				}else{
					$(".product-all-details").html("<div class='no-products text-center marginTop10'>No products for this customer</div>");
				}
			}
		});
	}
	
	var firstPagination = function(){
		$(".product-all-details").append('<img src="/data-api/images/loading.gif" class="loading-icon">');
		$.viewAllcustomerProducts(id, startPage, pageSize,{
			success: function (data) {
				$(".product-all-details").html('');
				if(data != null && data.results != null){
					for(var idx =0; idx<data.results.length; idx++){
						var id = data.results[idx].product.id;
						var obj = data.results[idx];
						noOfPages = data.totalNumberOfPages;
						totalNumberOfResults = data.totalNumberOfResults;
						data.results[idx].label = obj.product.label; 
						data.results[idx].type = obj.product.type;
						data.results[idx].level = obj.product.level; 
						data.results[idx].cacheTime = obj.product.cacheTime; 
						data.results[idx].id = obj.product.id;
						if(data.results[idx].allTracks == true){
							data.results[idx].allTracks = "All Tracks";
							data.results[idx].hide = "hide";
						}else{
							data.results[idx].allTracks = "";
							data.results[idx].hide = "";
						}
						$(".product-all-details").append(renderAllCustomerProductsTemplate.evaluate(obj));
						
					}
				}else{
					$(".product-all-details").html("<div class='no-products text-center marginTop10'>No products for this customer</div>");
				}
				
				if(totalNumberOfResults > pageSize){
					pagination(noOfPages);
				}else{
					$('.pagination').remove();
				}
				
			}
		});
	}
	
	firstPagination();
	pagination = function(noOfPages) {
		//Pagination
		$('#productPagSrch').bootpag({
			total: noOfPages, //number of pages
			page: 1,  //page to show on start
			maxVisible: 5 //maximum number of visible pages
		});
	};
	
	$('#productPagSrch').on("#productPagSrch page", function(event, pageNum){ //To triggering click on pages
		if (pageNum != null && pageNum != undefined) {
			$(".product-all-details").html('');
			displayPaginationProducts(pageNum, pageSize);
		}
	});
	
	$(document).on('click', '#view-tracks-link', function () {
		var productId = $(this).attr('product_id');
		var customerId = $('#logout').attr('login_id');
		$(".render-all-tracks, .productNameForTrack").html('');
		$(".render-all-tracks").append('<img src="/data-api/images/loading.gif" class="loading-icon">');
		$.renderProductTracks(customerId,productId,{
			success: function (data) {
				$(".render-all-tracks").html('');
				$('.productNameForTrack').html(data.product.label);
				if(data != null){
					if(data.allTracks != true && data.productTracks != null){
						for(var idx =0; idx<data.productTracks.length; idx++){
							var obj = data.productTracks[idx];
							data.productTracks[idx].id = obj.track.id; 
							data.productTracks[idx].country = obj.track.country;
							data.productTracks[idx].name = obj.track.name; 
							$(".render-all-tracks").append(renderAllProductTracksTemplate.evaluate(obj));
						}
					}else if(data.allTracks){
						$(".render-all-tracks").html("<div class='all-tracks text-center marginTop10'>All tracks for this product</div>");
					}else{
						$(".render-all-tracks").html("<div class='all-tracks text-center marginTop10'>No tracks for this product</div>");
					}
				}
			}
		});
	});
	
	$('.render-all-tracks').slimScroll({
        railVisible: true,
        alwaysVisible: false,
        size: '5px',
        railColor: '#ffffff',
        height: '200px',
        wheelStep: 2,
        distance: '0px'
	});
	
})(jQuery);