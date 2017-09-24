define(['jquery'], function ($) {
	return {
		getTest: function(testId, async) {
			return $.ajax({
				url: '/services/test/get',
				async: async,
				data: {
					testId: testId
				}
			});
		},
		
		getTestList: function(currentPage, searchKey, sessionId) {
			return $.ajax({
				url: '/services/test/list',
				data: {
					pageNumber: currentPage - 1,
					searchKey: searchKey,
					sessionId: sessionId
				}
			});
		},
		
		getImage: function(fileName) {
			return '/services/test/getimage/' + fileName;				
		},
		
		getImageList: function(testId, async) {
			return $.ajax({
				url: '/services/test/imagelist',
				data: {
					testId: testId,
				}
			});
		}
	};
});