define(['jquery'], function ($) {
	return {
		getSession: function(sessionId) {
			return $.ajax({
				url: '/services/session/get',
				data: {
					sessionId: sessionId
				}
			});
		},
		
		getSessionList: function(currentPage, from, to, patientId) {
			return $.ajax({
				url: '/services/session/list',
				data: {
					pageNumber: currentPage - 1,
					from: from,
					to: to,
					patientId: patientId
				}
			});
		},
		
		getRecentSessionList: function(currentPage, daysAgo, doctorId) {
			return $.ajax({
				url: '/services/session/recentlist',
				data: {
					pageNumber: currentPage - 1,
					daysAgo: daysAgo,
					doctorId: doctorId
				}
			});
		},
		
		getSessionCommentList: function(sessionId) {
			return $.ajax({
				url: '/services/session/commentlist',
				data: {
					sessionId: sessionId
				}
			});
		},
		
		addComment: function(sessionId, comment) {
    		return $.ajax({
    			url: '/services/session/addcomment',
    			method: 'POST',
    			data: {
    				sessionId: sessionId,
					comment: comment
    			} 
    		});
    	},
	};
});