define(['jquery'], function ($) {
	return {
		getTest: function(testId) {
			return $.ajax({
				url: '/services/test/get',
				data: {
					testId: testId
				}
			});
		},
		
		getTestList: function(currentPage, searchKey, patientId) {
			return $.ajax({
				url: '/services/test/list',
				data: {
					pageNumber: currentPage - 1,
					searchKey: searchKey,
					patientId: patientId
				}
			});
		},
	};
});