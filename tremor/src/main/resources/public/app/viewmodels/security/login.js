define(['durandal/app', 'knockout', 
	'viewmodels/security/loginModal','modules/securityservice'], 
	function (app, ko, loginModal, securityService) {
	var Login = function() {
		
	};
	
	Login.prototype.showModal = function() {
		loginModal.show();
	};
	
    return Login;
});