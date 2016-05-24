myApp.onPageInit('user.setPassword', function(page) {
		});

function user_detail_password() {
	myApp.modalPassword('为保障你的数据安全，修改密码前请填写原密码', '验证原密码', function(value) {
				$$.ajax({
							url : appUrl + '/user/validatePassword.htm',
							data : {
								password : value
							},
							success : function(data, status, xhr) {
								view5.router.load({
											url : appUrl
													+ "/user/setPassword.htm"
										});
							},
							error : function(xhr, status) {
								myApp.alert(xhr.responseText, '错误');
							}
						})
			});
}