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
		
		getPatientList: function(currentPage, searchKey, doctorId) {
			return $.ajax({
				url: '/services/user/patientlist',
				data: {
					pageNumber: currentPage - 1,
					searchKey: searchKey,
					doctorId: doctorId
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
    	
    	addPatient: function(doctorId, username, password) {
    		return $.ajax({
    			url: '/services/user/addpatient',
    			method: 'POST',
    			data: {
    				doctorId: doctorId,
    				username: username,
    				password: password
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
    	},
    	
    	resetPassword: function(userId) {
    		return $.ajax({
    			url: '/services/user/resetpassword',
    			method: 'POST',
    			data: {
    				userId: userId
    			}
    		});
    	},
	};
});