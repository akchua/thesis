define(['durandal/app','knockout', 'modules/securityservice'], function (app, ko, securityService) {
	
	var Home = function() {
		
		this.isPatient = ko.observable (false);
		this.isDoctor = ko.observable (false);
		this.isAdmin = ko.observable (false);
		
		this.userDetails = {
			id: ko.observable(),
			fullName: ko.observable(),
			userType: ko.observable()
		};
	};
	
	Home.prototype.showPatientInfo = function () {
		//Opens a Modal
	};
	
	Home.prototype.activate = function(){
		var self = this;
		
		this.userDetails.id(app.user.id);
		this.userDetails.fullName(app.user.fullName);
		this.userDetails.userType(app.user.userType.displayName);
		
		
		if(app.user.userType.name == 'DOCTOR') {
			self.isPatient(false);
			self.isDoctor(true);
			self.isAdmin(false);
			console.log("DOCTOR");
    	} else if(app.user.userType.name == 'PATIENT'){
    		self.isPatient(true);
			self.isDoctor(false);
			self.isAdmin(false);
    	} else {
    		self.isPatient(false);
			self.isDoctor(false);
			self.isAdmin(true);
    	}
	}
	
	return Home;
});