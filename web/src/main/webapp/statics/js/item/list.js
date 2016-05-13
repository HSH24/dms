myApp.onPageInit('item.list', function(page) {
			$$('form.ajax-submit').on('beforeSubmit', function(e) {
					});

			$$('form.ajax-submit').on('submitted', function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '信息', function() {
									// 更新首页购物车标记
									portal_home_cart_stats();
								});
					});

			$$('form.ajax-submit').on('submitError', function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
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
					item_list_trade("1", "1");
				}
			});
}

function scanFinished(str) {
	alert(str);
}

function item_list_trade(itemId, skuId, title, text) {
	myApp.modal({
		title : title,
		text : text,
		afterText : '<div class="list-block" style="margin: 0">'
				+ '<div class="item-content">'
				+ '<div class="item-inner page-settings">'
				+ '<div class="item-title label" style="width: 50%;">采购数量：</div>'
				+ '<div class="item-input">'
				+ '<select><option>1</option></select>' + '</div>' + '</div>'
				+ '</div>' + '</div>',
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
						$$('#item_list_trade_quantity').val("1");
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