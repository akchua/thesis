define(['plugins/router', 'durandal/app','knockout', 'modules/session'], 
		function (router, app, ko, session) {
	
	var Session = function() {
		this.patientId = ko.observable();
		
		this.itemsPerPage = ko.observable(app.user.itemsPerPage);
		this.currentPage = ko.observable(1);
		this.currentPageSubscription = null;
		this.searchFrom = ko.observable();
		this.searchTo = ko.observable();
		
		this.sessionDate = ko.observable();
	};
	
	Session.prototype.activate = function (patientId) {
		var self = this;
		
		self.patientId(patientId);
		
		self.currentPage(1);
		self.currentPageSubscription = self.currentPage.subscribe(function() {
			self.refreshSessionList();
		});
		
		self.refreshSessionList();
		
	};
	
	Session.prototype.refreshSessionList = function (){
		var self = this;
		
		console.log(self.patientId());
		
		session.getSessionList(self.currentPage(), /*self.searchFrom()*/'?', /*self.searchTo()*/'?', self.patientId()).done(function(data) {
    		console.log(data);
    	});
	};
	
	Session.prototype.search = function() {
		var self = this;
		
		self.currentPage(1);
    	self.refreshSessionList();
		
    };
	
	return Session;
});