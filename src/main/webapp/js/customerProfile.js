(function($) {
	
	renderCustomerProfile = new Template(['<div class="padding10"><img src="/data-api/images/user-icon.png"><div class="greyColor adminId width160" data_id=#{id}>Customer Login ID</div>',
                                  		'<span class="greyColor">:</span><span class="blueColor">#{loginId}</span></div>',	
                                  		'<div class="padding10"><img src="/data-api/images/user-icon.png"><div class="greyColor width153">Company Name:</div>',
                                  		'<span class="greyColor paddingLeft8">:</span><span class="blueColor">#{companyName}</span></div>',
                                  		'<div class="padding10"><img src="/data-api/images/Email-icon.png"><div class="greyColor width153">Email</div>',
                                  		'<span class="greyColor">:</span><span class="blueColor">#{email}</span></div>',
                                  		'<div class="padding10"><img src="/data-api/images/access-level-icon.png"><div class="greyColor width160">Monthly Base API hit limit:</div>',
                                  		'<span class="greyColor">:</span><span class="blueColor">#{baseAccessLimit}</span></div>',
                                  		'<div class="padding10"><img src="/data-api/images/is-active-icon.png"><div class="greyColor width160">Is Active</div>',
                                  		'<span class="greyColor">:</span><span class="blueColor">#{isActive}</span></div>',
                                  		'<div class="padding10"><img src="/data-api/images/calender-icon.png"><div class="greyColor width160">API hit counter reset day of Month</div>',
                                  		'<span class="greyColor">:</span><span class="blueColor">#{resetDayOfMonth}</span></div>',].join(''));
	
	var id = $('#logout').attr('login_id');
	$.renderCustomerProfile(id,{
		success: function (data) {
			if(data != null){
				if(data.isActive == true){
					data.isActive = "Yes"
				}else{
					data.isActive = "No"
				}
				$("#customerProfile").append(renderCustomerProfile.evaluate(data));
				
			}
		}
	});
	
	$(document).on('click', '#pwd-update-button', function () {
		var dataTempObj = {};
		var id = $('#logout').attr('login_id');
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
					$('.pwd-success').text(data.success).removeClass('hide').fadeOut(5000);
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
	
})(jQuery);