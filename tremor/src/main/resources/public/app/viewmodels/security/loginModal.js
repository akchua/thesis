define(['durandal/app', 'knockout', 'plugins/dialog','modules/securityservice', 'viewmodels/users/userAdd'], 
		function (app, ko, dialog, securityService, userAdd) {
	
	var LoginModal = function() {
		this.username = ko.observable();
		
		this.password = ko.observable();
		
		this.errorMessage = ko.observable();
	};
	
	LoginModal.prototype.login = function() {
		var self = this;
		
		self.errorMessage('');
		securityService.login(self.username(), self.password()).done(function(data) {
			if(data == 'SUCCESS') {
				securityService.getUser().done(function(user) {
	        		app.user = user;
	        		//Show the app by setting the root view model for our application with a transition.
	                app.setRoot('viewmodels/shell');
	                self.cancel();
		        });
			} else { // FAILURE
				self.password('');
				self.errorMessage('Invalid Username / Password!');
			}
		});
	};
	
	LoginModal.prototype.compositionComplete = function(){
		var self = this;
		
		 $('input').blur(function() {
			 $('input').keypress(function(e){
				    if(e.which == 13) {
				    	self.login();
				    }
			})
			    var $this = $(this);
			    if ($this.val())
			      $this.addClass('used');
			    else
			      $this.removeClass('used');
			  });

			  var $ripples = $('.ripples');

			  $ripples.on('click.Ripples', function(e) {

			    var $this = $(this);
			    var $offset = $this.parent().offset();
			    var $circle = $this.find('.ripplesCircle');

			    var x = e.pageX - $offset.left;
			    var y = e.pageY - $offset.top;

			    $circle.css({
			      top: y + 'px',
			      left: x + 'px'
			    });

			    $this.addClass('is-active');

			  });

			  $ripples.on('animationend webkitAnimationEnd mozAnimationEnd oanimationend MSAnimationEnd', function(e) {
			  	$(this).removeClass('is-active');
			  });

	};
	
	LoginModal.show = function(){
		dialog.show(new LoginModal());
	};
	
	LoginModal.prototype.cancel = function() {
        dialog.close(this);
    };
	
	LoginModal.prototype.register = function() {
		var self = this;
		
		self.cancel();
    	userAdd.show(new Object(),  'Register');
    };
	
    return LoginModal;
});