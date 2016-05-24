myApp.onPageInit('sale.list', function(page) {
			sale_list_stats();
		});

function sale_list_stats() {
	$$.get(appUrl + '/sale/stats.htm', {}, function(data) {
				var stats = data.split("&");
				$$('#sale/index/a').html(stats[0]);
				$$('#sale/index/b').html(stats[1]);
			});
}