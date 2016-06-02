myApp.onPageInit('sale.list', function(page) {
	myApp.picker({
				input : '#sale/list/date',
				toolbarCloseText : '完成',
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
					p.setValue([$$('#sale/list/year').val(),
							$$('#sale/list/month').val()]);
				},
				onClose : function(p) {
					mainView.router.reloadPage(appUrl + '/sale/list.htm?year='
							+ p.value[0] + '&month=' + p.value[1])
				}
			});

	sale_list_stats();
});

function sale_list_stats() {
	$$.get(appUrl + '/sale/stats.htm', {}, function(data) {
				var stats = data.split("&");
				$$('#sale/list/a').html(stats[0]);
				$$('#sale/list/b').html(stats[1]);
			});
}