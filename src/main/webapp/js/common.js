(function($) {
	
	/*//Click function for logout.
	$(document).on('click', '#logout', function () {
		$.logout({
			success: function (data) {
				location.href = 'data-api/login.jsp';
			}
		});
	});*/
	
	//Click function for check box check all and uncheck all.
	$(document).on('click', '.selectall', function () {
        if(this.checked) { 
            $('.checkbox').each(function() { 
                this.checked = true; 
            });
            $(this).attr('clicked','yes');
        }else{
            $('.checkbox').each(function() { 
                this.checked = false; 
            });
            $(this).attr('clicked','no');
        }
    });
	
	$(document).on('click', '.api-radio', function () {
		$('.api-radio').each(function() { 
			$(this).prop('checked', false);
			$(this).attr('clicked','no');
        });
		$(this).prop('checked', true);
		$(this).attr('clicked','yes');
    });
	
	$(document).on('change', '.checkbox', function () {
        $.each($('.checkbox'), function(){
            if(!$(this).find('input').is(':checked')){
            	 $('.selectall').attr('checked',false);
            }
        }); 
    });
	
	var height = $(window).height();
	height = height -300;
	if($('#logout').attr('login_role') != "CUSTOMER"){
		$('.container').css('min-height',height);
	}
	
	
	if(window.location.href.indexOf("loginFailure") >= 0){
		$('.invalid-error').removeClass('hide');
	}
	
	$(document).on('click', '#change-pwd', function () {
		$('#change-pwd-div').removeClass('hide');
		$('.text-field').css('border-color','#437aba');
		if(!($('#addCustomer').text() == "Edit customer")){
			$('.error-msz').addClass('hide');
		}
		
	});
	
	$(document).on('click', '#pwd-cancel-button', function () {
		$('#change-pwd-div').addClass('hide');
		$('.text-field').val('');
		$('.pwd-success').addClass('hide');
	});
	
	$(document).on('keyup', '.text-field', function () {
		$('.pwd-success').addClass('hide');
		$('.error-msz').addClass('hide');
		$('.error-msz1').addClass('hide');
		$('#addUser-add-button').css('pointer-events','');
		$(this).css('border-color','#437aba');
		$('.pwd-rules').css({color:'#7F7F7F', border:'1px solid #427ABA'});
	});
	
	$(document).on('keyup', '.login-error', function () {
		$('.invalid-error').addClass('hide');
	});
	
})(jQuery);