myApp.onPageInit('item.list', function(page) {
			$$('form.ajax-submit').on('beforeSubmit', function(e) {
					});

			$$('form.ajax-submit').on('submitted', function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '信息', function() {
									// 更新首页购物车标记
									// portal_home_cart_stats();
								});
					});

			$$('form.ajax-submit').on('submitError', function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});
		});

function item_list_trade(itemId, skuId) {
	myApp.modal({
		title : '特仑苏纯牛奶利乐苗条装',
		text : '￥10',
		afterText : '<span>采购数量：<select><option>1</option><option>2</option><option>3</option></select></span>',
		buttons : [{
					text : '取消',
					onClick : function() {
					}
				}, {
					text : '确认下单',
					onClick : function() {
						myApp.showIndicator();

						$$('#item_list_trade_itemId').val(itemId);
						$$('#item_list_trade_skuId').val(skuId);
						$$('#item/list/trade').trigger("submit");
					}
				}]
	});
}

function item_list_cart(itemId, skuId) {
	myApp.showIndicator();

	$$('#item_list_cart_itemId').val(itemId);
	$$('#item_list_cart_skuId').val(skuId);
	$$('#item/list/cart').trigger("submit");
}