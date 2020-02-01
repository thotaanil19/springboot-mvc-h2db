(function($) {
	
var pageSize=10,noOfPages='',totalNumberOfResults='',startPage=1;
	
	renderAllReportsTemplate = new Template(['<div class="report-details-grid"><div class="small-12 large-2 medium-2 columns text">#{customerId}</div>',
		                    					'<div class="small-12 large-2 medium-2 columns text">#{custProdId}</div>',
		                    					'<div class="small-12 large-2 medium-2 columns text">#{endpoint}</div>',
		                    					'<div class="small-12 large-2 medium-2 columns text">#{timeStampStr}</div>',
		                    					'<div class="small-12 large-4 medium-4 columns text">#{attribute1}</div>',
		                    					'</div>'].join(''));
	
	$(document).on('click', '.search-log-btn', function () {
		var startDate = $('#startDate').val();
		var endDate = $('#endDate').val();
		if(startDate == ''){
			$('#startDate').css('border-color','red');
		}
		if(endDate == ''){
			$('#endDate').css('border-color','red');
		}
		if(startDate != '' && endDate != '') {
			var newObj = {}, postObj = {};
			newObj.fromDateStr = startDate;
			newObj.toDateStr = endDate;
			newObj.pageNumber = startPage;
			newObj.pageSize = pageSize;
			$('#startDate, #endDate').css('border-color','#CCC');
			postObj = Object.toJSON(newObj);
			$.renderPaginationReports(postObj,{
				success: function (data) {
					if(data != null && data.results != null && data.results.length >= 1){
						$(".report-all-details").html('');
						$(".report-grid").removeClass('hide');
						for(var idx=0; idx<data.results.length; idx++){
							noOfPages = data.totalNumberOfPages;
							totalNumberOfResults = data.totalNumberOfResults;
							$(".report-all-details").append(renderAllReportsTemplate.evaluate(data.results[idx]));
						}
					}else{
						$(".report-grid").removeClass('hide');
						$(".report-all-details").html('<div class="text-center marginTop50 admin-dashboard">No Reports</div>');
					}
					if(totalNumberOfResults > pageSize){
						pagination(noOfPages);
					}else{
						$('.pagination').remove();
					}
					
				}
			});
		}
		
	});
	
	pagination = function(noOfPages) {
		//Pagination
		$('#reportPagSrch').bootpag({
			total: noOfPages, //number of pages
			page: 1,  //page to show on start
			maxVisible: 5 //maximum number of visible pages
		}).on("#reportPagSrch page", function(event, pageNum){ //To triggering click on pages
			if (pageNum != null && pageNum != undefined) {
				$(".report-all-details").html('');
				$(".report-grid").removeClass('hide');
				var startDate = $('#startDate').val();
				var endDate = $('#endDate').val();
				var newObj = {}, postObj = {};
				newObj.fromDateStr = startDate;
				newObj.toDateStr = endDate;
				newObj.pageNumber = pageNum;
				newObj.pageSize = pageSize;
				postObj = Object.toJSON(newObj);
				displayPaginationreports(postObj);
			}
		});
	};
	
	displayPaginationreports = function(postObj){
		$.renderPaginationReports(postObj,{
			success: function (data) {
				if(data != null && data.results != null){
					$(".report-all-details").html('');
					for(var idx=0; idx<data.results.length; idx++){
						noOfPages = data.totalNumberOfPages;
						totalNumberOfResults = data.totalNumberOfResults;
						$(".report-all-details").append(renderAllReportsTemplate.evaluate(data.results[idx]));
					}
				}else{
					$(".report-all-details").html('<div class="text-center marginTop50 admin-dashboard">No Reports</div>');
				}
			}
		});
	}
	
	var nowTemp = new Date();
	var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);
	var checkin = $('#dp4').fdatepicker({
		onRender: function (date) {
			return date.valueOf() > now.valueOf() ? 'disabled' : '';
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
	
})(jQuery);