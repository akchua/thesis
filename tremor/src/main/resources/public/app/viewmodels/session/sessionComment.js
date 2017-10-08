define(['durandal/app','knockout','plugins/dialog', 'modules/session'], 
		function (app, ko, dialog, session) {
	
	var sessionComment = function(sessionId) {
		this.sessionId = sessionId;
		
		this.userComment = ko.observable();
		
		this.comment = ko.observable();
		this.commenter = ko.observable();
		this.timestamp = ko.observable();
		
		this.commentList = ko.observable();
	};
	
	sessionComment.prototype.activate = function() {
		var self = this;
		self.refreshComment();
    };
    
    sessionComment.prototype.cancel = function() {
        dialog.close(this);
    };
    
    sessionComment.show = function(sessionId) {
    	return dialog.show(new sessionComment(sessionId));
    };
    
    sessionComment.prototype.refreshComment = function(){
    	var self = this;
    	session.getSessionCommentList(self.sessionId).done(function(data) {
    		self.commentList(data);
    		console.log(self.commentList);
    		console.log(self.commentList.commenter.formattedName);
    	});
    };
    
    sessionComment.prototype.add = function(){
    	var self = this;
    	
    	session.addComment(self.sessionId,self.userComment()).done(function(data){
    		self.refreshComment();
    	});
    };
    
	return sessionComment;
});