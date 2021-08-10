document.onkeydown = function(event) {
	e = event ? event : (window.event ? window.event : null);
	// if (e.keyCode == 13) {
	// 	return false
	// }
};
/*window.onresize = function() {
	var oVm = document.getElementById('vm')
	var cliWidth = document.body.clientWidth;
	var cliHeight = document.body.clientHeight;
	var divWidth = cliWidth - 2;
	var divHeight = cliHeight - 2;
	oVm.style.width = divWidth + "px";
	oVm.style.height = divHeight + "px";
};*/