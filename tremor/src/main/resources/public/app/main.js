requirejs.config({
    paths: {
        'text': '../lib/require/text',
        'durandal':'../lib/durandal/js',
        'plugins' : '../lib/durandal/js/plugins',
        'transitions' : '../lib/durandal/js/transitions',
        'knockout': '../lib/knockout/knockout-3.4.0',
        'bootstrap': '../lib/bootstrap/js/bootstrap',
        'jquery': '../lib/jquery/jquery-1.9.1',
        'bootstrap-datetimepicker': '../lib/bootstrap/js/bootstrap-datetimepicker',
        'moment': '../lib/moment/moment',
        'fullcalendar' : '../lib/fullcalendar/js/fullcalendar.min',
        'jqPlot': '../lib/jqplot/js/jquery.jqplot'
    },
    shim: {
        'bootstrap': {
            deps: ['jquery'],
            exports: 'jQuery'
       }
    }
});

define(['durandal/system', 'durandal/app', 'durandal/viewLocator', 'modules/securityservice', 'durandal/composition','knockout'],  function (system, app, viewLocator, securityService, composition, ko) {
    //>>excludeStart("build", true);
    system.debug(true);
    //>>excludeEnd("build");

    app.title = 'Tremor';

    app.configurePlugins({
        router: true,
        dialog: true,
        widget: true
    });

    app.start().then(function() {
        //Replace 'viewmodels' in the moduleId with 'views' to locate the view.
        //Look for partial views in a 'views' folder in the root.
        viewLocator.useConvention();

        securityService.getUser().done(function(user) {
    		app.user = user;
    		//Show the app by setting the root view model for our application with a transition.
            app.setRoot('viewmodels/shell');
        }).error(function() {
        	app.setRoot('viewmodels/security/login');
        });
    });
});