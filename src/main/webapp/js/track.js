(function($) {
	
	var pageSize=10,noOfPages='',totalNumberOfResults='',startPage=1;
	
	renderAllTracksTemplate = new Template(['<div class="track-details-grid"><div class="small-2 large-2 medium-2 columns text paddoingLeft40">#{id}</div>',
	                                        	'<div class="small-2 large-2 medium-2 columns text paddoingLeft40">#{country}</div>',
	                                        	'<div class="small-2 large-2 medium-2 columns text paddoingLeft30">#{type}</div>',
	                                        	'<div class="small-6 large-6 medium-6 columns text paddoingLeft20">#{name}</div></div>'].join(''));
	
	
	var firstPagination = function(){
		$(".add-tracks-div").append('<img src="/data-api/images/loading.gif" class="loading-icon">');
		$.renderPaginationTracks(startPage, pageSize,{
			success: function (data) {
				if(data != null && data.results != null){
					$(".add-tracks-div").html('');
					for(var idx=0; idx<data.results.length; idx++){
						noOfPages = data.totalNumberOfPages;
						totalNumberOfResults = data.totalNumberOfResults;
						$(".add-tracks-div").append(renderAllTracksTemplate.evaluate(data.results[idx]));
					}
				}else{
					$(".add-tracks-div").html('<div class="text-center marginTop50 admin-dashboard">No Tracks</div>');
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
		$('#trackPagSrch').bootpag({
			total: noOfPages, //number of pages
			page: 1,  //page to show on start
			maxVisible: 5 //maximum number of visible pages
		}).on("#trackPagSrch page", function(event, pageNum){ //To triggering click on pages
			if (pageNum != null && pageNum != undefined) {
				$(".add-tracks-div").html('');
				var postObj = {}, newObj = {};
				var pageNumber = pageNum;
				var sortingField = null;
				var sortOrder = null;
				$('body').delegate('each', '.sort-track', function () {
					sortOrder = $(this).attr('filter');
					if(sortOrder == "asc"){
						sortOrder = "asc";
						sortingField = $(this).attr('sort_type');
					}
				});
				
				postObj.pageNumber = pageNumber;
				postObj.pageSize = pageSize;
				postObj.sortingField = sortingField;
				postObj.sortOrder = sortOrder;
				newObj = Object.toJSON(postObj);
				displayPaginationTracks(newObj);
			}
		});
	};
	
	displayPaginationTracks = function(newObj){
		$.renderSortTracks(newObj,{
			success: function (data) {
				if(data != null && data.results != null){
					$(".add-tracks-div").html('');
					for(var idx=0; idx<data.results.length; idx++){
						noOfPages = data.totalNumberOfPages;
						totalNumberOfResults = data.totalNumberOfResults;
						$(".add-tracks-div").append(renderAllTracksTemplate.evaluate(data.results[idx]));
					}
				}
			}
		});
	}
	
	/*$(document).on('click', '.sort-track', function () {
		var sorting = $(this).attr('filter');
		var sortType = $(this).attr('sort_type');
		if(sorting == "desc"){
			sorting = "asc";
			$('.sort-track').attr('filter','desc')
			$(this).attr('filter','asc')
			$('.sort-track').find('img.down-arrow').removeClass('hide');
			$('.sort-track').find('img.up-arrow').addClass('hide');
			$(this).find('img.down-arrow').addClass('hide');
			$(this).find('img.up-arrow').removeClass('hide');
		}else{
			sorting = "desc";
			$('.sort-track').attr('filter','asc')
			$(this).attr('filter','desc')
			$('.sort-track').find('img.down-arrow').removeClass('hide');
			$('.sort-track').find('img.up-arrow').addClass('hide');
			$(this).find('img.down-arrow').removeClass('hide');
			$(this).find('img.up-arrow').addClass('hide');
		}
		//$.renderSortingTracks(sortType, sorting,{
			//success: function (data) {
				//if(data != null){
					//$(".add-tracks-div").html('');
					//for(var idx=0; idx<data.length; idx++){
						//$(".add-tracks-div").append(renderAllTracksTemplate.evaluate(data[idx]));
					//}
				//}
			//}
		//});
		if(!$('#trackPagSrch').find('ul.pagination li:first-child').next().hasClass('active')){
			firstPagination();
		}else{
			$(".add-tracks-div").html('');
			var postObj = {}, newObj = {};
			var pageNumber = startPage;
			var sortingField = sortType;
			var sortOrder = sorting;
			postObj.pageNumber = pageNumber;
			postObj.pageSize = pageSize;
			postObj.sortingField = sortingField;
			postObj.sortOrder = sortOrder;
			newObj = Object.toJSON(postObj);
			displayPaginationTracks(newObj);
		}
		
	});*/
})(jQuery);