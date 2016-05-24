myApp.onPageInit('user.setUserName', function(page) {
			$$('form.ajax-submit.user-setUserName-form').on('beforeSubmit',
					function(e) {
					});

			$$('form.ajax-submit.user-setUserName-form').on('submitted',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '信息', function() {
									$$('#user/detail/userName')
											.html($$('#user/setUserName/userName')
													.val());
									view5.router.back();
								});
					});

			$$('form.ajax-submit.user-setPassword-form').on('submitError',
					function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});
		});

function user_setUserName_save() {

	myApp.showIndicator();

	$$('#user/setUserName/form').attr("action",
			appUrl + "/user/updateUserName.htm");

	$$('#user/setUserName/form').trigger("submit");
}