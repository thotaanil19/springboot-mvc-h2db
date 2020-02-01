(function($) {

	var pageSize=10,noOfPages='',totalNumberOfResults='',newObj={}, startPage=1,findProduct='',accessLimitval = '',productsSelected=[],selected = [],checkValidations = false, dateReset = false;
	var cancelSaveClicked = false, apiCancelClick = false;
	renderAllCustomersTemplate = new Template(['<div class="customer-details-grid" customer_id="#{id}" login_id=#{loginId}>',
	                                           		'<div class="small-12 large-1 medium-1 columns text ">#{id}</div><div class="small-12 large-2 medium-2 columns text edit-company">#{companyName}</div><div class="small-12 large-2 medium-2 columns text edit-email">#{email}</div>',
	                                           		'<div class="small-12 large-1 medium-1 columns text paddingLeft20">#{isActive}</div><div class="small-12 large-1 medium-1 columns text edit-access paddingLeft20">#{baseAccessLimit}</div>',
	                                           		'<div class="small-12 large-2 medium-2 columns text edit-reset-month">#{resetDayOfMonth}</div><div class="small-12 large-1 medium-1 columns text width11">',
	                                           		'<a product_id=#{id} id="view-product-link" data-reveal-id="viewProductsModal" class="paddingLeft40">Products</a></div>',
	                                           		'<div class="small-12 large-1 medium-1 columns text"><select class="all-api-keys"><option>API Keys</option>#{customerApiKeys}</select></div>',
  													'<div class="small-12 large-1 medium-1 columns width5"><img class="customer-edit-icon removeDisableEdit" customer_id="#{id}" src="/data-api/images/edit-icon.png"><img src="/data-api/images/edit-icon.png" class="disableEdit hide"></div>',
  												'</div>'].join(''));

	renderAllProductsTemplate = new Template(['<div class="row view-product-details-grid small-12 large-12 medium-12"><div class="small-3 large-3 medium-3 columns text">#{product.label}</div>',
	                                          	'<div class="small-2 large-2 medium-2 columns text ">#{product.type}</div><div class="small-2 large-2 medium-2 columns text">#{product.level}</div>',
	                                          	'<div class="small-2 large-2 medium-2 columns text">#{isActive}</div><div class="small-3 large-3 medium-3 columns text trackList">#{allTracks}</div>',
	                                          	'</div>'].join(''));

	renderListProductsTemplate = new Template(['<option product_id=#{id} value="#{label}">#{label}</option>'].join(''));

	renderProductDetailsTemplate = new Template(['<div id="single-product" class="product-each" each_product_id = #{productId} product_id=#{id}><div class="product-grid marginTop30"><div class="small-12 large-2 medium-3 columns text">Product Label</div>',
	                                             	'<div class="small-12 large-2 medium-3 columns text ">Type</div><div class="small-12 large-2 medium-3 columns text">Level</div>',
	                                             	'<div class="small-12 large-2 medium-2 columns text">Days Allowed</div>',
	                                             	'<div class="small-12 large-2 medium-3 columns text">Is Active</div></div>',
	                                             	'<div class="product-details-grid marginBot20"><div class="small-12 large-2 medium-3 columns text text-center product-label-text">#{label}</div>',
	                                             	'<div class="small-12 large-2 medium-3 columns text text-center">#{type}</div><div class="small-12 large-2 medium-3 columns text text-center">#{level}</div>',
	                                             	'<div class="small-12 large-2 medium-2 columns text text-center daysBack">#{daysBack}</div>',
	                                             	'<div class="small-12 large-2 medium-3 columns text text-center product-active"><span class="dropdown-active">#{isActive}</span>#{edit}</div></div><img class="loading-track-icon loading-hide" src="/data-api/images/loading.gif">',
	                                             	'<div class="product-allTracks #{hide}"><input type="checkbox" checked class="allTracks"><span class="paddingLeft6">All Tracks</span>',
	                                             	'<div class="right hide" id="addTracks"><img src="/data-api/images/add-icon.png" data-reveal-id="addTracksModal" id="add-track-icon" product_id=#{id}><span class="paddingLeft6">Add/Remove Tracks</span></div>',
	                                             	'</div><div class="small-12 large-12 medium-12 track-text hide">Selected Tracks:</div><div class="selected-tracks hide small-12 large-12 medium-12 columns"><div class="small-12 large-2 medium-2 columns">ID</div>',
	                                             	'<div class="small-12 large-2 medium-2 columns">Country</div><div class="small-12 large-1 medium-1 columns">Type</div><div class="small-12 large-7 medium-7 columns">Name</div></div>',
	                                             	'<div class="selected-tracks-list small-12 large-12 medium-12 hide">',
	                                             	'</div></div>'].join(''));

	renderApiKeyTemplate = new Template(['<div class="api-details-grid"><div class="small-12 large-1 medium-1 columns text-center"><input type="radio" #{checked} class="marginTop10 api-radio"/></div>',
	                                     	'<div class="small-12 large-6 medium-6 columns text api-text">#{apiKey}</div><div class="small-12 large-2 medium-2 columns text text-center">#{isActive}</div>',
	                                     	'<div class="small-12 large-3 medium-3 columns text text-center">#{disabledAtStr}</div>',
	                                     	'</div>'].join(''));

	renderAllTracksTemplate = new Template(['<div class="track-details-grid small-12 large-12 medium-12"><div class="small-1 large-1 medium-1 columns  text-center"><input type="checkbox" #{checked} class="checkbox track-checkbox" track_id=#{id} track_type=#{type} track_country=#{country} track_name=#{name}></div>',
	                                        	'<div class="small-1 large-1 medium-1 columns text text-center">#{id}</div><div class="small-3 large-3 medium-3 columns text text-center">#{country}</div>',
	                                        	'<div class="small-2 large-2 medium-2 columns text text-center">#{type}</div><div class="small-5 large-5 medium-5 columns text text-center track-name">#{name}</div></div>'].join(''));

	$(document).on('click', '#add-customer', function () {
		$('.add-customer').removeClass('hide');
		$('.customer-details').addClass('hide');
		$('#addCustomer').html('Add customer');
		$('.customer-password').parent().removeClass('hide');
		$('.customer-password').removeClass('redBorder');
		$('.customer-fields span').css('display','none');
		$('.customer-api-limit, .customer-active').addClass('hide');
		$('.customer-access-limit').parent().addClass('hide');
		$('.reset-date-error').addClass('hide').css('display','none');
		var baseVal = $('#logout').attr('base_access_limit');
		$('.customer-base-limit').val(baseVal);
		$('.customer-reset-month').val(1);
		$('#customer-update-button').addClass('hide');
		$('#customer-add-button').removeClass('hide');
	});


	$(window).bind('beforeunload',function(){
	     //save info somewhere
		if($('.customer-details').hasClass('hide')){
			 return 'are you sure you want to leave?';
		}
	});

	$(document).on('click', '#customer-cancel-button', function () {
		if($('#addCustomer').text() == "Edit customer"){
			$('.editCustomerSection').removeClass('hide');
			$('.addCustomerSection').addClass('hide');
		}else{
			$('.editCustomerSection').addClass('hide');
			$('.addCustomerSection').removeClass('hide');
		}
		$('#cancelConfirmModal').foundation('reveal', 'open');
	});

	$(document).on('click', '#api-cancel-button', function () {
		$('#apiConfirmModal').foundation('reveal', 'open');
		apiCancelClick = true;
	});

	$(document).on('click', '#addCustomer-cancel-btn', function () {
		$('#cancelConfirmModal').foundation('reveal', 'close');
		newObj = {}
		firstPagination(newObj);
		clearAllDetails();
	});

	$(document).on('click', '#cancel-cnfrm-btn', function () {
		if(apiCancelClick){
			cancelSaveClicked = true;
			$("#api-update-button").click();
		}else{
			cancelSaveClicked = true;
			$("#customer-update-button").click();
		}
	});

	$(document).on('click', '#cancel-no-btn', function () {
		$('#cancelConfirmModal').foundation('reveal', 'close');
	});

	$(document).on('click', '#add-product-icon', function () {
		$('.product-list').removeClass('hide');
		$('#product-select').html('');
		$.renderAllProducts({
			success: function (data) {
				if(data != null){
					$("#product-select").append("<option>Select One</option>")
					for(var idx=0; idx<data.length; idx++){
						$("#product-select").append(renderListProductsTemplate.evaluate(data[idx]));
					}
				}
			}
		});
	});

	$(document).on('change', '#product-select', function () {
		var id = $(this).find("option:selected").attr('product_id');
		$('.error-msz').remove();
		$('.no-products').remove();
		checkId = parseInt(id);
		$(".product-label").find('.loading-icon').remove();
		if(!($.inArray(checkId, productsSelected) > -1)){
			productsSelected.push(parseInt(id));
			$('.product-list').addClass('hide');
			$('.all-products').removeClass('hide');
			$('#single-product').removeClass('hide');
			$.renderSingleProduct(id,{
				success: function (data) {
					data.productId = data.id;
					if(data.isActive == '' || data.isActive == undefined){
						data.isActive = "Yes";
					}
					if(data.daysBack == '' || data.daysBack == undefined){
						data.daysBack = 7;
					}
					if($('#addCustomer').text() == "Edit customer"){
						data.hide = 'hide';
						data.edit = '<img class="product-edit-icon paddingLeft15 hide" src="/data-api/images/edit-icon.png">'
					}
					$(".product-label").prepend(renderProductDetailsTemplate.evaluate(data));
					$(".product-label div:first-child").find('.product-allTracks').removeClass('hide');
				}
			});
		}else{
			$('.all-products').prepend('<div class="error-msz">This product has been  selected</div>')
		}


	});

	var clickedId = '', editProduct = {};
	$(document).on('click', '.allTracks', function () {
		clickedId = $(this).parent().closest('#single-product').attr('product_id');

		if(!(this.checked)){
			editProduct = $(this).parent().closest('#single-product');
			$('#trackConfirmModal').attr('product_id',clickedId);
			$('#trackConfirmModal').foundation('reveal', 'open');
			editProduct.find('.allTracks').prop('checked', true);
		}else{
			editProduct = $(this).parent().closest('#single-product');
			editProduct.find("#addTracks").addClass('hide');
			editProduct.find('.selected-tracks').addClass('hide');
			editProduct.find('.selected-tracks-list').addClass('hide');
			selected = [];
		}
    });

	$(document).on('click', '#track-cnfrm-btn', function () {
		selected = [];
		if(editProduct.find('.allTracks').checked){
			editProduct.find('.allTracks').prop('checked', true);
			editProduct.find('#addTracks').addClass('hide');
		}else{
			editProduct.find('#addTracks').removeClass('hide');
			editProduct.find('.allTracks').prop('checked', false);
			editProduct.find('.tracks').remove();
		}
		$('#trackConfirmModal').foundation('reveal', 'close');
	});

	$(document).on('click', '#track-no-btn', function () {
		editProduct.find('#addTracks').addClass('hide');
		editProduct.find('.allTracks').prop('checked', true);
	});

	$(document).on('click', '.product-edit-icon', function () {
		if($(this).hasClass('tracksLoading')){
			alert('Please wait tracks are loading...')
		}else{
			$(this).css('pointer-events','none');
			var eachProduct = $(this).parent().closest('#single-product');
			eachProduct.find('.product-allTracks').removeClass('hide');
			var checkTrackCheckbox = eachProduct.find('.allTracks');
			//Change isActive column to dropdown.
			var isActiveText = eachProduct.find('.dropdown-active').text();
			var daysBack = eachProduct.find('.daysBack').text();
			if(daysBack != 'NA'){
				eachProduct.find('.daysBack').html('<input type="text" class="daysBack-val">');
				eachProduct.find('.daysBack .daysBack-val').val(daysBack);
			}else{
				eachProduct.find('.daysBack').html('<input type="text" class="daysBack-val">');
			}

			if(isActiveText == 'Yes'){
				eachProduct.find('.dropdown-active').html('<select class="isactive-select"><option value="yes">YES</option><option value="no">NO</option></select>');
			}else{
				eachProduct.find('.dropdown-active').html('<select class="isactive-select"><option value="no">NO</option><option value="yes">YES</option></select>');
			}
			if(checkTrackCheckbox.prop('checked') != true){
				eachProduct.find('.selected-tracks-list, .selected-tracks, .track-text, #addTracks').removeClass('hide');
			}
		}
    });

	$(document).on('click', '#api-key-icon', function () {
		$('.api-grid').removeClass('hide');
		$(this).css('pointer-events','none');
		$.renderAPIKey({
			success: function (data) {
				if(data != null){
					if(data.isActive == true){
						data.isActive = "Yes"
					}else{
						data.isActive = "No"
					}
					$('.api-radio').removeAttr('checked');
					data.checked = "checked";
					if(data.disabledAtStr == undefined){
						data.disabledAtStr = "NA"
					}
					$(".api-key-details").prepend(renderApiKeyTemplate.evaluate(data));
				}else{
					$(".api-key-details").append('<div>No API key Generated</div>');
				}
			}
		});
	});

	$(document).on('click', '#add-track-icon', function () {
		$(".render-all-tracks").html('');
		var id = $(this).attr('product_id');
		var each_id = $(this).closest('#single-product').attr('each_product_id');
		$('.selectall').attr('checked', false);
		$('#addTracksModal').attr('product_id',id);
		$('#addTracksModal').attr('each_id',each_id);
		findProduct = $(this).parent().parent().closest('#single-product');
		findProduct.find('.loading-track-icon').removeClass('loading-hide');
		$('#addTracksModal .selectall').attr('clicked','no');
		var checkTracks = findProduct.find('.selected-track-div').hasClass('tracks');
		if(!checkTracks){
			selected = [];
		}else{
			selected = [];
			$.each(findProduct.find('.selected-track-div'), function(){
				var trackId = $(this).attr('track_id');
				selected.push(trackId);
        	});
		}
		$.renderAllTracks({
			success: function (data) {
				if(data != null){
					findProduct.find('.loading-track-icon').addClass('loading-hide');
					for(var idx =0; idx<data.length; idx++){
						$(".render-all-tracks").append(renderAllTracksTemplate.evaluate(data[idx]));
						$.each($('.track-checkbox'), function(){
							var trackId = $(this).attr('track_id');
							if($.inArray(trackId, selected) > -1){
								$(this).prop('checked', true);
							}else{
								$(this).prop('checked', false);
							}
	                	});
					}
				}
			}
		});
	});

	$(document).on('click', '#add-tracks-button', function () {
		var customerProduct = {}, selectedTrackId = [], customerObj = {}, customerProducts = [], productTracks = [], newObj= {};
		var allTracksFlag = true;
		var id = $(this).parent().closest('#addTracksModal').attr('product_id');
		var selectAllChecked = $('#addTracksModal .selectall');
		var productId = parseInt($(this).parent().closest('#addTracksModal').attr('each_id'));
		var customerId = parseInt($('.customer-login-id').attr('customer_id'));
		var productActive = findProduct.find('.dropdown-active').text();
		if(productActive != "YESNO" && productActive != "NOYES"){
			if(productActive == "Yes"){
				productActive = true;
			}else{
				productActive = false
			}
		}else{
			productActive = findProduct.find('.isactive-select option:selected').val();
			if(productActive == "yes"){
				productActive = true;
			}else{
				productActive = false
			}
		}
		if(selectAllChecked.prop('checked') == true){
			findProduct.find('.allTracks').prop('checked', true);
			findProduct.find('#addTracks').addClass('hide');
			findProduct.find('.selected-tracks').addClass('hide');
			findProduct.find('.selected-tracks-list').html('');
			allTracksFlag = true;
		}else{
			findProduct.find('.selected-tracks-list').html('');
			selected = [];
			allTracksFlag = false;
			var counter = 0;
			$.each($('.track-checkbox'), function(){
				var track ={};
				var trackId = $(this).attr('track_id');
				var trackCountry = $(this).attr('track_country');
				var trackType = $(this).attr('track_type');
				var trackName = $(this).closest('.track-details-grid').find('.track-name').text();
				if(this.checked){
					var productTrack={};
					track.id = trackId;
					track.name = trackName;
					track.type = trackType;
					track.country = trackCountry;
					selected.push(track);
					selectedTrackId.push(trackId);
					productTrack.track = track;
					productTracks[counter++] = productTrack;
				};
			});
			findProduct.find('.selected-tracks-list, .track-text, .selected-tracks').removeClass('hide');
			for(var idx=0; idx<selected.length;idx++){
				findProduct.find(".selected-tracks-list").append('<div class="small-12 large-12 medium-12 columns selected-tracks-div">'+
						'<div class="selected-track-div tracks small-12 large-2 medium-2 columns" track_id='+selected[idx].id+'><img src="/data-api/images/close-icon.png" class="tracks-close-icon" product_id='+id+'><span class="track-id">'+selected[idx].id+'</span></div>'+
						'<div class="small-12 large-2 medium-2 columns">'+selected[idx].country+'</div><div class="small-12 large-1 medium-1 columns">'+selected[idx].type+'</div><div class="small-12 large-7 medium-7 columns">'+selected[idx].name+'</div></div>');
			}
		}
		//It will save Tracks and product to DB.
		if($('#addCustomer').html() == "Edit customer"){
			customerProduct.productId = productId;
			customerProduct.isActive = productActive;
			customerProduct.allTracks = allTracksFlag;
			customerProduct.productTracks=productTracks;
			customerProducts[0] = customerProduct;
			customerObj.customerProducts = customerProducts;
			customerObj.id = customerId;
			newObj = Object.toJSON(customerObj);
			$.saveProductTracks(newObj,{
				success: function (data) {
					$('#addTracksModal').foundation('reveal', 'close');
				}
			});
		}else{
			$('#addTracksModal').foundation('reveal', 'close');
		}

	});

	var nowTemp = new Date();
	var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);
	var checkin = $('#dp4').fdatepicker({
		onRender: function (date) {
			return date.valueOf() < now.valueOf() ? 'disabled' : '';
		}
	}).on('changeDate', function (ev) {
			$('#startDate').val($('#dp4').data('date'));
			$('#dp4').fdatepicker('hide');
	}).data('datepicker');
	var checkout = $('#dp5').fdatepicker({
		onRender: function (date) {
			return date.valueOf() <= checkin.date.valueOf() ? 'disabled' : '';
		}
	}).on('changeDate', function (ev) {
		endDate = new Date(ev.date);
		$('#endDate').val($('#dp5').data('date'));
		$('#dp5').fdatepicker('hide');
	}).data('datepicker');

	$(document).on('click', '#view-product-link', function () {
		var id= $(this).attr("product_id");
		$("#view-all-products, .productCompanyName").html('');
		$("#view-all-products").append('<img src="/data-api/images/loading.gif" class="loading-icon">');
		$.viewCustomerProductLink(id,{
			success: function (data) {
				$("#view-all-products").html('');
				if(data != null){
					$('.productCompanyName').html(data.companyName);
					if(data.customerProducts != null){
						for(var idx =0; idx<data.customerProducts.length; idx++){
							if(data.customerProducts[idx].isActive == true){
								data.customerProducts[idx].isActive = 'Yes'
							}else{
								data.customerProducts[idx].isActive = 'No'
							}
							if(data.customerProducts[idx].allTracks == true){
								data.customerProducts[idx].allTracks = "All Tracks";
							}else{
								var dataTemp = data.customerProducts[idx].productTracks;
								var selected = [];
								if(dataTemp != undefined && dataTemp != null){
									for (var i=0; i<dataTemp.length; i++){
										selected.push(dataTemp[i].track.id);
									}
									data.customerProducts[idx].allTracks = selected;
								}else{
									data.customerProducts[idx].allTracks = "No Tracks";
								}

							}
							$("#view-all-products").append(renderAllProductsTemplate.evaluate(data.customerProducts[idx]));
							loginRoles();
						}
					}else{
						$("#view-all-products").html("<div class='no-products text-center marginTop10'>No products for this customer</div>");
					}
				}
			}
		});
	});

	var firstPagination = function(newObj){
		sortObj = Object.toJSON(newObj);
		$("#customer-all-details").append('<img src="/data-api/images/loading.gif" class="loading-icon">');
		$.renderPaginationCustomers(sortObj, startPage, pageSize,{
			success: function (data) {
				if(data != null && data.results != null && data.results != undefined){
					$("#customer-all-details").html('');
					for(var idx=0; idx<data.results.length; idx++){
						noOfPages = data.totalNumberOfPages;
						totalNumberOfResults = data.totalNumberOfResults;
						if(data.results[idx].isActive == true){
							data.results[idx].isActive = 'Yes'
						}else{
							data.results[idx].isActive = 'No'
						}
						if(data.results[idx].email == ' '){
							data.results[idx].email = '-'
						}
						if(data.results[idx].companyName == ' '){
							data.results[idx].companyName = '-'
						}

						if(data.results[idx].customerApiKeys !=null){
							for(var j=0; j<data.results[idx].customerApiKeys.length; j++){
								data.results[idx].customerApiKeys[j] = "<option>"+data.results[idx].customerApiKeys[j].apiKey+"</option>";
							}
						}
						$("#customer-all-details").append(renderAllCustomersTemplate.evaluate(data.results[idx]));
						loginRoles();
					}
				}else{
					$("#customer-all-details").html('<div class="text-center customer-text marginTop50">No Customers</div>');
				}
				if(totalNumberOfResults > pageSize){
					pagination(noOfPages);
				}else{
					$('.pagination').remove();
				}

			}
		});
	};

	var newObj = {}
	firstPagination(newObj);

	pagination = function(noOfPages) {
		//Pagination
		var SortCriteria = {}, pageNum = '';
		$('#customerPagSrch').bootpag({
			total: noOfPages, //number of pages
			page: 1,  //page to show on start
			maxVisible: 5 //maximum number of visible pages
		});

	};

	$('#customerPagSrch').on("page", function(event, pageNum){ //To triggering click on pages
		if (pageNum != null && pageNum != undefined) {
			$("#customer-all-details").html('');
			var SortCriteria = {};
			$.each($('.sort-filed'), function(){
				if($(this).attr('clicked') == 'yes'){
					sorting = $(this).attr('filter');
					sortType = $(this).attr('sort_type');
					SortCriteria.sortingField = sortType;
					SortCriteria.sortOrder = sorting;
				}
			});
			displayPaginationCustomers(SortCriteria, pageNum, pageSize);
		}
	});

	displayPaginationCustomers = function(newObj, pageNum, pageSize){
		sortObj = Object.toJSON(newObj);
		$("#customer-all-details").append('<img src="/data-api/images/loading.gif" class="loading-icon">');
		$.renderPaginationCustomers(sortObj, pageNum, pageSize,{
			success: function (data) {
				if(data != null && data.results != null && data.results != undefined){
					//$("#customer-all-details").html('');
					for(var idx=0; idx<data.results.length; idx++){
						noOfPages = data.totalNumberOfPages;
						totalNumberOfResults = data.totalNumberOfResults;
						if(data.results[idx].isActive == true){
							data.results[idx].isActive = 'Yes'
						}else{
							data.results[idx].isActive = 'No'
						}
						if(data.results[idx].customerApiKeys !=null){
							for(var j=0; j<data.results[idx].customerApiKeys.length; j++){
								data.results[idx].customerApiKeys[j] = "<option>"+data.results[idx].customerApiKeys[j].apiKey+"</option>";
							}
						}
						$('.loading-icon').remove();
						$("#customer-all-details").append(renderAllCustomersTemplate.evaluate(data.results[idx]));
						loginRoles();
					}
				}
			}
		});
	}

	$(document).on('click', '#customer-add-button', function () {
		var customerObj = {}, customerProducts = [], customerProduct ={}, customerApiKeys=[], customerApiKey={},customerApiLimit={}, customerProducts=[];
		$('.error-msz').remove();
		var loginId = $('.customer-login-id').val();
		var passwordStr = $('.customer-password').val();
		var email= $('.customer-email').val();
		var companyName= $('.customer-company').val();
		var resetDayOfMonth = $('.customer-reset-month').val();
		if(resetDayOfMonth >=1 && resetDayOfMonth <= 25 && resetDayOfMonth != '' && resetDayOfMonth != undefined){
			dateReset = true;
			$('.reset-date-error').addClass('hide').css('display','none');
			$('.customer-reset-month').css('border-color','#427ABA');
		}else{
			dateReset = false;
			$('.reset-date-error').removeClass('hide').css('display','inline-block');
			$('.customer-reset-month').css('border-color','red');
		}
		var apiKey=$('.api-key-details .api-details-grid').find('.api-text').text();
		checkLoginId();
		checkTextValidations();
		checkPassword();
		var pushAllProducts = renderProductObj();
		if(loginId && passwordStr && email  && resetDayOfMonth){
			checkValidations = true;
		}else{
			checkValidations = false;
			$('.error-append').append('<div class="error-msz">All fileds are required.</div>')
		}
		if(checkValidations && checkCustomerId && dateReset){
			$('.edit-customer-loading').removeClass('hide');
			customerObj.loginId = loginId;
			customerObj.passwordStr = passwordStr;
			customerObj.companyName = companyName;
			customerObj.email = email;
			customerObj.resetDayOfMonth = resetDayOfMonth;
			//API keys
			if(apiKey != null && apiKey != ""){
				customerApiKey.apiKey = apiKey;
				customerApiKeys[0] = customerApiKey;
				customerObj.customerApiKeys= customerApiKeys;
			}
			if(pushAllProducts.length >0){
				for(idx=0; idx<pushAllProducts.length; idx++){
					var temp = {},customerProduct={},productTracks=[];
					temp = pushAllProducts[idx];
					var pid = temp.productId;
					var allTracksFlag = temp.allTracks;
					var tracks = temp.tracks;
					if(!allTracksFlag) {
						for(j=0; j<tracks.length; j++){
							var trackId = tracks[j];
							var track = {};
							var productTrack={};
							track.id = trackId;
							productTrack.track = track;
							productTracks[j] = productTrack;
						}
						customerProduct.productTracks =productTracks;
					}

					customerProduct.productId = pid;
					customerProduct.allTracks = allTracksFlag;
					customerProducts[idx] = customerProduct;
				}
				customerObj.customerProducts = customerProducts;
			}
			newObj = Object.toJSON(customerObj);
			$.addCustomer(newObj,{
				success: function (data) {
					newObj = {};
					firstPagination(newObj);
					$('.edit-customer-loading').addClass('hide');
					clearAllDetails();
				},
				error: function(data) {
					$('.edit-customer-loading').addClass('hide');
					alert('Customer Login Id already exists.');
					$('.customer-login-id').css('border-color','red');
				}
			});
		}

    });

	$(document).on('click', '.tracks-close-icon', function () {
		var $this = $(this).closest('.selected-tracks-div');
		$this.remove();
		var id = $(this).parent().attr("track_id");
		selected.splice($.inArray(id, selected), 1);
		var addedTracks = $this.find('.selected-track-div').hasClass('tracks');
		if(addedTracks){
			$this.removeClass('hide');
		}else{
			selected = [];
			$this.addClass('hide');
		}
	});

	renderProductObj = function(){
		var pushAllProducts = [];
		$.each($('.product-label #single-product'), function(){
        	var productId = $(this).attr('each_product_id');
            var findAllTracks = $(this).find('.allTracks').is(':checked');
            checkActive = $(this).find('.dropdown-active');
            checkIsActive = checkActive.find('option:selected').val();
            checkText = $(this).find('.dropdown-active select').hasClass('isactive-select');
            if(checkIsActive == "yes" || checkText){
            	isActiveText = $(this).find('option:selected').val();
            	var isActive = true;
            	if(isActiveText == "yes"){
            		isActive = true;
            	}else{
            		isActive = false;
            	}
            	daysBack = $(this).find('.daysBack .daysBack-val').val();
            	dayaBack = parseInt(daysBack);
            	if(findAllTracks){
                	allTracks = true;
                	pushAllProducts.push({"productId": parseInt(productId), "allTracks": true, "isActive": isActive, "daysBack":daysBack});

                }else{
                	allTracks = false;
                	var tracks = [];
                	$(this).find('.selected-tracks').removeClass('hide');
                	var selectedTracks = $(this).find('.selected-tracks-list .selected-track-div');
                	//When no tracks are selected for particular product.
                	//We are sending all tracks as true for that product.
                	if(!selectedTracks){
                		pushAllProducts.push({"productId": parseInt(productId), "allTracks": true, "isActive": isActive, "daysBack":daysBack});
                	}else{
                		$.each(selectedTracks, function(){
                    		selectedTrack = $(this).attr('track_id');
                    		tracks.push(selectedTrack);
                    	});
                		pushAllProducts.push({"productId": parseInt(productId), "allTracks": false, "tracks":tracks, "isActive": isActive, "daysBack":daysBack});
                	}
                }
            }else{
            	daysBack = $(this).find('.daysBack').html();
            	if(daysBack == 'NA'){
            		daysBack = '';
            	}
            	isActiveText = $(this).find('.dropdown-active').text();
            	var isActive = true;
            	if(isActiveText == "Yes"){
            		isActive = true;
            	}else{
            		isActive = false;
            	}
            	if(findAllTracks){
                	allTracks = true;
                	pushAllProducts.push({"productId": parseInt(productId), "allTracks": true, "isActive": isActive, "daysBack":daysBack});

                }else{
                	allTracks = false;
                	var tracks = [];
                	$(this).find('.selected-tracks').removeClass('hide');
                	var selectedTracks = $(this).find('.selected-tracks-list .selected-track-div');
                	//When no tracks are selected for particular product.
                	//We are sending all tracks as true for that product.
                	if(!selectedTracks){
                		pushAllProducts.push({"productId": parseInt(productId), "allTracks": true, "isActive": isActive, "daysBack":daysBack});
                	}else{
                		$.each(selectedTracks, function(){
                    		selectedTrack = $(this).attr('track_id');
                    		tracks.push(selectedTrack);
                    	});
                			pushAllProducts.push({"productId": parseInt(productId), "allTracks": false, "tracks":tracks, "isActive": isActive, "daysBack":daysBack});
                	}

                }
            }
        });
		return pushAllProducts;
	};

	clearAllDetails = function(){
		$('.add-customer').addClass('hide');
		$('.api-grid').addClass('hide');
		$('.error-msz').remove();
		$('.customer-details').removeClass('hide');
		$('input[type="text"]').val('');
		$('input[type="password"]').val('');
		$('#product-select').html('');
		$('.product-label').html('');
		$('.all-products').addClass('hide');
		$('.customer-space-msz').addClass('hide');
		$('.api-key-details').html('');
		$('.reset-date-error').addClass('hide').css('display','none');
		selected=[];
		checkCustomerId = true;
		productsSelected=[];
		$('#api-key-icon').css('pointer-events','');
		$('.customer-login-id').removeAttr('readonly');
	};

	checkTextValidations = function(){
		$.each($('input[type="text"]'), function(){
			var val = $(this).val().trim();
			if(val == ""){
				checkValidations = false;
				$(this).addClass('redBorder');
			}else{
				checkValidations = true;
				$(this).removeClass('redBorder');
			}
		});

		numberValidation = ["customer-base-limit"];
		for(idx=0; idx < numberValidation.length; idx++){
			$.each($('.'+numberValidation[idx]+''), function(){
				var val = $(this).val().trim();
				var newval = val;
				var pattern = new RegExp('^[0-9]+$');
				if(val !=""){
					if (!pattern.test(newval)) {
						checkValidations = false;
						$(this).addClass('redBorder');
						 if($('#addCustomer').text() == "Edit customer"){
							 $(this).parent().append('<div class="error-msz edit-textfields-error">This should be a number.</div>');
						 }else{
							 $(this).parent().append('<div class="error-msz number-validate">This should be a number.</div>');
						 }

					}else{
						checkValidations = true;
						var checkNumber = $(this).parent().next();
						if(checkNumber.hasClass('.number-validate')){
							checkNumber.remove();
						}
						$(this).removeClass('redBorder');
					}
				}

			});
		}

		//Email Validations
		var emailRegex = new RegExp('^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$');
		var emailVal = $('.customer-email').val().trim();
		var $this = $('.customer-email');
		if(emailVal !=""){
			if (!emailRegex.test(emailVal)) {
				checkValidations = false;
				$this.addClass('redBorder');
				if($('#addCustomer').text() == "Edit customer"){
					$this.parent().append('<div class="error-msz edit-textfields-error">Invalid Email Format</div>');
				}else{
					$this.parent().append('<div class="error-msz number-validate">Invalid Email Format</div>');
				}
			}else{
				checkValidations = true;
				var checkNumber = $this.parent().next();
				if(checkNumber.hasClass('.number-validate')){
					checkNumber.remove();
				}
				$this.removeClass('redBorder');
			}
		}

		//Password Validations
		var password = $('.customer-password').val().trim();
		var PASSWORD_PATTERN_1 =  new RegExp('((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{12,})');
		var PASSWORD_PATTERN_2 = new RegExp('((?=.*\\d)(?=.*[a-z])(?=.*[!@#$_%]).{12,})');
		var PASSWORD_PATTERN_3 = new RegExp('((?=.*\\d)(?=.*[A-Z])(?=.*[!@#$_%]).{12,})');
		var PASSWORD_PATTERN_4 = new RegExp('((?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$_%]).{12,})');
		var $this = $('.customer-password');
		if(password !=""){
			if (!PASSWORD_PATTERN_1.test(password) || !PASSWORD_PATTERN_2.test(password) || !PASSWORD_PATTERN_3.test(password) || !PASSWORD_PATTERN_4.test(password)) {
				checkValidations = false;
				$this.addClass('redBorder');
				$('.pwd-position').css({color:'red', border:'1px solid red'});
				$this.focus()
			}else{
				checkValidations = true;
				$('.passwordMsz').remove();
				$this.removeClass('redBorder');
				$('.pwd-position').css({color:'#7F7F7F', border:'1px solid #427ABA'});
			}
		}

	};

	var checkCustomerId = true;
	checkLoginId = function(){
		var loginVal = $('.customer-login-id').val().trim();
	    var loginId = new RegExp('\\s');
	    if (loginId.test(loginVal)) {
	    	checkCustomerId = false;
	       $('.customer-space-msz').removeClass('hide');
	       $('.customer-login-id').css('border-color','red');
	    }else{
	    	checkCustomerId = true;
	    	$('.customer-space-msz').addClass('hide');
	    	 $('.customer-login-id').css('border-color','#427aba');
	    }
	};

	checkPassword = function(){
		var val = $('.customer-password').val().trim();
		if(val == ""){
			checkValidations = false;
			$('.customer-password').addClass('redBorder');
		}else{
			checkValidations = true;
		}
	};

	checkEditApiKey = function(){
		var apiKeyObj = {}, apiKey='', isActive = true;
		$('.api-radio').each(function() {
			if($(this).is(':checked')){
				apiKey = $(this).parent().next().text();
				isActive = $(this).parent().next().next().text();
				if(isActive == 'Yes'){
					isActive = true;
				}else{
					isActive = false;
				}
				apiKeyObj.apiKey = apiKey;
				apiKeyObj.isActive = isActive;
			}

        });
		return apiKeyObj;
	}

	$(document).on('keyup', 'input[type="text"]', function () {
		$.each($('input[type="text"]'), function(){
			var val = $(this).val().trim();
			if(val != ""){
				checkValidations = true;
				$(this).removeClass('redBorder');
				$(this).css('border-color','#427aba');
				$(this).parent().find('.error-msz').remove();
				$('.customer-space-msz').addClass('hide');
				$('.reset-date-error').addClass('hide').css('display','none');
			}
		});
		$('#customer-update-button').css('pointer-events','');
    });

	$(document).on('keyup', 'input[type="password"]', function () {
		checkValidations = true;
		$(this).removeClass('redBorder');
		$('.pwd-position').css({color:'#7F7F7F', border:'1px solid #427ABA'});
		$('#customer-update-button').css('pointer-events','');
    });

	$(document).on('keyup', '.customer-password', function () {
		var val = $(this).val().trim();
		if(val != ""){
			checkValidations = true;
			$(this).removeClass('redBorder');
		}
    });

	//Disable Add Admin button based on user login role.
	loginRoles = function(){
		var loginRole = $('#logout').attr('login_role');
		if((loginRole == 'LEVEL_1' || loginRole == 'LEVEL_2') && loginRole != null){
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

	$(document).on('click', '.customer-edit-icon', function () {
		var id = $(this).attr('customer_id');
		$('.product-edit-icon').css('pointer-events','');
		$('.changeCustomerPwd, .add-customer, .customer-active').removeClass('hide');
		$('input[type="text"]').removeClass('redBorder');
		$('.customer-details').addClass('hide');
		$('#customer-update-button').removeClass('hide').css('pointer-events','');
		$('.customer-access-limit').parent().removeClass('hide');
		$('.reset-date-error').addClass('hide').css('display','none');
		$('#customer-add-button').addClass('hide');
		$('.customer-login-id').attr('readonly','readonly');
		$('#addCustomer').html('Edit customer');
		$('.customer-password').parent().addClass('hide');
		$('.customer-fields span').css('display','inline-block');
		var customerGrid = $(this).parent().closest('.customer-details-grid');
		var email = customerGrid.find('.edit-email').text().trim();
		var companyName = customerGrid.find('.edit-company').text().trim();
		var loginId = customerGrid.attr('login_id');
		var customerId = customerGrid.attr('customer_id');
		$('.customer-login-id').attr('customer_id',customerId);
		var accessLimit = customerGrid.find('.edit-access').text().trim();
		var resetMonth = customerGrid.find('.edit-reset-month').text().trim();
		$('.customer-login-id').val(loginId);
		$('.customer-email').val(email);
		$('.customer-base-limit').val(accessLimit);
		$('.customer-reset-month').val(resetMonth);
		$('.customer-company').val(companyName);
		$('.all-products').removeClass('hide');
		$(".product-label, .product-allTracks").append('<img src="/data-api/images/loading.gif" class="loading-icon">');
		$.viewAllcustomers(id,{
			success: function (data) {
				$(".product-label").html("");
				$('.customer-is-active').html('');
				if(data.isActive == true){
					$('.customer-is-active').append('<option value="yes">Yes</option><option value="no">No</option>');
				}else{
					$('.customer-is-active').append('<option value="no">No</option><option value="yes">Yes</option>');
				}
				if(data != null && data.customerProducts != null){
					for(var idx =0; idx<data.customerProducts.length; idx++){
						var productId = data.customerProducts[idx].product.id;
						productsSelected.push(data.customerProducts[idx].product.id);
						var obj = data.customerProducts[idx];
						if(data.customerProducts[idx].isActive == true){
							data.customerProducts[idx].isActive = 'Yes'
						}else{
							data.customerProducts[idx].isActive = 'No'
						}
						if(data.customerProducts[idx].daysBack == '' || data.customerProducts[idx].daysBack == undefined){
							data.customerProducts[idx].daysBack = 'NA';
						}
						data.customerProducts[idx].label = data.customerProducts[idx].product.label;
						data.customerProducts[idx].type = data.customerProducts[idx].product.type;
						data.customerProducts[idx].level = data.customerProducts[idx].product.level;
						data.customerProducts[idx].productId = data.customerProducts[idx].product.id;
						data.customerProducts[idx].edit = '<img class="product-edit-icon tracksLoading paddingLeft15 hide" src="/data-api/images/edit-icon.png">';
						data.customerProducts[idx].hide = 'hide';
						$(".product-label").append(renderProductDetailsTemplate.evaluate(data.customerProducts[idx]));
					}
					$.viewAllcustomers(id,{
						success: function (data) {
							if(data != null && data.customerProducts != null){
								for(var idx =0; idx<data.customerProducts.length; idx++){
									var productId = data.customerProducts[idx].product.id;
									var obj = data.customerProducts[idx];
									var getProduct = $('.product-each');
									getProduct.each( function(){
										var findId = $(this).attr('each_product_id');
										if(findId == productId){
											getProduct = $(this);
										}
									});

									if(obj.allTracks != true){
										selected = [];
										if(obj.productTracks != undefined && obj.productTracks !=null ){
											for(var j=0; j<obj.productTracks.length; j++){
												selected.push((obj.productTracks[j].track));
											}
											for(var i=0; i<selected.length;i++){
												getProduct.find('#addTracks').removeClass('hide');
												getProduct.find(".selected-tracks-list").append('<div class="small-12 large-12 medium-12 columns selected-tracks-div">'+
																									'<div class="selected-track-div tracks small-12 large-2 medium-2 columns" track_id='+selected[i].id+'><img src="/data-api/images/close-icon.png" class="tracks-close-icon" product_id='+productId+'><span class="track-id">'+selected[i].id+'</span></div>'+
																									'<div class="small-12 large-2 medium-2 columns">'+selected[i].country+'</div><div class="small-12 large-1 medium-1 columns">'+selected[i].type+'</div><div class="small-12 large-7 medium-7 columns">'+selected[i].name+'</div></div>');
											}
										}
										getProduct.find('.allTracks').removeAttr('checked');
									}

								}
							}
							$(".product-edit-icon").removeClass('tracksLoading');
						}

					});
				}else{
					$(".product-label").html("<div class='no-products text-center marginTop10'>No products for this customer</div>");
				}
			}
		});



			$.getCustomerCurrentMonthApiLimits(id,{
				success: function (data) {
					if(data != null){
						$('#startDate').val(data.startDateStr);
						$('#startDate').attr('api_limit_id', data.id);
						$('#endDate').val(data.endDateStr);
						$('.customer-access-limit').val(data.currentAccessLimit);
						accessLimitval = data.currentAccessLimit;
						if(data.startDateStr != undefined && data.endDateStr != undefined && data.currentAccessLimit != undefined){
							$('.customer-api-limit').removeClass('hide');
						}
					}
				}
			});
			$.getCustomerApiKeys(id,{
				success: function (data) {
					if(data != null){
						for(idx=0; idx<data.length; idx++){
							$('.api-grid').removeClass('hide');
							//$('.api-radio').removeAttr('checked');
							if(idx == 0){
								data[idx].checked = "checked";
							}else{
								data[idx].checked = "";
							}

							if(data[idx].isActive){
								data[idx].isActive = "Yes"
							}else {
								data[idx].isActive = "No"
							}
							if(data[idx].disabledAtStr == undefined){
								data[idx].disabledAtStr = "NA"
							}
							$(".api-key-details").append(renderApiKeyTemplate.evaluate(data[idx]));
						}
					}
				}
			});
    });

	$(document).on('click', '#customer-update-button', function () {
		var customerObj = {}, customerProducts = [], customerProduct ={}, customerApiKeys=[], customerApiKey={},customerApiLimit={}, customerProducts=[];
		$('.error-msz').remove();
		$(this).css('pointer-events','none');
		var loginId = $('.customer-login-id').val();
		var customerId = $('.customer-login-id').attr('customer_id');
		var passwordStr = $('.customer-password').val();
		var email= $('.customer-email').val();
		var companyName= $('.customer-company').val();
		var baseAccessLimit = $('.customer-base-limit').val();
		var resetDayOfMonth = $('.customer-reset-month').val();
		var isActive = $('.customer-is-active option:selected').val();
		if(isActive == 'yes'){
			isActive = true;
		}else{
			isActive = false;
		}
		var apiKey = checkEditApiKey();
		checkTextValidations();
		if(resetDayOfMonth >=1 && resetDayOfMonth <= 25 && resetDayOfMonth != '' && resetDayOfMonth != undefined){
			dateReset = true;
			$('.reset-date-error').addClass('hide').css('display','none');
			$('.customer-reset-month').css('border-color','#427ABA');
		}else{
			dateReset = false;
			$('.reset-date-error').removeClass('hide').css('display','inline-block');
			$('.customer-reset-month').css('border-color','red');
		}
		checkPassword();
		var pushAllProducts = renderProductObj();
		if(loginId && email && baseAccessLimit  && resetDayOfMonth){
			checkValidations = true;
		}else{
			checkValidations = false;
			$('.error-append').append('<div class="error-msz">All fileds are required.</div>')
		}
		if(cancelSaveClicked){
			$('#cancelConfirmModal').foundation('reveal', 'close');
		}
		if(checkValidations && dateReset){
			$('.edit-customer-loading').removeClass('hide');
			customerObj.loginId = loginId;
			customerObj.email = email;
			customerObj.id = customerId;
			customerObj.companyName = companyName;
			customerObj.baseAccessLimit = baseAccessLimit;
			customerObj.resetDayOfMonth = resetDayOfMonth;
			customerObj.isActive = isActive;
			//API keys
			if(apiKey != null && apiKey != ""){
				customerApiKey.apiKey = apiKey.apiKey;
				customerApiKey.isActive = apiKey.isActive;
				customerApiKeys[0] = customerApiKey;
				customerObj.customerApiKeys= customerApiKeys;
			}
			if(pushAllProducts.length >0){
				for(idx=0; idx<pushAllProducts.length; idx++){
					var temp = {},customerProduct={},productTracks=[];
					temp = pushAllProducts[idx];
					var pid = temp.productId;
					var isActive = temp.isActive;
					var allTracksFlag = temp.allTracks;
					var tracks = temp.tracks;
					var daysBack = temp.daysBack;
					daysBack = parseInt(daysBack);
					console.log(daysBack);
					if(!allTracksFlag) {
						for(j=0; j<tracks.length; j++){
							var trackId = tracks[j];
							var track = {};
							var productTrack={};
							track.id = trackId;
							productTrack.track = track;
							productTracks[j] = productTrack;
						}
						customerProduct.productTracks =productTracks;
					}
					customerProduct.productId = pid;
					customerProduct.isActive = isActive;
					customerProduct.allTracks = allTracksFlag;
					customerProduct.daysBack = daysBack;
					customerProducts[idx] = customerProduct;
				}
				customerObj.customerProducts = customerProducts;
			}
			newObj = Object.toJSON(customerObj);
			$.editCustomer(newObj,{
				success: function (data) {
					$('#customer-update-button').css('pointer-events','');
					newObj = {}
					firstPagination(newObj);
					$('.edit-customer-loading').addClass('hide');
					clearAllDetails();
				},
				error: function(data) {
					$('.edit-customer-loading').addClass('hide');
					alert('Unable to Edit the Customer.');
				}
			});
		}
	});

	$(document).on('click', '#pwd-update-button', function () {
		var dataTempObj = {};
		var id= $('.customer-login-id').attr('customer_id');
		var password = $('#password-new').val().trim();
		var cnfrmPassword = $('#cnfrm-password').val().trim();
		if(!password){
			$('#password-new').css('border-color','red');
		}
		if(!cnfrmPassword){
			$('#cnfrm-password').css('border-color','red');
		}
		if((password != null && cnfrmPassword != null) && (cnfrmPassword == password) && (id != null) && (cnfrmPassword.length >=12)){
			dataTempObj.id = id;
			dataTempObj.passwordStr = password;
			newObj = Object.toJSON(dataTempObj);
			$.changeCustomerPassword(newObj,{
				success: function (data) {
					$('.pwd-success').css({display:'inline-block', width:'100%'});
					$('.pwd-success').text(data.success).removeClass('hide').fadeOut(2000);
					$('.text-field').val('');
				},
				error: function(data){
					$('.pwd-rules').css({color:'red', border:'1px solid red'});
				}
			});
		}else if(cnfrmPassword != password){
			$('.pwd-error').text('password and confirm password should match').removeClass('hide');
			$('.pwd-error').addClass('text-center');
		}else if((cnfrmPassword == password) && (cnfrmPassword.length <=11)){
			$('.pwd-error').text('Password Should Contain Min 12 characters').removeClass('hide');
			$('.pwd-error').addClass('text-center');
		}

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
		$("#customer-all-details").html('');
		newObj.sortingField = sortType;
		newObj.sortOrder = sorting;
		firstPagination(newObj);
	});

	$(document).on('click', '#api-update-button', function () {
		$('.api-error-msz').remove();
		var startDateStr = $('#startDate').val();
		var endDateStr = $('#endDate').val();
		var currentAccessLimit = $('.customer-access-limit').val();
		var customerId = $('.customer-login-id').attr('customer_id');
		var customerApiLimit = {}, newObj = {}, valid = true;
		if(currentAccessLimit == ""){
			$('.customer-access-limit').addClass('redBorder');
		}
		var pattern = new RegExp('^[0-9]+$');
		if(currentAccessLimit !=""){
			if (!pattern.test(currentAccessLimit)) {
				valid = false;
				$('.customer-access-limit').addClass('redBorder');
			}
		}
		if(currentAccessLimit <= accessLimitval && accessLimitval != undefined){
			valid = false;
			$('.success-api-msz').append('<div class="api-error-msz">API limit Should be greater than '+accessLimitval+'.</div>');
			$('.api-error-msz').fadeOut(5000);

		}
		if(accessLimitval == undefined ){
			valid = false;
			$('.success-api-msz').append('<div class="api-error-msz">Unable to update API limit.</div>');
			$('.api-error-msz').fadeOut(5000);
		}
		if(cancelSaveClicked){
			apiCancelClick = false;
			$('#apiConfirmModal').foundation('reveal', 'close');
		}
		if(startDateStr != null && startDateStr != "" && endDateStr != null && endDateStr != null && currentAccessLimit != null && currentAccessLimit != "" && valid){
			customerApiLimit.startDateStr = startDateStr;
			customerApiLimit.endDateStr = endDateStr;
			customerApiLimit.currentAccessLimit = currentAccessLimit;
			customerApiLimit.customerId = customerId;
			newObj = Object.toJSON(customerApiLimit);
			$.updateCustomerApiLimits(newObj,{
				success: function (data) {
					if(data == true){
						$('.success-api-msz').append('<div class="api-limit-msz">API limits Updated Succesfully.</div>');
						accessLimitval = currentAccessLimit;
						$('.api-limit-msz').fadeOut(5000);
					}else{
						$('.success-api-msz').append('<div class="api-error-msz">API limit Should be greater than '+accessLimitval+'.</div>');
						$('.api-error-msz').fadeOut(5000);
					}
				},
				error: function(data){
					$('.success-api-msz').append('<div class="api-error-msz">Unable to update API limit.</div>');
					$('.api-error-msz').fadeOut(5000);
				}
			});
		}
	});

	$(document).on('mouseenter', ".trackList, .edit-email, .edit-company, .product-label-text", function () {
	     var $this = $(this);
	     if($this.hasClass('trackList')){
	    	 var text = $this.text().trim();
		     var tracksArray = text.split(",");
		     var newText = '';
		     var counter = 0;
		     for(var j=0; j<tracksArray.length; j++){
		    	 counter ++;
		    	 if(counter >= 8){
		    		 counter = 0;
		    		 newText = newText + ', ' +tracksArray[j];
		    	 }else{
		    		 if(j ==0) {
		    			 newText = tracksArray[j];
		    		 } else {
		    			 if(counter >= 8){
		    				 newText = newText + ',\n' +tracksArray[j];
		    			 } else {
		    				 newText = newText + ', ' +tracksArray[j];
		    			 }
		    		 }
		    	 }
		     }
	     }else{
	    	 newText = $this.text();
	     }

	     if (this.offsetWidth < this.scrollWidth && !$this.attr('title')) {
	         $this.tooltip({
	             title: newText,
	             placement: "bottom"
	         });
	         $this.tooltip('show');
	     }
	 });

	$(document).on('keydown', ".daysBack-val", function (e) {
		// Allow: backspace, delete, tab, escape, enter and .
		if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
				// Allow: Ctrl+A, Command+A
				(e.keyCode == 65 && ( e.ctrlKey === true || e.metaKey === true ) ) ||
				// Allow: home, end, left, right, down, up
				(e.keyCode >= 35 && e.keyCode <= 40)) {
			// let it happen, don't do anything
			return;
		}
		// Ensure that it is a number and stop the keypress
		if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
			e.preventDefault();
		}
	});

})(jQuery);