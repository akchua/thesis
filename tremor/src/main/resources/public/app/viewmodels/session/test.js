define(['durandal/app','knockout', 'modules/test', 'viewmodels/session/testModal', 
	'viewmodels/session/image', 'viewmodels/session/sessionComment'], 
		function (app, ko, test, testModal, imageModal, sessionComment) {
	
	var Test = function(sessionId) {
		
		this.sessionId = sessionId;
		
		this.itemsPerPage = ko.observable(app.user.itemsPerPage);
		this.currentPage = ko.observable(1);
		this.currentPageSubscription = null;
		this.searchKey = ko.observable();
		
		this.imageButton = ko.observable();
		
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
		
		test.getTestList(self.currentPage(),null, self.sessionId).done(function(data) {
			self.testList(data.list);
    		self.totalItems(data.total);
    	});
	};
	
	Test.prototype.search = function() {
		var self = this;
		
		self.currentPage(1);
    	self.refreshTestList();
    };
    
    Test.prototype.viewLeftHandModal = function(testId) {
    	testModal.show(testId, true);
    };
    
    Test.prototype.viewRightHandModal = function(testId) {
    	testModal.show(testId, false);
    };
    
    Test.prototype.viewImageModal = function(testId) {
    	imageModal.show(testId);
    };
    
    Test.prototype.viewSessionComment = function(sessionId){
    	sessionComment.show(this.sessionId);
    };
    
	return Test;
});