myApp.onPageInit('user.setPassword', function(page) {
			$$('form.ajax-submit.user-setPassword-form').on('beforeSubmit',
					function(e) {
					});

			$$('form.ajax-submit.user-setPassword-form').on('submitted',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						top.location.href = appUrl + "/user/shop.htm";
					});

			$$('form.ajax-submit.user-setPassword-form').on('submitError',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});
		});

function updatePassword() {
	var J_Pwd = $$('#J_Pwd').val();
	var J_RePwd = $$('#J_RePwd').val();

	if (J_Pwd != J_RePwd) {
		myApp.alert("两次输入密码不一致。", '错误');
		return;
	}

	myApp.showIndicator();

	$$('#user/setPassword/form').attr("action",
			appUrl + "/user/updatePassword.htm");

	$$('#user/setPassword/form').trigger("submit");
}