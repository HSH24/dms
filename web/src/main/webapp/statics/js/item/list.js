myApp.onPageInit('item.list', function(page) {
			$$('form.ajax-submit').on('beforeSubmit', function(e) {
					});

			$$('form.ajax-submit').on('submitted', function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '信息', function() {
									if (item_list_flag == "cart") {
										// 更新首页购物车标记
										portal_home_cart_stats();
									}

									if (item_list_flag == "trade") {
										// 更新我的中心资金统计
										member_index_stats();
									}

									item_list_flag = "";
								});
					});

			$$('form.ajax-submit').on('submitError', function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});

			$$('.open-picker').on('click', function() {

					});
			$$('.close-picker').on('click', function() {

					});
		});

function item_list_scan() {
	try {
		window.scaner.scanContent();
	} catch (e) {
		alert(e);
	}

	return;

	wx.scanQRCode({
				needResult : 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
				scanType : ["qrCode", "barCode"], // 可以指定扫二维码还是一维码，默认二者都有
				success : function(res) {
					var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
				}
			});
}

function scanFinished(str) {
	alert(str);
}

var item_list_flag;

function item_list_trade(itemId, skuId) {
	item_list_flag = "trade";

	myApp.showIndicator();

	$$('#item_list_trade_itemId').val(itemId);
	$$('#item_list_trade_skuId').val(skuId);
	$$('#item_list_trade_quantity').val($$('#item/list/quantity/' + itemId
			+ '/' + skuId).val());

	$$('#item/list/trade').trigger("submit");
}

function item_list_cart(itemId, skuId) {
	item_list_flag = "cart";

	myApp.showIndicator();

	$$('#item_list_cart_itemId').val(itemId);
	$$('#item_list_cart_skuId').val(skuId);
	$$('#item_list_cart_quantity').val($$('#item/list/quantity/' + itemId + '/'
			+ skuId).val());

	$$('#item/list/cart').trigger("submit");
}

function item_list_minus(id) {
	var q = $$('#item/list/quantity/' + id).val();

	if (q == 1) {
		return;
	}

	item_list_num(id, dcmSub(q, 1));
}

function item_list_plus(id) {
	var q = $$('#item/list/quantity/' + id).val();
	item_list_num(id, dcmAdd(q, 1));
}

function item_list_num(id, quantity) {
	$$('#item/list/quantity/' + id).val(quantity);
}
