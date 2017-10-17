define(['plugins/router', 'durandal/app', 'knockout', 'modules/securityservice', 'viewmodels/users/userAdd', 'modules/userservice'], 
		function (router, app, ko, securityService, userAdd, userService) {
	var homeroute = [
	    { route: ['', 'home'], moduleId: 'viewmodels/home/home', title: 'Home', nav: true }
	];
	
	var adminroute = [
		{ route: ['users'], moduleId: 'viewmodels/users/usersCrud', title: 'Registered Users', nav: true, hash:'#users' },
		{ route: 'session/:id', moduleId: 'viewmodels/session/session', title: 'Uploaded Sessions', nav: false, hash: '#session' }
	];
	
	var doctorroute = [
		{ route: ['patients'], moduleId: 'viewmodels/patient/patientCrud', title: 'My Registered Patients', nav: true, hash:'#patients'},
		{ route: 'session/:id', moduleId: 'viewmodels/session/session', title: 'Patient Sessions', nav: false, hash: '#session' }
	];
	
	var patientroute = [
		{ route: 'session/:id', moduleId: 'viewmodels/session/session', title: 'My Sessions', nav: false, hash: '#session' }
	];
	
	var Shell = function() {
		this.router = router;
		
		this.routes = homeroute;
		this.errorMessage = ko.observable();
		
		this.userDetails = {
			id: ko.observable(),
			fullName: ko.observable(),
			userType: ko.observable(),
			itemsPerPage: ko.observable()
		};
	};
	
	Shell.prototype.activate = function() {
		var self = this;
		
		if(app.user) {
			self.userDetails.id(app.user.id);
    		self.userDetails.fullName(app.user.fullName);
    		self.userDetails.userType(app.user.userType);
    		self.userDetails.itemsPerPage(app.user.itemsPerPage);
    		
    		switch(app.user.userType.name) {
	    		case 'ADMINISTRATOR':
	    			self.routes = self.routes.concat(adminroute);
	    			break;
	    		case 'PATIENT':
	    			self.routes= self.routes.concat(patientroute);
	    			break;
	    		case 'DOCTOR':
	    			self.routes = self.routes.concat(doctorroute);;
	    			break;
    		}
		}
    		
    	$.each(self.routes, function(index, route) {
            if (route.childRoutes === undefined)
                return
            $.each(route.childRoutes, function(index, childRoute) {
                childRoute.route = route.route + '/' + childRoute.route;
                childRoute.moduleId = route.moduleRootId + '/' + childRoute.moduleId;
                childRoute.title = childRoute.title;
                childRoute.hash = route.hash + '/' + childRoute.hash;
                childRoute.parent = route.moduleRootId;
            });
            self.routes = self.routes.concat(route.childRoutes);
        });
    	
        self.router.map(self.routes)
        	.buildNavigationModel()
        	.mapUnknownRoutes('viewmodels/security/login');
        
        return router.activate();
	};
	
	Shell.prototype.refreshUser = function() {
		var self = this;
		
		securityService.getUser().done(function(user) {
    		app.user = user;
    		self.userDetails.id(user.id);
    		self.userDetails.fullName(user.fullName);
    		self.userDetails.userType(user.userType);
    		self.userDetails.itemsPerPage(user.itemsPerPage);
        });
	};
	
	Shell.prototype.editProfile = function(){
		var self = this;
		
		userService.getUser(self.userDetails.id()).done(function(user) {
    		userAdd.show(user,  'Edit User').done(function() {
        	});
    	});
	};
	
	Shell.prototype.logout = function() {
		securityService.logout().done(function() {
    		location.href = '/';
    	});
	};
	
	
	return Shell;
});