define(['jquery'], function ($) {
	return {
		getUser: function(userId) {
			return $.ajax({
				url: '/services/user/get',
				data: {
					userId: userId
				}
			});
		},
		
		getUserList: function(currentPage, searchKey) {
			return $.ajax({
				url: '/services/user/list',
				data: {
					pageNumber: currentPage - 1,
					searchKey: searchKey
				}
			});
		},
    	
    	getUserTypeList: function() {
    		return $.ajax({
    			url: '/services/user/usertype'
    		});
    	},
    	
    	saveUser: function(userFormData) {
    		return $.ajax({
    			url: '/services/user/save',
    			method: 'POST',
    			data: {
    				userFormData: userFormData
    			} 
    		});
    	},
    	
    	removeUser: function(userId) {
    		return $.ajax({
    			url: '/services/user/remove',
    			method: 'POST',
    			data: {
    				userId: userId
    			}
    		});
    	}
	};
});