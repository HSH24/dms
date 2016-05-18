myApp.onPageInit('user.setPassword', function(page) {
			$$('form.ajax-submit').on('beforeSubmit', function(e) {
					});

			$$('form.ajax-submit').on('submitted', function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
					});

			$$('form.ajax-submit').on('submitError', function(e) {
						myApp.hideIndicator();
						var xhr = e.detail.xhr;
						myApp.alert(xhr.responseText, '错误');
					});
		});

function user_detail_password() {
	myApp.prompt('为保障你的数据安全，修改密码前请填写原密码。', '验证原密码', function(value) {
				$$.get(appUrl + '/user/validatePassword.htm', {},
						function(data) {
							alert(data);
						});
			});
}