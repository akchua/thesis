define(['durandal/app','knockout', 'modules/userservice', 'viewmodels/users/userAdd'], 
		function (app, ko, userService, userAdd) {
	
	var UsersCrud = function() {
		this.enableReset = ko.observable(true);
		this.userList = ko.observable();
    	
    	this.searchKey = ko.observable();
    	
    	this.enableReset = ko.observable(true);
    	
    	this.itemsPerPage = ko.observable(app.user.itemsPerPage);
		this.totalItems = ko.observable();
		this.currentPage = ko.observable(1);
		this.currentPageSubscription = null;
	};
	
	UsersCrud.prototype.activate = function() {
    	var self = this;
    	
    	self.currentPage(1);
		self.currentPageSubscription = self.currentPage.subscribe(function() {
			self.refreshUserList();
		});
		
		self.refreshUserList();
    };
    
    UsersCrud.prototype.refreshUserList = function() {
    	var self = this;
    	
    	userService.getUserList(self.currentPage(), self.searchKey()).done(function(data) {
    		self.userList(data.list);
    		self.totalItems(data.total);
    	});
    };
    
    UsersCrud.prototype.search = function() {
    	var self = this;
    	
    	self.currentPage(1);
    	self.refreshUserList();
    };
    
    UsersCrud.prototype.view = function(userId) {
    	var self = this;
    	
    	userService.getUser(userId).done(function(user) {
    		userAdd.show(user,  'Edit User').done(function() {
        		self.refreshUserList();
        	});
    	});
    };
    
    UsersCrud.prototype.add = function() {
    	var self = this;
    	
    	userAdd.show(new Object(),  'Create User').done(function() {
    		self.refreshUserList();
    	});
    };
    
    UsersCrud.prototype.resetPassword = function(userId, lastName, firstName) {
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
    
    UsersCrud.prototype.remove = function(userId, lastName, firstName) {
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
    
    
	return UsersCrud;
});