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

$$('form.ajax-submit').on('beforeSubmit', function(e) {
		});

$$('form.ajax-submit').on('submitted', function(e) {
			myApp.hideIndicator();
			var xhr = e.detail.xhr;

			mainView.router.load({
						url : appUrl + "/account/setPassword.htm"
					});
		});

$$('form.ajax-submit').on('submitError', function(e) {
			myApp.hideIndicator();
			var xhr = e.detail.xhr;
			myApp.alert(xhr.responseText, '错误');
		});

function submit() {
	myApp.showIndicator();

	setPassportCookies();
	window.document.forms[0].submit();
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
}

function sendCheckCode() {
	if (disabled) {
		return;
	}

	$.ajax({
				type : "post",
				url : appUrl + "/account/sendCheckCode.htm",
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

	$$('#portal/forgetPassword/form').attr("action",
			appUrl + "/account/validateCheckCode.htm");

	$$('#portal/forgetPassword/form').trigger("submit");
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