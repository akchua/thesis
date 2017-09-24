define(['jquery'], function ($) {
	return {
		getFinger: function(fingerId) {
			return $.ajax({
				url: '/services/finger/get',
				data: {
					fingerId: fingerId
				}
			});
		},
		
		getFingerList: function(handId, async) {
			return $.ajax({
				url: '/services/finger/list',
				async: async,
				data: {
					handId: handId
				}
			});
		},
	};
});