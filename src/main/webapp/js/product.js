(function($) {
	
	var productId='',pageSize=10,noOfPages='',totalNumberOfResults='',startPage=1,valid = true;
	
	//Checkbox code in product template. Add this code in below template if needed.
	//<div class="small-12 large-1 medium-1 columns"><input type="checkbox" class="checkbox" product_id=#{id} /></div>
	renderAllProductsTemplate = new Template(['<div class="product-details-grid product_#{id}" product_id=#{id}>',
	                                          	'<div class="small-12 large-3 medium-4 columns text product-label">#{label}</div><div class="small-12 large-2 medium-2 columns text product-type">#{type}</div>',
	                                          	'<div class="small-12 large-2 medium-2 columns text product-level">#{level}</div><div class="small-12 large-4 medium-2 columns text product-cache">#{cacheTime}</div>',
	                                          	'<div class="small-12 large-1 medium-1 columns"><img src="/data-api/images/edit-icon.png" class="removeDisableEdit" data-reveal-id="editProductModal" id="edit-product-icon"></div>',
	                                          	'<img src="/data-api/images/edit-icon.png" class="disableEdit hide"></div>'].join(''));
	
	//Click function to delete selected checkbox records.
	$(document).on('click', '#product-delete', function () {
		var deleted = [],newObj={};
        $.each($('.product-details-grid'), function(){
        	var $this = $(this);
            if($(this).find('input').is(':checked')){
            	deleted.push(parseInt($this.find('input:checked').attr('product_id')));
            };
        });
        if(deleted == ""){
        	alert("Please select atleast one product to delete.")
        }else{
        	newObj = Object.toJSON(deleted);
        	$.deleteProduct(newObj,{
    			success: function (data) {
    				var newObj = {};
    				firstPagination(newObj);
    			}
    		});
            $('.selectall').attr('checked',false);
        }
        
    });
	
	$(document).on('click', '#add-product', function () {
		$('input[type="text"]').val('');
		$('.error-msz').addClass('hide');
	});
	
	$(document).on('click', '#Product-add-button', function () {
		var dataTempObj = {};
		$('.error-msz').remove();
		$('#editProductModal input[type="text"]').val('');
        var  label= $('.modal-product-label').val().trim();
        var  type= $('.modal-product-type').val().trim();
        var  level= $('.modal-product-level').val().trim();
        var  cacheTime= $('.modal-cache-time').val().trim();
        validations();
        if(valid){
        	if(label && type && level && cacheTime){
    			dataTempObj.label = label;
    			dataTempObj.type = type;
    			dataTempObj.level = level;
    			dataTempObj.cacheTime = cacheTime;
    			newObj = Object.toJSON(dataTempObj);
    			$.addProduct(newObj,{
    				success: function (data) {
    					dataTempObj.id = data.id;
    					$('.reveal-modal').foundation('reveal', 'close');
    					$('#addProductModal input[type="text"]').val('');
    					$(".product-all-details").prepend(renderAllProductsTemplate.evaluate(dataTempObj));
    					var newObj = {};
    					firstPagination(newObj);
    					loginRoles();
    				},
    				error: function(data){
    					var error = data.responseJSON.errors;
						if(error != null){
							for(var idx=0; idx<error.length; idx++){
								$('.modal-text-add').append('<div class="error-msz">'+error[idx].detail+'</div>'); 
							}
						}
    				}
    			});
    		}else{
    			$('.modal-text-add').append('<div class="error-msz">All fileds are required.</div>');
    		}
        }else if(label == "" || type == "" || level == ""  || cacheTime == ""){
        	$('.modal-text-add').append('<div class="error-msz">All fileds are required.</div>');
        }
        
    });
	
	$(document).on('click', '#edit-product-icon', function () {
		$('.error-msz').addClass('hide');
		var productLabel = $(this).parent().parent().find('.product-label').text().trim();
		var productLevel = $(this).parent().parent().find('.product-level').text().trim();
		var productType = $(this).parent().parent().find('.product-type').text().trim();
		var cacheTime= $(this).parent().parent().find('.product-cache').text().trim();
		productId = $(this).parent().parent().attr('product_id');
		$('.edit-product-label').val(productLabel);
		$('.edit-product-type').val(productType);
		$('.edit-product-level').val(productLevel);
		$('.edit-product-cache').val(cacheTime);
    });
	
	$(document).on('click', '#edit-product-button', function () {
		var dataTempObj = {};
		$('#addProductModal input[type="text"]').val('');
		id = productId;
		var  label= $('.edit-product-label').val().trim();
        var  type= $('.edit-product-type').val().trim();
        var  level= $('.edit-product-level').val().trim();
        var  cacheTime= $('.edit-product-cache').val().trim();
        validations();
        if(valid){
			if(label && type && level && cacheTime){
				dataTempObj.id = id;
				dataTempObj.label = label;
				dataTempObj.type = type;
				dataTempObj.level = level;
				dataTempObj.cacheTime = cacheTime;
				newObj = Object.toJSON(dataTempObj);
				$.editProduct(newObj,{
					success: function (data) {
						$('#editProductModal').foundation('reveal', 'close');
						$('#editProductModal input[type="text"]').val('');
						$(".product_"+id).html(renderAllProductsTemplate.evaluate(dataTempObj));
					},
    				error: function(data){
    					var error = data.responseJSON.errors;
						if(error != null){
							for(var idx=0; idx<error.length; idx++){
								$('.edit-modal-product').append('<div class="error-msz">'+error[idx].detail+'</div>'); 
							}
						}
    				}
				});
			} else {
    			$('.edit-modal-product').append('<div class="error-msz">All fileds are required.</div>');
    		}
        }else if(label == "" || type == "" || level == ""  || cacheTime == ""){
        	$('.edit-modal-product').append('<div class="error-msz">All fileds are required.</div>');
        }
        loginRoles();
	});
	
	
	
	var firstPagination = function(newObj){
		sortObj = Object.toJSON(newObj);
		$(".product-all-details").append('<img src="/data-api/images/loading.gif" class="loading-icon">');
		$.renderPaginationProducts(sortObj, startPage, pageSize,{
			success: function (data) {
				if(data != null && data.results != null && data.results != undefined){
					$(".product-all-details").html('');
					for(var idx=0; idx<data.results.length; idx++){
						noOfPages = data.totalNumberOfPages;
						totalNumberOfResults = data.totalNumberOfResults;
						$(".product-all-details").append(renderAllProductsTemplate.evaluate(data.results[idx]));
						loginRoles();
					}
				}else{
					$(".product-all-details").html('<div class="text-center product-text marginTop50">No Products</div>');
				}
				if(totalNumberOfResults > pageSize){
					pagination(noOfPages);
				}else{
					$('.pagination').remove();
				}
				
			}
		});
	}
	
	var newObj = {}
	firstPagination(newObj);

	pagination = function(noOfPages) {
		//Pagination
		$('#productPagSrch').bootpag({
			total: noOfPages, //number of pages
			page: 1,  //page to show on start
			maxVisible: 5 //maximum number of visible pages
		})
	};
	$('#productPagSrch').on("page", function(event, pageNum){ //To triggering click on pages
		if (pageNum != null && pageNum != undefined) {
			$(".product-all-details").html('');
			var SortCriteria = {};
			$.each($('.sort-filed'), function(){
				if($(this).attr('clicked') == 'yes'){
					sorting = $(this).attr('filter');
					sortType = $(this).attr('sort_type');
					SortCriteria.sortingField = sortType;
					SortCriteria.sortOrder = sorting;
				}
	        });
			displayPaginationProducts(SortCriteria, pageNum, pageSize);
		}
		
	});
	
	var displayPaginationProducts = function(newObj, pageNum, pageSize){
		sortObj = Object.toJSON(newObj);
		$(".product-all-details").append('<img src="/data-api/images/loading.gif" class="loading-icon">');
		$.renderPaginationProducts(sortObj, pageNum, pageSize,{
			success: function (data) {
				if(data != null && data.results != null && data.results != undefined){
					//$(".product-all-details").html('');
					for(var idx=0; idx<data.results.length; idx++){
						noOfPages = data.totalNumberOfPages;
						totalNumberOfResults = data.totalNumberOfResults;
						$('.loading-icon').remove();
						$(".product-all-details").append(renderAllProductsTemplate.evaluate(data.results[idx]));
						loginRoles();
					}
				}
			}
		});
	};
	
	var validations = function(){
		var addCache = $('.modal-cache-time').val().trim();
		var editCache = $('.edit-product-cache').val().trim();
        var cacheTime = new RegExp('^[0-9]+$');
        if (!cacheTime.test(addCache) && addCache) {
            $('#Product-add-button').css('pointer-events','none');
            $('.modal-cache-time').addClass('redBorder');
            $('.modal-text-add').append('<div class="error-msz">Cache Time should be number.</div>');
            valid = false;
        }else if(!cacheTime.test(editCache) && editCache){
        	$('#edit-product-button').css('pointer-events','none');
        	$('.edit-product-cache').addClass('redBorder');
        	$('.edit-modal-product').append('<div class="error-msz">Cache Time should be number.</div>');
            valid = false;
        }else {
        	$('#Product-add-button').css('pointer-events','');
        	$('#edit-product-button').css('pointer-events','');
        	valid = true;
        }
	}
	
	//Disable Add Admin button based on user login role.
	loginRoles = function(){
		var loginRole = $('#logout').attr('login_role');
		if((loginRole == 'LEVEL_1') && loginRole != null){
			$('.removeDisableAdd').removeClass('hide');
			$('.disableAdd').addClass('hide');
		} else{
			$('.removeDisableAdd').addClass('hide');
			$('.disableAdd').removeClass('hide');
		}
		
		//Disable edit icon
		if((loginRole == 'LEVEL_1' || loginRole == 'LEVEL_2' || loginRole == 'LEVEL_3') && loginRole != null){
			$('.removeDisableEdit').removeClass('hide');
			$('.disableEdit').addClass('hide');
		} else{
			$('.removeDisableEdit').addClass('hide');
			$('.disableEdit').removeClass('hide');
		}
		
	}
	
	loginRoles();

	$(document).on('keyup', '.text-field', function () {
		$('#Product-add-button').css('pointer-events','');
		$('#edit-product-button').css('pointer-events','');
		$('.error-msz').addClass('hide');
		$('.text-field').removeClass('redBorder');
		valid = true;
    });
	
	$(document).on('click', '.sort-filed', function () {
		var newObj = {};
		$('.sort-filed').attr('clicked','no')
		$(this).attr('clicked','yes');
		var sorting = $(this).attr('filter');
		var sortType = $(this).attr('sort_type');
		if(sorting == "desc"){
			sorting = "asc";
			$('.sort-filed').attr('filter','asc')
			$(this).attr('filter','asc')
			$('.sort-filed').find('img.down-arrow').addClass('hide');
			$('.sort-filed').find('img.up-arrow').removeClass('hide');
			$(this).find('img.down-arrow').addClass('hide');
			$(this).find('img.up-arrow').removeClass('hide');
		}else{
			sorting = "desc";
			$('.sort-filed').attr('filter','asc')
			$(this).attr('filter','desc')
			$('.sort-filed').find('img.down-arrow').addClass('hide');
			$('.sort-filed').find('img.up-arrow').removeClass('hide');
			$(this).find('img.down-arrow').removeClass('hide');
			$(this).find('img.up-arrow').addClass('hide');
		}
		$(".product-all-details").html('');
		newObj.sortingField = sortType;
		newObj.sortOrder = sorting;
		firstPagination(newObj);
	});
	
	
	$(document).on('mouseenter', ".product-label", function () {
	     var $this = $(this);
	     if (this.offsetWidth < this.scrollWidth && !$this.attr('title')) {
	         $this.tooltip({
	             title: $this.text(),
	             placement: "bottom"
	         });
	         $this.tooltip('show');
	     }
	 });
	
})(jQuery);