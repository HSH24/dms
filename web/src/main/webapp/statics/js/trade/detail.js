myApp.onPageInit('trade.detail', function(page) {
			$$('form.ajax-submit.trade-detail-form').on('beforeSubmit',
					function(e) {
					});

			$$('form.ajax-submit.trade-detail-form').on('submitted',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '信息');
					});

			$$('form.ajax-submit.trade-detail-form').on('submitError',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});
		});

function trade_detail_receipt() {
	myApp.showIndicator();

	var params = [];

	$$("input[name='trade/detail/orderId']").each(function(e) {
				var orderId = this.value;
				params.push({
							"orderId" : orderId,
							"quantity" : $$('#trade/detail/quantity/' + orderId)
									.val()
						});
			});

	$$('#trade/detail/form/receiptDetailList').val(JSON.stringify(params));

	$$('#trade/detail/form').trigger("submit");
}

function trade_detail_minus(orderId) {
	var q = $$('#trade/detail/quantity/' + orderId).val();

	if (q == 0) {
		return;
	}

	trade_detail_num(orderId, dcmSub(q, 1));
}

function trade_detail_plus(orderId) {
	var q = $$('#trade/detail/quantity/' + orderId).val();
	trade_detail_num(orderId, dcmAdd(q, 1));
}

function trade_detail_num(orderId, quantity) {
	$$('#trade/detail/quantity/' + orderId).val(quantity);
	// $$('#trade/detail/quantity/edited/' + orderId).html('×' + data);
}

function trade_detail_edit() {
	$$('#trade/detail/edit').hide();
	$$('#trade/detail/edited').show();

	$$('div[id^="trade/detail/quantity/edited"]').hide();
	$$('div[id="trade/detail/quantity/edit"]').show();

	$$('#trade/detail/item/price').hide();
	$$('#trade/detail/media/2').hide();

	$$('#trade/detail/btn/receipt').show();
}

function trade_detail_edited() {
	$$('#trade/detail/edited').hide();
	$$('#trade/detail/edit').show();

	$$('div[id="trade/detail/quantity/edit"]').hide();
	$$('div[id^="trade/detail/quantity/edited"]').show();

	$$('#trade/detail/item/price').show();
	$$('#trade/detail/media/2').show();

	$$('#trade/detail/btn/receipt').hide();
}