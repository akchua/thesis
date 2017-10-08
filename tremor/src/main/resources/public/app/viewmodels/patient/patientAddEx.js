define(['durandal/app','knockout', 'plugins/dialog', 'modules/userservice'], 
		function (app, ko, dialog, userService) {
	
	var PatientAddEx = function(user, title) {
		this.user = user;
		this.title = title;
		this.userTypeList = ko.observable();
		
		this.UserTypeModel = {
				userType: ko.observable()
			};
		
		this.PatientAddExModel = {
	    		username: ko.observable(),
	    		password: ko.observable()
		    };
	};
	
	PatientAddEx.prototype.activate = function() {
    	var self = this;
    	
    	self.PatientAddExModel.username(self.user.username);
    	self.PatientAddExModel.password(self.user.password);
    	
    	userService.getUserTypeList().done(function(userTypeList) {
    		self.userTypeList(userTypeList);
    		self.UserTypeModel.userType('PATIENT');
    	});
    };
    
    PatientAddEx.show = function(user, title) {
    	return dialog.show(new PatientAddEx(user, title));
    };
    
    PatientAddEx.prototype.save = function() {
    	var self = this;
    	
       userService.addPatient(self.PatientAddExModel.username, self.PatientAddExModel.password).done(function(result) {
        	if(result.success) {
        		dialog.close(self);	
        	} 
        	app.showMessage(result.message);
        });
    };
    
    
    PatientAddEx.prototype.cancel = function() {
        dialog.close(this);
    };
    
    PatientAddEx.prototype.compositionComplete = function() {
    	$('input').blur(function() {
			 $('input').keypress(function(e){
				    if(e.which == 13) {
				    	self.save();
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
    
    
    
	return PatientAddEx;
});