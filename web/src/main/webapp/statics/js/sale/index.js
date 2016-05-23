myApp.onPageInit('sale.index', function(page) {
			sale_index_stats();
		});

function sale_index_stats() {
	$$.get(appUrl + '/sale/stats.htm', {}, function(data) {
				var stats = data.split("&");
				$$('#sale/index/a').html(stats[0]);
				$$('#sale/index/b').html(stats[1]);
			});
}