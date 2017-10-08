define(['durandal/app','knockout', 'modules/securityservice', 'modules/session'], function (app, ko, securityService, session) {
	
	var Home = function() {
		
		this.isPatient = ko.observable (false);
		this.isDoctor = ko.observable (false);
		this.isAdmin = ko.observable (false);
		
		this.recentActivityList = ko.observable();
		
		this.userDetails = {
			id: ko.observable(),
			fullName: ko.observable(),
			userType: ko.observable()
		};
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
		
		session.getRecentSessionList(0, 10, app.user.id).done(function(data){
			self.recentActivityList(data.list);
			console.log(self.recentActivityList());
		});
	}
	
	return Home;
});