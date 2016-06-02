myApp.onPageInit('member.index', function(page) {
			member_index_stats('0');
		});

function member_index_stats(type) {
	if (type == '1' || type == '0') {
		$$.get(appUrl + '/trade/stats.htm', {}, function(data) {
					var stats = data.split("&");
					$$('#member/index/tosend').html(stats[1]);
					$$('#member/index/send').html(stats[2]);
					$$('#member/index/sign').html(stats[3]);
				});
	}

	if (type == '2' || type == '0') {
		$$.get(appUrl + '/cashflow/stats.htm', {}, function(data) {
					var stats = data.split("&");
					$$('#member/index/crAmount').html(stats[0]);
					$$('#member/index/curBal').html(stats[1]);
				});
	}
}