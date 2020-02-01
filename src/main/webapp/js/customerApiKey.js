(function($) {
	
	renderApiKeyTemplate = new Template(['<div class="api-details-grid"><div class="small-12 large-2 medium-2 columns text-center"><input type="checkbox" class="checkbox" id="#{id}"></div>',
	                                     	'<div class="small-12 large-5 medium-5 columns text api-long-text text-center">#{apiKey}</div><div class="small-12 large-1 medium-1 columns text text-center">#{isActive}</div>',
	                                     	'<div class="small-12 large-3 medium-3 columns paddingLeft30 text left text-center">#{disabledAtStr}</div>',
	                                     	'<div class="small-12 large-1 medium-1 columns disable"></div></div>'].join(''));
	
	var id = $('#logout').attr('login_id');
	$.getCustomerApiKeys(id,{
		success: function (data) {
			if(data.length >=1){
				for(idx=0; idx<data.length; idx++){
					$('.api-grid').removeClass('hide');
					$('.api-radio').removeAttr('checked');
					data[idx].checked = "checked";
					if(data[idx].isActive == true){
						data[idx].isActive = "Yes"
					}else{
						data[idx].isActive = "No"
					}
					if(data[idx].disabledAtStr == undefined){
						data[idx].disabledAtStr = "NA"
					}
					$(".api-key-details").append(renderApiKeyTemplate.evaluate(data[idx]));
				}
			}else{
				$(".api-key-details").append('<div class="marginTop20 no-api-key">No API keys.</div>');
			}
		}
	});
	
	$(document).on('click', '#api-key-icon', function () {
		$('.api-grid').removeClass('hide');
		$.renderAPIKey({
			success: function (data) {
				if(data != null){
					$('.no-api-key').remove();
					var postObj = {}, newobj = {};
					var customerId = $('#logout').attr('login_id');
					postObj.apiKey = data.apiKey;
					postObj.customerId = customerId;
					newObj = Object.toJSON(postObj);
					$.saveAPIKey(newObj,{
						success: function (data) {
							if(data.isActive == true){
								data.isActive = "Yes"
							}else{
								data.isActive = "No"
							}
							if(data.disabledAtStr == undefined){
								data.disabledAtStr = "NA"
							}
							$(".api-key-details").prepend(renderApiKeyTemplate.evaluate(data));
						}
					});
				}else{
					$(".api-key-details").append('<div>No API key Generated</div>');
				}
			}
		});
	});
	
	$(document).on('click', '#deactivate-api-key', function () {
		var selected = [];
		  $('.checkbox').each(function() { 
              if(this.checked){
            	  var id = $(this).attr('id');
            	  selected.push(parseInt(id));
              }
          });
		  var customerId = $('#logout').attr('login_id');
		  customerId = parseInt(customerId);
		  var apiObj = {}, newApiObj = {};
		  apiObj = selected;
		  newApiObj = Object.toJSON(apiObj);
		  if(selected.length >=1){
			  $.deleteCustomerApiKeys(customerId,newApiObj,{
					success: function (data) {
						$('.selectall, .checkbox').attr('checked',false);
						location.reload();
					}
			  });
		  }else{
			  alert('Please select atleast one API key to deactivate.');
		  }
		  
	});
	
	$('.api-key-details').slimScroll({
        railVisible: true,
        alwaysVisible: false,
        size: '5px',
        railColor: '#ffffff',
        height: '200px',
        wheelStep: 2,
        distance: '0px'
	});
	
	
})(jQuery);