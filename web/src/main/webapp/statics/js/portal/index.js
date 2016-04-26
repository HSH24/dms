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

function submit() {
	myApp.showIndicator();

	setPassportCookies();
	window.document.forms[0].submit();
}