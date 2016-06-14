// Initialize your app
var myApp = new Framework7({
			animateNavBackIcon : true,
			// animatePages : Framework7.prototype.device.ios,
			pushState : true,
			swipePanel : 'left',
			modalButtonOk : '确认',
			modalButtonCancel : '取消',
			imagesLazyLoadPlaceholder : imgUrl + '/image/loading.png',
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

var portal_index_op;

$$('form.ajax-submit.portal-index-login-form').on('beforeSubmit', function(e) {
		});

$$('form.ajax-submit.portal-index-forgetPassword-form').on('beforeSubmit',
		function(e) {
		});

$$('form.ajax-submit.portal-index-login-form').on('submitted', function(e) {
			myApp.hideIndicator();
			var xhr = e.detail.xhr;

			top.location.href = appUrl + "/user/shop.htm";
		});

$$('form.ajax-submit.portal-index-forgetPassword-form').on('submitted',
		function(e) {
			myApp.hideIndicator();
			var xhr = e.detail.xhr;

			mainView.router.load({
						url : appUrl + "/user/setPassword.htm"
					});
		});

$$('form.ajax-submit.portal-index-login-form').on('submitError', function(e) {
			myApp.hideIndicator();
			var xhr = e.detail.xhr;
			myApp.alert(xhr.responseText, '错误');
		});

$$('form.ajax-submit.portal-index-forgetPassword-form').on('submitError',
		function(e) {
			myApp.hideIndicator();
			var xhr = e.detail.xhr;
			myApp.alert(xhr.responseText, '错误');
		});

function submit() {
	setPassportCookies();

	myApp.showIndicator();

	portal_index_op = "login";
	$$('#portal/index/login/form').attr("action", appUrl + "/login.htm");

	$$('#portal/index/login/form').trigger("submit");
}

function more() {
	var buttons = [{
				text : '帮助中心',
				onClick : function() {

				}
			}, {
				text : '取消'
			}];
	myApp.actions(buttons);
}

// forgetPassword

function forgetPassword() {
	$('#portal_index_forgetPassword_passport').val($('#portal_index_passport')
			.val());

	myApp.popup('.popup-forgetPassword');

	setPassportCookies();
}

function sendCheckCode() {
	if (disabled) {
		return;
	}

	$('#portal_index_forgetPassword_checkCode').val('');

	$.ajax({
				type : "post",
				url : appUrl + "/user/sendCheckCode.htm",
				data : {
					"passport" : $('#portal_index_forgetPassword_passport')
							.val(),
					dateTime : new Date().getTime()
				},
				beforeSend : function() {
					startTimer();
				},
				success : function(data) {

				},
				error : function(data) {
					myApp.alert(data.responseText, '错误');
					stopTimer();
				}
			});
}

function validate() {
	myApp.showIndicator();

	portal_index_op = "forgetPassword";
	$$('#portal/index/forgetPassword/form').attr("action",
			appUrl + "/user/validateCheckCode.htm");

	$$('#portal/index/forgetPassword/form').trigger("submit");
}

var timer;
var sendBtn = $('#sendBtn');
var disabled = false;

function stopTimer() {
	clearTimeout(timer);
	disabled = false;
	sendBtn.text('点此获取');
}

function startTimer() {
	disabled = true;
	btnCountdown(sendBtn, 60, function() {
				sendBtn.text('再次获取');
				disabled = false;
			});
}

function btnCountdown(obj, second, callback) {
	$(obj).text(second + '秒');
	if (--second >= 0) {
		timer = setTimeout(function() {
					btnCountdown(obj, second, callback);
				}, 1000);
	} else {
		callback();
	}
}

// base

var __last_login_passport__ = "__last_login_passport__";

function setPassport() {
	var lastuser = $.cookie(__last_login_passport__);

	if (lastuser != null && lastuser != "" && lastuser != "null") {
		document.forms[0].passport.value = lastuser;
		document.forms[0].password.focus();
	} else {
		document.forms[0].passport.focus();
		document.forms[0].passport.select();
	}
}

function setPassportCookies() {
	$.cookie(__last_login_passport__, null);

	var passport = document.forms[0].passport.value;

	$.cookie(__last_login_passport__, passport, {
				expires : 30,
				path : '/' + appName + '/',
				domain : domain
			});

	// _gaq.push(['_trackEvent', 'Home', 'Login', passport]);
}