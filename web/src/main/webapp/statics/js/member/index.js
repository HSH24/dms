myApp.onPageInit('member.index', function(page) {
			member_index_stats();
		});

function member_index_stats() {
	$$.get(appUrl + '/trade/stats.htm', {}, function(data) {
				var stats = data.split("&");
				$$('#member/index/tosend').html(stats[1]);
				$$('#member/index/send').html(stats[2]);
				$$('#member/index/sign').html(stats[3]);
			});

	$$.get(appUrl + '/cashflow/stats.htm', {}, function(data) {
				var stats = data.split("&");
				$$('#member/index/drAmount').html(stats[0]);
				$$('#member/index/crAmount').html(stats[1]);
				$$('#member/index/curBal').html(stats[2]);
			});
}