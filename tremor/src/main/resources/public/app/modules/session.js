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
	};
});