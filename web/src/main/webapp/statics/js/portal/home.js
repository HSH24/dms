// Initialize your app
var myApp = new Framework7({
			animateNavBackIcon : true,
			animatePages : Framework7.prototype.device.ios,
			swipePanel : 'left',
			// Hide and show indicator during ajax requests
			onAjaxStart : function(xhr) {
				myApp.showIndicator();
			},
			onAjaxComplete : function(xhr) {
				myApp.hideIndicator();
			}
		});

// Export selectors engine
var $$ = Dom7;

// Add view
var mainView = myApp.addView('.view-main', {
			// Because we use fixed-through navbar we can enable dynamic navbar
			dynamicNavbar : true
		});

// ==============================

// 基于准备好的dom，初始化echarts实例
var myChart = echarts.init(document.getElementById('main'));

// 指定图表的配置项和数据
var option = {
	radar : {
		indicator : [{
					name : '剩余预付款',
					max : 20000
				}, {
					name : '销售金额',
					max : 1000
				}, {
					name : '派送订单',
					max : 100
				}, {
					name : '平均库存周转',
					max : 7
				}, {
					name : '采购订单',
					max : 100
				}]
	},
	series : [{
				type : 'radar',
				label : {
					normal : {
						show : true
					}
				},
				data : [{
							value : [10000, 100, 50, 3, 30]
						}]
			}],
	backgroundColor : '#FFFFFF'
};

// 使用刚指定的配置项和数据显示图表。
myChart.setOption(option);

// ==============================

var view2 = myApp.addView('#view-2', {
			dynamicNavbar : true
		});
$$('#href-2').on('click', function() {
			if (view2.history.length == 1) {
				view2.router.load({
							url : appUrl + "/mall/list.htm",
							ignoreCache : true,
							reload : true
						});
			}
		});

var view3 = myApp.addView('#view-3', {
			dynamicNavbar : true
		});
$$('#href-3').on('click', function() {
			if (view3.history.length == 1) {
				view3.router.load({
							url : appUrl + "/item/list.htm"
						});
			}
		});

var view4 = myApp.addView('#view-4', {
			dynamicNavbar : true
		});
$$('#href-4').on('click', function() {
			if (view4.history.length == 1) {
				view4.router.load({
							url : appUrl + "/cart/index.htm",
							ignoreCache : true,
							reload : true
						});
			}
		});

var view5 = myApp.addView('#view-5', {
			dynamicNavbar : true
		});
$$('#href-5').on('click', function() {
			if (view5.history.length == 1) {
				view5.router.load({
							url : appUrl + "/member/index.htm"
						});
			}
		});

$$('#view_2_click').on('click', function() {
			if (view2.history.length == 1) {
				view2.router.load({
							url : appUrl + "/mall/list.htm",
							ignoreCache : true,
							reload : true
						});
			}

			$$('#href-2').addClass("active");
		});

$$('#view_3_click').on('click', function() {
			if (view3.history.length == 1) {
				view3.router.load({
							url : appUrl + "/item/list.htm"
						});
			}

			$$('#href-3').addClass("active");
		});

$$('#view_5_click').on('click', function() {
			if (view5.history.length == 1) {
				view5.router.load({
							url : appUrl + "/member/index.htm"
						});
			}

			$$('#href-5').addClass("active");
		});

$$('#view_6_click').on('click', function() {
			if (view5.history.length == 1) {
				view5.router.load({
							url : appUrl + "/member/index.htm"
						});
			}

			$$('#href-5').addClass("active");
		});

function portal_home_cart_stats() {
	$$.get(appUrl + '/cart/stats.htm', {}, function(data) {
				if (data > 0) {
					$$('#portal/home/cart').addClass('badge bg-red');
					$$('#portal/home/cart').html(data);
				} else {
					$$('#portal/home/cart').removeClass('badge bg-red');
					$$('#portal/home/cart').html('');
				}
			});
}

portal_home_cart_stats();
