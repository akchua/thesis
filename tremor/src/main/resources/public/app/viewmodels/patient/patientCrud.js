define(['durandal/app','knockout', 'modules/userservice', 'viewmodels/patient/patientInfo', 'modules/securityservice'], 
		function (app, ko, userService, patientInfo, securityService) {
	
	var PatientCrud = function() {
		this.enableReset = ko.observable(true);
		this.patientList = ko.observable();
    	
    	this.searchKey = ko.observable();
    	
    	this.enableReset = ko.observable(true);
    	
    	this.itemsPerPage = ko.observable(app.user.itemsPerPage);
		this.totalItems = ko.observable();
		this.currentPage = ko.observable(1);
		this.currentPageSubscription = null;
		
		this.DoctorInfoModel = {
				id: ko.observable()
		};
	};
	
	PatientCrud.prototype.activate = function() {
    	var self = this;
    	
    	securityService.getUser().done(function(user) {
    		self.DoctorInfoModel.id(user.id);
        });
    	
    	
    	self.currentPage(1);
		self.currentPageSubscription = self.currentPage.subscribe(function() {
			self.refreshPatientList();
		});
		
		self.refreshPatientList();
    };
    
    PatientCrud.prototype.refreshPatientList = function() {
    	var self = this;
    	
    	userService.getPatientList(self.currentPage(), self.searchKey(), self.DoctorInfoModel.id).done(function(data) {
    		self.patientList(data.list);
    		self.totalItems(data.total);
    	});
    };
    
    PatientCrud.prototype.search = function() {
    	var self = this;
    	
    	self.currentPage(1);
    	self.refreshPatientList();
    };
    
    PatientCrud.prototype.view = function(userId) {
    	var self = this;
    	
    	userService.getUser(userId).done(function(user) {
    		patientInfo.show(user,  'Edit User').done(function() {
        		self.refreshUserList();
        	});
    	});
    };
    
    PatientCrud.prototype.add = function() {
    	var self = this;
    	
    	patientInfo.show(new Object(),  'Create User').done(function() {
    		self.refreshPatientList();
    	});
    };
    
    PatientCrud.prototype.resetPassword = function(userId, lastName, firstName) {
    	var self = this;
    	
    	app.showMessage('<p>Are you sure you want to reset password of User <span class="text-primary">' + firstName + ' ' + lastName + '</span>?</p>'
    				+ '<p>The new password will be sent to the user\'s email.</p>',
				'<p class="text-danger">Confirm Reset Password</p>',
				[{ text: 'Yes', value: true }, { text: 'No', value: false }])
		.then(function(confirm) {
			if(confirm) {
				self.enableReset(false);
				userService.resetPassword(userId).done(function(result) {
					self.refreshUserList();
					app.showMessage(result.message);
					self.enableReset(true);
				});
			}
		})
    };
    
    PatientCrud.prototype.remove = function(userId, lastName, firstName) {
    	var self = this;
    	
    	app.showMessage('<p>Are you sure you want to remove User <span class="text-primary">' + firstName + ' ' + lastName + '</span>?</p>',
				'<p class="text-danger">Confirm Remove</p>',
				[{ text: 'Yes', value: true }, { text: 'No', value: false }])
		.then(function(confirm) {
			if(confirm) {
				userService.removeUser(userId).done(function(result) {
					self.refreshUserList();
					app.showMessage(result.message);
				});
			}
		})
    };
    
    
	return PatientCrud;
});