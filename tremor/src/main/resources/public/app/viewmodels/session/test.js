define(['durandal/app','knockout', 'modules/test'], function (app, ko, test) {
	
	var Test = function(sessionId) {
		
		this.sessionId = sessionId;
		
		this.itemsPerPage = ko.observable(app.user.itemsPerPage);
		this.currentPage = ko.observable(1);
		this.currentPageSubscription = null;
		this.searchKey = ko.observable();
		
		this.testList = ko.observable();
		this.totalItems = ko.observable();
		
	};
	
	Test.prototype.activate = function () {
		var self = this;
		
		self.currentPage(1);
		self.currentPageSubscription = self.currentPage.subscribe(function() {
			self.refreshTestList();
		});
		
		self.refreshTestList();
	};
	
	Test.prototype.refreshTestList = function (){
		var self = this;
		
		test.getTestList(self.currentPage(), /*self.searchKey()*/null, self.sessionId).done(function(data) {
			self.testList(data.list);
    		self.totalItems(data.total);
    		console.log(data);
    	});
	};
	
	Test.prototype.search = function() {
		var self = this;
		
		self.currentPage(1);
    	self.refreshTestList();
    };
    
	return Test;
});