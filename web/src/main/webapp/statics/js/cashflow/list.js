myApp.onPageInit('cashflow.list', function(page) {
	myApp.picker({
				input : '#cashflow/list/date',
				rotateEffect : true,
				formatValue : function(p, values, displayValues) {
					return values[0] + '年' + values[1] + '月';
				},
				cols : [{
							values : ['2015', '2016', '2017'],
							displayValues : ['2015年', '2016年', '2017年'],
							textAlign : 'right',
							width : 1000
						}, {
							values : ['1', '2', '3', '4', '5', '6', '7', '8',
									'9', '10', '11', '12'],
							displayValues : ['1月', '2月', '3月', '4月', '5月',
									'6月', '7月', '8月', '9月', '10月', '11月', '12月'],
							textAlign : 'left',
							width : 1000
						}],
				onOpen : function(p) {
					p.setValue([$$('#cashflow/list/year').val(),
							$$('#cashflow/list/month').val()]);
				},
				onClose : function(p) {
					myApp.getCurrentView().router.reloadPage(appUrl
							+ '/cashflow/list.htm?year=' + p.value[0]
							+ '&month=' + p.value[1]);
				}
			});

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