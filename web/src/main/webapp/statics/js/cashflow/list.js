myApp.onPageInit('cashflow.index', function(page) {
			cashflow_index_stats();
		});

function cashflow_index_stats() {
	$$.get(appUrl + '/cashflow/stats.htm', {
				type : 'detail'
			}, function(data) {
				var stats = data.split("&");
				$$('#cashflow/index/a').html(stats[0]);
				$$('#cashflow/index/c').html(stats[1]);
				$$('#cashflow/index/b').html(stats[2]);
				$$('#cashflow/index/curBal').html(stats[3]);
			});
}