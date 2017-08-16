define(['durandal/app','knockout', 'modules/securityservice'], function (app, ko, securityService) {
	
	var Home = function() {
		
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
		securityService.getUser().done(function(user) {
    		app.user = user;
    		self.userDetails.id(user.id);
    		self.userDetails.fullName(user.fullName);
    		self.userDetails.userType(user.userType.displayName);
        });
	}
	
	return Home;
});