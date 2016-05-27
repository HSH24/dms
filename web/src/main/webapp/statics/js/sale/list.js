myApp.onPageInit('sale.list', function(page) {
			sale_list_stats();
		});

function sale_list_stats() {
	$$.get(appUrl + '/sale/stats.htm', {}, function(data) {
				var stats = data.split("&");
				$$('#sale/list/a').html(stats[0]);
				$$('#sale/list/b').html(stats[1]);
			});
}