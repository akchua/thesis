define(['durandal/app','knockout', 'plugins/dialog', 'modules/userservice'], 
		function (app, ko, dialog, userService) {
	
	var UserAdd = function(user, title) {
		this.user = user;
		this.title = title;
		this.userTypeList = ko.observable();
		this.sexList = ko.observableArray(['Male', 'Female']);
		
		this.showDoctorInformation = ko.observable(false);
		this.showPatientInformation = ko.observable(false);
		
		this.userAddModel = {
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
	    		address: ko.observable(),
	    		
	    		hospital_name: ko.observable(),
	    		hospital_address: ko.observable()
		    };
	};
	
    UserAdd.prototype.activate = function() {
    	var self = this;
    	
    	self.userAddModel.id(self.user.id);
    	
    	self.userAddModel.firstName(self.user.firstName);
    	self.userAddModel.lastName(self.user.lastName);
    	self.userAddModel.username(self.user.username);
    	self.userAddModel.password(self.user.password);
    	self.userAddModel.confirmPassword(self.user.confirmPassword);
    	
    	self.userAddModel.itemsPerPage(self.user.itemsPerPage);
    	
    	self.userAddModel.emailAddress(self.user.emailAddress);
    	self.userAddModel.contactNumber(self.user.contactNumber);
    	
    	self.userAddModel.birthdate(self.user.birthdate);
    	self.userAddModel.sex(self.user.sex);
    	self.userAddModel.address(self.user.address);
    	
    	self.userAddModel.hospital_name(self.user.hospital_name);
    	self.userAddModel.hospital_address(self.user.hospital_address);
    	
    	userService.getUserTypeList().done(function(userTypeList) {
    		self.userTypeList(userTypeList);
    		
    		if(self.user.userType) self.userAddModel.userType(self.user.userType.name);
    		
    		if(self.userAddModel.userType() == 'DOCTOR') {
    			self.showDoctorInformation(true);
    			self.showPatientInformation(false);
        	} else if(self.userAddModel.userType() == 'PATIENT'){
        		self.showDoctorInformation(false);
        		self.showPatientInformation(true);
        	} else {
        		self.showDoctorInformation(false);
        		self.showPatientInformation(false);
        	}
    		
    	});
    };
    
    UserAdd.show = function(user, title) {
    	return dialog.show(new UserAdd(user, title));
    };
    
    UserAdd.prototype.save = function() {
    	var self = this;
    	
    	console.log(self.userAddModel);
    	
       userService.saveUser(ko.toJSON(self.userAddModel)).done(function(result) {
        	if(result.success) {
        		dialog.close(self);	
        	} 
        	app.showMessage(result.message);
        });
    };
    
    
    UserAdd.prototype.cancel = function() {
        dialog.close(this);
    };
    
    UserAdd.prototype.compositionComplete = function() {
    	var self = this;
    	
    	self.userAddModel.userType.subscribe(function(newUserType) {
    		if(newUserType == 'DOCTOR') {
    			self.showDoctorInformation(true);
    			self.showPatientInformation(false);
        	} else if(newUserType == 'PATIENT'){
        		self.showDoctorInformation(false);
        		self.showPatientInformation(true);
        	} else {
        		self.showDoctorInformation(false);
        		self.showPatientInformation(false);
        	}
    	});
    };
    
    
    
	return UserAdd;
});