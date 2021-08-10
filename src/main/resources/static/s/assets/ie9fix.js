(window.navigator.userAgent.toLowerCase().indexOf('msie 9.0') > 0) && (typeof Vue !== 'undefined') && (function() {
	document.addEventListener('keyup', function() {
		var el = document.activeElement;
		if (el) {
			var tagName = (el.tagName || '').toLowerCase(), keyCode = window.event.keyCode;
			if ((keyCode == 8/* backspace */|| keyCode == 46/* delete */) && (tagName == 'input' || tagName == 'textarea')) {
				var e = document.createEvent('HTMLEvents');
				e.initEvent('input', true, true);
				el.dispatchEvent(e);
			}
		}
	});
})();