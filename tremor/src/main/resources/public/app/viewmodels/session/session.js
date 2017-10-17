define(['plugins/router', 'durandal/app','knockout', 'durandal/activator',
	'modules/session', 'viewmodels/session/test'], 
		function (router, app, ko, activator, session, Test) {
	
	var Session = function(sessionId) {
		this.patientId = ko.observable();
		
		this.isSessionClicked = ko.observable(false);
		this.activeSession = ko.observable();
		
		this.itemsPerPage = ko.observable(app.user.itemsPerPage);
		this.currentPage = ko.observable(1);
		this.currentPageSubscription = null;
		this.searchFrom = ko.observable();
		this.searchTo = ko.observable();
		
		this.sessionDate = ko.observable();
		
		this.sessionList = ko.observable();
		this.totalItems = ko.observable();
		
		this.testList = activator.create();
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
		
		session.getSessionList(self.currentPage(), null, null, self.patientId()).done(function(data) {
			self.sessionList(data.list);
    		self.totalItems(data.total);
    		console.log(data);
    	});
	};
	
	Session.prototype.loadTestList = function(sessionId){
		var self = this;
		
		self.testList.activateItem(new Test(sessionId));
	};
	
	Session.prototype.search = function() {
		var self = this;
		
		self.currentPage(1);
    	self.refreshSessionList();
    };
    
    Session.prototype.showTests = function (sessionId){
    	var self = this;
    	self.isSessionClicked(true);
    	self.loadTestList(sessionId);
    };
	
	return Session;
});