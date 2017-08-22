define(['durandal/app','knockout', 'plugins/dialog', 'modules/userservice', 'modules/securityservice'], 
		function (app, ko, dialog, userService, securityService) {
	
	var PatientInfo = function(user, title) {
		this.user = user;
		this.title = title;
		this.userTypeList = ko.observable();
		
		this.enableReset = ko.observable(false);
		
		this.DoctorInfoModel = {
				id: ko.observable()
		};
		
		this.PatientInfoModel = {
	    		id: ko.observable(),
	    		
	    		firstName: ko.observable(),
	    		lastName: ko.observable(),
	    		username: ko.observable(),
	    		password: ko.observable(),
	    		confirmPassword: ko.observable(),
	    		emailAddress: ko.observable(),
	    		contactNumber: ko.observable(),
	    		userType: ko.observable(),
	    		itemsPerPage: ko.observable(),
	    		
	    		birthdate: ko.observable(),
	    		sex: ko.observable(),
	    		address: ko.observable()
		    };
	};
	
	PatientInfo.prototype.activate = function() {
    	var self = this;
    	
    	self.PatientInfoModel.id(self.user.id);
    	
    	self.PatientInfoModel.firstName(self.user.firstName);
    	self.PatientInfoModel.lastName(self.user.lastName);
    	self.PatientInfoModel.username(self.user.username);
    	self.PatientInfoModel.password(self.user.password);
    	self.PatientInfoModel.confirmPassword(self.user.confirmPassword);
    	
    	self.PatientInfoModel.itemsPerPage(self.user.itemsPerPage);
    	
    	self.PatientInfoModel.emailAddress(self.user.emailAddress);
    	self.PatientInfoModel.contactNumber(self.user.contactNumber);
    	
    	self.PatientInfoModel.birthdate(self.user.birthdate);
    	self.PatientInfoModel.sex(self.user.sex);
    	self.PatientInfoModel.address(self.user.address);
    	
    	userService.getUserTypeList().done(function(userTypeList) {
    		self.userTypeList(userTypeList);
    		if(self.user.userType) self.PatientInfoModel.userType(self.user.userType.name);
    		self.PatientInfoModel.userType('PATIENT');
    	});
    	
    	securityService.getUser().done(function(user) {
    		self.DoctorInfoModel.id(user.id);
        });
    };
    
    PatientInfo.show = function(user, title) {
    	return dialog.show(new PatientInfo(user, title));
    };
    
    PatientInfo.prototype.save = function() {
    	var self = this;
    	
       userService.addPatient(self.DoctorInfoModel.id, self.PatientInfoModel.username, self.PatientInfoModel.password).done(function(result) {
        	if(result.success) {
        		dialog.close(self);	
        	} 
        	app.showMessage(result.message);
        });
    };
    
    
    PatientInfo.prototype.cancel = function() {
        dialog.close(this);
    };
    
    
    
	return PatientInfo;
});