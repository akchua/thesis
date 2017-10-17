define(['durandal/app','knockout', 'plugins/dialog', 'modules/userservice'], 
		function (app, ko, dialog, userService) {
	
	var PatientAdd = function(user, title) {
		this.user = user;
		this.title = title;
		this.userTypeList = ko.observable();
		this.sexList = ko.observableArray(['Male', 'Female']);
		
		this.PatientAddModel = {
	    		id: ko.observable(),
	    		
	    		firstName: ko.observable(),
	    		lastName: ko.observable(),
	    		username: ko.observable(),
	    		password: ko.observable(),
	    		confirmPassword: ko.observable(),
	    		emailAddress: ko.observable(),
	    		contactNumber: ko.observable(),
	    		userType: ko.observable(),
	    		itemsPerPage: ko.observable(10),
	    		
	    		birthdate: ko.observable(),
	    		sex: ko.observable(),
	    		address: ko.observable()
		    };
	};
	
	PatientAdd.prototype.activate = function() {
    	var self = this;
    	
    	self.PatientAddModel.id(self.user.id);
    	
    	self.PatientAddModel.firstName(self.user.firstName);
    	self.PatientAddModel.lastName(self.user.lastName);
    	self.PatientAddModel.username(self.user.username);
    	self.PatientAddModel.password(self.user.password);
    	self.PatientAddModel.confirmPassword(self.user.confirmPassword);
    	
    	self.PatientAddModel.itemsPerPage(self.user.itemsPerPage);
    	
    	self.PatientAddModel.emailAddress(self.user.emailAddress);
    	self.PatientAddModel.contactNumber(self.user.contactNumber);
    	
    	self.PatientAddModel.birthdate(self.user.birthdate);
    	self.PatientAddModel.sex(self.user.sex);
    	self.PatientAddModel.address(self.user.address);
    	
    	userService.getUserTypeList().done(function(userTypeList) {
    		self.userTypeList(userTypeList);
    		if(self.user.userType) self.PatientAddModel.userType(self.user.userType.name);
    		self.PatientAddModel.userType('PATIENT');
    	});
    };
    
    PatientAdd.show = function(user, title) {
    	return dialog.show(new PatientAdd(user, title));
    };
    
    PatientAdd.prototype.save = function() {
    	var self = this;
    	console.log(ko.toJSON(self.PatientAddModel));
       userService.saveUser(ko.toJSON(self.PatientAddModel)).done(function(result) {
        	if(result.success) {
        		 userService.addPatient(self.PatientAddModel.username, self.PatientAddModel.password).done(function(result) {
        	        	if(result.success) {
        	        		dialog.close(self);	
        	        	} 
        	        });
        		dialog.close(self);	
        	} 
        	app.showMessage(result.message);
        });
    };
    
    
    PatientAdd.prototype.cancel = function() {
        dialog.close(this);
    };
    
    PatientAdd.prototype.compositionComplete = function(){
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
    
    
    
	return PatientAdd;
});