define(['durandal/app','knockout', 'modules/userservice', 'modules/securityservice',
	'viewmodels/patient/patientAdd', 'viewmodels/patient/patientAddEx', 'viewmodels/users/usersCrud'], 
		function (app, ko, userService, securityService, 
				patientAdd, patientAddEx, usersCrud) {
	
	var PatientCrud = function() {
		this.enableReset = ko.observable(true);
		this.patientList = ko.observable();
    	
    	this.searchKey = ko.observable();
    	
    	this.enableReset = ko.observable(true);
    	
    	this.itemsPerPage = ko.observable(app.user.itemsPerPage);
		this.totalItems = ko.observable();
		this.currentPage = ko.observable(1);
		this.currentPageSubscription = null;
	};
	
	PatientCrud.prototype.activate = function() {
    	var self = this;
    	
    	self.currentPage(1);
		self.currentPageSubscription = self.currentPage.subscribe(function() {
			self.refreshPatientList();
		});
		
		self.refreshPatientList();
    };
    
    PatientCrud.prototype.refreshPatientList = function() {
    	var self = this;
    	userService.getPatientList(self.currentPage(), self.searchKey(), app.user.id).done(function(data) {
    		self.patientList(data.list);
    		self.totalItems(data.total);
    		console.log(data);
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
    		patientAdd.show(user,  'Edit Patient Information').done(function() {
        		self.refreshPatientList();
        	});
    	});
    };
    
    PatientCrud.prototype.add = function() {
    	var self = this;
    	
    	patientAdd.show(new Object(),  'Add a New Patient').done(function() {
    		self.refreshPatientList();
    	});
    };
    
    PatientCrud.prototype.addExisting = function() {
    	var self = this;
    	
    	patientAddEx.show(new Object(),  'Add an Existing Patient').done(function() {
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
					self.refreshPatientList();
					app.showMessage(result.message);
					self.enableReset(true);
				});
			}
		})
    };
    
    PatientCrud.prototype.removePatient = function(userId, lastName, firstName) {
    	var self = this;
    	
    	app.showMessage('<p>Are you sure you want to remove User <span class="text-primary">' + firstName + ' ' + lastName + '</span>?</p>',
				'<p class="text-danger">Confirm Remove</p>',
				[{ text: 'Yes', value: true }, { text: 'No', value: false }])
		.then(function(confirm) {
			if(confirm) {
				userService.removePatient(userId).done(function(result) {
					self.refreshPatientList();
					app.showMessage(result.message);
				});
			}
		})
    };
    
    PatientCrud.prototype.viewSession = function(id){
    	usersCrud.viewPatientSession(id);
    };
    
    
	return PatientCrud;
});