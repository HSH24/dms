myApp.onPageInit('cashflow.list', function(page) {
			cashflow_list_stats();
		});

function cashflow_list_stats() {
	$$.get(appUrl + '/cashflow/stats.htm', {
				type : 'detail'
			}, function(data) {
				var stats = data.split("&");
				$$('#cashflow/list/a').html(stats[0]);
				$$('#cashflow/list/c').html(stats[1]);
				$$('#cashflow/list/b').html(stats[2]);
				$$('#cashflow/list/curBal').html(stats[3]);
			});
}