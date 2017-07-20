define(['durandal/app','knockout','../../template/modal/modal'], function (app, ko, myModal) {
	
	var Home = function() {	};
	
	Home.prototype.showActivityModal = function () {
		//Opens a Modal
		myModal.show("Patient Activity", ["Close"], true, { "class": "messageBox2" }, "../viewmodels/home/patientActivity");
	};
	
	return Home;
});