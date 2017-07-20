define(['plugins/dialog', 'knockout'], function (dialog, ko) {
	
    var CustomModal = function(title, options, autoclose, settings, viewModel) {
	    this.title = title || CustomModal.defaultTitle;
	    this.options = options || CustomModal.defaultOptions;
	    this.autoclose = autoclose || false;
	    this.settings = $.extend({}, CustomModal.defaultSettings, settings);
	    this.viewModel = viewModel;
	    this.container = ko.observable(this.viewModel); //Binds passed viewModel to the container in the modal.
    };
    
    CustomModal.prototype.selectOption = function (dialogResult) {
    	dialog.close(this, dialogResult);
    };
    
    CustomModal.show = function(title, options, autoclose, settings, viewModel){
        return dialog.show(new CustomModal(title, options, autoclose, settings, viewModel));
    };

    return CustomModal;
});