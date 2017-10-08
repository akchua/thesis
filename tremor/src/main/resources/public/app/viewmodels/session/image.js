define(['durandal/app','knockout','plugins/dialog', 
	'modules/userservice', 'modules/test', 'modules/finger'], 
		function (app, ko, dialog, userService, test, finger) {
	
	var ImageModal = function(testId) {
		this.testId = testId;
		
		this.testImageList = ko.observable();
		this.noImage = ko.observable();
	};
	
	ImageModal.prototype.activate = function() {
		var self = this;
		
		test.getImageList(self.testId, false).done(function(data){
			if (data.length == 0)	self.noImage(true);
			for (var i=0; i < data.length; i++){
					data[i].filePath = test.getImage(data[i].fileName);
			}
			self.testImageList(data);
		});
    };
    
    ImageModal.prototype.cancel = function() {
        dialog.close(this);
    };
    
    ImageModal.show = function(testId) {
    	return dialog.show(new ImageModal(testId));
    };
    
	return ImageModal;
});