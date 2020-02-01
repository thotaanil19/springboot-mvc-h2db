(function($) {
	
	var nowTemp = new Date();
	var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);
	var checkin = $('#dp4').fdatepicker({
		onRender: function (date) {
			return date//.valueOf() < now.valueOf() ? 'disabled' : '';
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
	
	//getCustomerCurrentMonthApiLimits
	
	currentMonth = function(){
		var id = $('#logout').attr('login_id');
		$.getCustomerCurrentMonthApiLimits(id,{
			success: function (data) {
				if(data != null){
					$('.totalHits, .usedHits, .remainingHits').html('');
					var totalHits = data.currentAccessLimit;
					var usedHits = data.currentAccessCount;
					if(totalHits == '' || totalHits == undefined){
						totalHits = 0;
					}
					if(usedHits == '' || usedHits == undefined){
						usedHits = 0;
					}
					if(totalHits != null && usedHits != null){
						var remainingHits = totalHits-usedHits;
					}else{
						remainingHits = 0;
						totalHits = 0;
						usedHits = 0;
					}
					$('.totalHits').text(totalHits);
					$('.usedHits').text(usedHits);
					$('.remainingHits').text(remainingHits);
				}
			}
		});
	};
	
	currentMonth();
	
	$(document).on('click', '.get-current-month-limits', function () {
		currentMonth();
	});
	
	$(document).on('click', '.get-allTime-limits', function () {
		var id = $('#logout').attr('login_id');
		$.getCustomerAllTimeApiLimits(id,{
			success: function (data) {
				if(data != null){
					$('.totalHits, .usedHits, .remainingHits').html('');
					var totalApiHits = data.currentAccessLimit;
					var usedApiHits = data.currentAccessCount;
					if(totalApiHits == '' || totalApiHits == undefined){
						totalApiHits = 0;
					}
					if(usedApiHits == '' || usedApiHits == undefined){
						usedApiHits = 0;
					}
					if(totalApiHits != null && usedApiHits != null){
						var remainingApiHits = totalApiHits-usedApiHits;
					}else{
						remainingApiHits = 0;
						totalApiHits = 0;
						usedApiHits = 0;
					}
					
					$('.totalHits').text(totalApiHits);
					$('.usedHits').text(usedApiHits);
					$('.remainingHits').text(remainingApiHits);
				}
			}
		});
		
    });
	
	$(document).on('click', '.get-limits', function () {
		var checkRadio = $('.date-checker-radio').attr('clicked');
		var id = $('#logout').attr('login_id');
		var startDateStr = $('#startDate').val();
		var endDateStr = $('#endDate').val();
		if(startDateStr  && endDateStr && (checkRadio == "no" || checkRadio == undefined)){
			alert('Please select the radio button.')
		}else{
			$.getCustomerApiLimits(id,startDateStr,endDateStr,{
				success: function (data) {
					if(data != null){
						$('.totalHits, .usedHits, .remainingHits').html('');
						var totalApiHits = data.currentAccessLimit;
						var usedApiHits = data.currentAccessCount;
						if(totalApiHits == '' || totalApiHits == undefined){
							totalApiHits = 0;
						}
						if(usedApiHits == '' || usedApiHits == undefined){
							usedApiHits = 0;
						}
						if(totalApiHits != null && usedApiHits != null){
							var remainingApiHits = totalApiHits-usedApiHits;
						}else{
							remainingApiHits = 0;
							totalApiHits = 0;
							usedApiHits = 0;
						}
						
						$('.totalHits').text(totalApiHits);
						$('.usedHits').text(usedApiHits);
						$('.remainingHits').text(remainingApiHits);
					}
				}
			});
		}
		
    });
	
})(jQuery);