define(['plugins/router', 'durandal/app', 'knockout', 'modules/securityservice'], 
		function (router, app, ko, securityService) {
	var homeroute = [
	    { route: ['', 'home'], moduleId: 'viewmodels/home/home', title: 'Home', nav: true },
	    { route: ['patients'], moduleId: 'viewmodels/patient/patientCrud', title: 'Patients', nav: true },
	    { route: ['users'], moduleId: 'viewmodels/users/usersCrud', title: 'Users', nav: true },
	    { route: ['session'], moduleId: 'viewmodels/session/sessionHome', title: 'Session', nav: true }
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
	    			//self.routes = self.routes.concat(testroute);
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
	
	Shell.prototype.logout = function() {
		securityService.logout().done(function() {
    		location.href = '/';
    	});
	};
	
	return Shell;
});