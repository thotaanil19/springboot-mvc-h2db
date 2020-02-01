(function($) {
	var  loginId, isActive,userName,accessLevel,editAdminLoginId,editAdminId,editAdminActive, pageSize=10,noOfPages='',totalNumberOfResults='',startPage=1;
	addAdminUsersTemplate = new Template(['<div class="user-details-grid user_#{id}">',
	                                      	  '<div class="small-12 large-3 medium-3 columns userText user-id paddingLeft30" user_id=#{id}>#{loginId}</div>',
	                              		      '<div class="small-12 large-3 medium-3 columns userText user-name">#{name}</div>',
	                              		      '<div class="small-12 large-2 medium-3 columns userText paddingLeft50 user-access">#{accessLevel}</div>',
	                              		      '<div class="small-12 large-2 medium-2 columns userText paddingLeft30 user-active">#{isActive}</div>',
		                                      '<div class="small-12 large-2 medium-1 columns lineheight35"><img src="/data-api/images/edit-icon.png" id="edit-icon" class="removeDisableEdit" data-reveal-id="editAdminModal">',
		                                      '<img src="/data-api/images/edit-icon.png" class="disableEdit hide"></div>',
		                                      '</div>'].join(''));
	renderAdminProfile = new Template(['<div class="padding10"><img src="/data-api/images/user-icon.png"><div class="greyColor adminId width120" data_id=#{id}>Login ID</div>',
	                                   		'<span class="greyColor">:</span><span class="blueColor">#{loginId}</span></div>',	
	                                   		'<div class="padding10"><img src="/data-api/images/Email-icon.png"><div class="greyColor width113">Name</div>',
	                                   		'<span class="greyColor">:</span><span class="blueColor">#{name}</span></div>',
	                                   		'<div class="padding10"><img src="/data-api/images/access-level-icon.png"><div class="greyColor width120">Access Level</div>',
	                                   		'<span class="greyColor">:</span><span class="blueColor">#{accessLevel}</span></div>',
	                                   		'<div class="padding10"><img src="/data-api/images/is-active-icon.png"><div class="greyColor width120">Is Active</div>',
	                                   		'<span class="greyColor">:</span><span class="blueColor">#{isActive}</span></div>'].join(''));
	
	//Open add Admin users modal.
	$(document).on('click', '#add-admin-button', function () {
		$('input[type="text"]').val('');
		$('input[type="password"]').val('');
		$('#addUser-add-button').css('pointer-events','');
		$('.add-access-level').val('');
		$('.error-msz').remove();
		$('.error-msz1').addClass('hide');
		allValidations = true;
		loginRoles();
	});
	
	//Adding Admin users.	
	$(document).on('click', '#addUser-add-button', function () {
		var dataTempObj = {};
		$('.error-msz').remove();
		var id = $('.add-login-id').val().trim();
		var name = $('.add-user-name').val().trim();
		var accessLevel = $( ".add-access-level option:selected" ).text().trim();
		var isActive = true;
		var password = $('.add-user-password').val().trim();
		var cnfrmPassword = $('.add-user-cnfrmPwd').val().trim();
		if(password != cnfrmPassword){
			$('.modal-text-add').append('<div class="error-msz">Password and Confirm Password should match.</div>');
		}else{
			var passwordStr = password;
		}
		if(id && name && accessLevel !="Select Level" && passwordStr){
			dataTempObj.loginId = id;
			dataTempObj.name = name;
			dataTempObj.accessLevel = accessLevel;
			dataTempObj.isActive = isActive;
			dataTempObj.passwordStr = passwordStr;
			newObj = Object.toJSON(dataTempObj);
			validations();
			if(allValidations){
				$.addAdminUsers(newObj,{
					success: function (data) {
						if(dataTempObj.isActive == true){
							dataTempObj.isActive = "Yes"
						}else{
							dataTempObj.isActive = "No"
						}
						dataTempObj.id = data.id;
						$(".add-users-div").prepend(addAdminUsersTemplate.evaluate(dataTempObj));
						$('#addAdminModal').foundation('reveal', 'close');
					},
					error: function(data){
						var error = data.responseJSON.errors;
						if(error != null){
							for(var idx=0; idx<error.length; idx++){
								$('.modal-text-add').append('<div class="error-msz">'+error[idx].description+'</div>');
								if(error[idx].id == 4016){
									$('.add-login-id').addClass('redBorder');
								}else if(error[idx].id == 4004){	
									$('.password').addClass('redBorder');
								}
							}
						}
					}
				});
			}
			
		}else{
			$('.modal-text-add').append('<div class="error-msz">All fileds are required.</div>');
		}
	});
	
	//Open Admin user to edit the changes in modal.
	$(document).on('click', '#edit-icon', function () {
		editAdminId = $(this).parent().parent().find('.user-id').attr('user_id');
		editAdminLoginId = $(this).parent().parent().find('.user-id').text().trim();
		var editAdminName = $(this).parent().parent().find('.user-name').text().trim();
		var editAdminAccess= $(this).parent().parent().find('.user-access').text().trim();
		editAdminActive= $(this).parent().parent().find('.user-active').text();
		$('.edit-admin-active').val(editAdminActive);
		//$('.modal-login-id').val(userName);
		$('.modal-user-name').val(editAdminName);
		$('.modal-acess-level').val(editAdminAccess);
		$('.error-msz').remove();
	});
	
	//Edit user changes.
	$(document).on('click', '#admin-update-button', function () {
		var dataTempObj = {};
		id = editAdminId;
		loginId = editAdminLoginId;
		$('.error-msz').remove();
	    name = $('.modal-user-name').val().trim();
		accessLevel = $('.edit-access-level option:selected').text().trim();
		isActive = $('.edit-admin-active option:selected').text().trim();
		if(isActive == "Yes"){
			isActive = true
		}else{
			isActive = false
		}
		if(name && accessLevel){
			dataTempObj.loginId = editAdminLoginId;
			dataTempObj.id = editAdminId;
			dataTempObj.name = name;
			dataTempObj.accessLevel = accessLevel;
			dataTempObj.isActive = isActive;
			newObj = Object.toJSON(dataTempObj);
			//validations();
			$.editAdminUser(newObj,{
				success: function (data) {
					if(dataTempObj.isActive == true){
						dataTempObj.isActive = "Yes"
					}else{
						dataTempObj.isActive = "No"
					}
					$('#editAdminModal').foundation('reveal', 'close');
					$(".user_"+id).html(addAdminUsersTemplate.evaluate(dataTempObj));
				}
			});
		}else{
			$('.modal-text-add').append('<div class="error-msz">All fileds are required.</div>');
		}
	});
	
	
	var allValidations = true
	var validations = function(){
        var loginVal = $('.add-login-id').val().trim();
        var loginId = new RegExp('\\s');
        if (!loginId.test(loginVal)) {
            $('#addUser-add-button').css('pointer-events','');
        	$('.error-msz1').addClass('hide');
            allValidations = true;
        }else{
        	$('#addUser-add-button').css('pointer-events','none');
            $('.error-msz1').removeClass('hide');
        	allValidations = false;
        }
	};
	
	//Default first time pagination.
	var firstPagination = function(newObj){
		sortObj = Object.toJSON(newObj);
		$(".add-users-div").append('<img src="/data-api/images/loading.gif" class="loading-icon">');
		$.renderPaginationUsers(sortObj, startPage, pageSize,{
			success: function (data) {
				if(data != null && data.results != null && data.results != undefined){
					$(".add-users-div").html('');
					for(var idx=0; idx<data.results.length; idx++){
						noOfPages = data.totalNumberOfPages;
						totalNumberOfResults = data.totalNumberOfResults;
						if(data.results[idx].isActive == true){
							data.results[idx].isActive = "Yes"
						}else{
							data.results[idx].isActive = "No"
						}
						$(".add-users-div").append(addAdminUsersTemplate.evaluate(data.results[idx]));
						loginRoles();
					}
				}else{
					$(".add-users-div").html('<div class="text-center admin-dashboard marginTop50">No Admin Users.</div>');;
				}
				if(totalNumberOfResults > pageSize){
					pagination(noOfPages);
				}else{
					$('.pagination').remove();
				}
			}
		});
	};
	
	pagination = function(noOfPages) {
		//Pagination
		$('#adminPagSrch').bootpag({
			total: noOfPages, //number of pages
			page: 1,  //page to show on start
			maxVisible: 5 //maximum number of visible pages
		});
	};
	
	//To trigger next num pagination.
	$('#adminPagSrch').on("page", function(event, pageNum){ //To triggering click on pages
		if (pageNum != null && pageNum != undefined) {
			$(".add-users-div").html('');
			var SortCriteria = {};
			$.each($('.sort-filed'), function(){
				if($(this).attr('clicked') == 'yes'){
					sorting = $(this).attr('filter');
					sortType = $(this).attr('sort_type');
					SortCriteria.sortingField = sortType;
					SortCriteria.sortOrder = sorting;
				}
	        });
			displayPaginationUsers(SortCriteria, pageNum, pageSize);
		}
	});
	
	//For pagination.
	var displayPaginationUsers = function(newObj, pageNum, pageSize){
		sortObj = Object.toJSON(newObj);
		$(".add-users-div").append('<img src="/data-api/images/loading.gif" class="loading-icon">');
		$.renderPaginationUsers(sortObj, pageNum, pageSize,{
			success: function (data) {
				if(data != null && data.results != null && data.results != undefined){
					$(".add-users-div").html('');
					for(var idx=0; idx<data.results.length; idx++){
						noOfPages = data.totalNumberOfPages;
						totalNumberOfResults = data.totalNumberOfResults;
						if(data.results[idx].isActive == true){
							data.results[idx].isActive = "Yes"
						}else{
							data.results[idx].isActive = "No"
						}
						$(".add-users-div").append(addAdminUsersTemplate.evaluate(data.results[idx]));
						loginRoles();
					}
				}
			}
		});
	}
	
	//To render admin profile data
	if($('#adminProfile').hasClass('admin-user-details')){
		$("#adminProfile").append('<img src="/data-api/images/loading.gif" class="loading-icon">');
		$.getAdminDeatils({
			success: function (data) {
				if(data != null){
					var loginId = data.loginId;
					var name = data.name;
					var accessLevel= data.accessLevel;
					var isActive= data.isActive;
					var id= data.id;
					if(data.isActive == true){
						data.isActive = "Yes"
					}else{
						data.isActive = "No"
					}
					$('.loading-icon').remove();
					$("#adminProfile").append(renderAdminProfile.evaluate(data));
					
				}
			}
		});
	}else{
		var newObj = {}
		firstPagination(newObj);
	}
	
	//To update password in admin page.
	$(document).on('click', '#pwd-update-button', function () {
		var dataTempObj = {};
		var id= $('.adminId').attr('data_id');
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
			$.changePassword(newObj,{
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
	
	$(document).on('keyup', '.add-login-id', function () {
		$('.add-login-id').removeClass('redBorder');
		$('#addUser-add-button').css('pointer-events','');
    	$('.error-msz1').addClass('hide');
    	allValidations = true;
    });
	
	//Sorting in admin users page.
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
		$(".add-users-div").html('');
		newObj.sortingField = sortType;
		newObj.sortOrder = sorting;
		firstPagination(newObj);
	});

})(jQuery);