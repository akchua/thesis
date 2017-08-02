define(['durandal/app','knockout','../../template/modal/modal'], function (app, ko, myModal) {
	
	var Home = function() {	};
	
	Home.prototype.showPatientInfo = function () {
		//Opens a Modal
		myModal.show("Patient Information", ["Close"], true, { "class": "messageBox2" }, "../viewmodels/patient/patientInfo");
	};
	
	return Home;
});