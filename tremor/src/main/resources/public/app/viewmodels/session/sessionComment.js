define(['durandal/app','knockout','plugins/dialog', 'modules/session'], 
		function (app, ko, dialog, session) {
	
	var sessionComment = function(sessionId) {
		this.sessionId = sessionId;
		
		this.userComment = ko.observableArray();
		
		this.comment = ko.observable();
		this.commenter = ko.observable();
		this.timestamp = ko.observable();
		
		this.commentList = ko.observable();
	};
	
    sessionComment.prototype.refreshComment = function(){
    	var self = this;
    	session.getSessionCommentList(self.sessionId).done(function(comments) {
    		self.commentList(comments);
    		console.log(self.commentList());
    	});
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

    
    sessionComment.prototype.add = function(){
    	var self = this;
    	
    	session.addComment(self.sessionId,self.userComment()).done(function(data){
    		self.refreshComment();
    	});
    };

    
	return sessionComment;
});