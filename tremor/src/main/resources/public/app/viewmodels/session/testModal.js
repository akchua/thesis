define(['durandal/app','knockout','plugins/dialog', 
	'modules/userservice', 'modules/test', 'modules/finger' ,'jqPlot', 'highlighter'], 
		function (app, ko, dialog, userService, test, finger, jqplot, highlighter) {
	
	var TestModal = function(testId) {
		this.testId = testId;
		
		this.fingerList = ko.observableArray([]);
		this.thumbData = ko.observable();
		this.ringData = ko.observable();
		this.middleData = ko.observable();
		this.indexData = ko.observable();
		this.pinkyData = ko.observable();
		
		this.fingerType = ko.observableArray([]);
		this.fingerPoints = ko.observableArray([]);
		
		this.chartConfig = {
				data1: []
		};
		
		this.tempChartConfig = {
				thumbData: [],
				ringData: [],
				middleData: [],
				indexData: [],
				pinkyData: [],
				rootData: []
		    };
	};
	
	TestModal.prototype.activate = function() {
		var self = this;
		
		$.jqplot.config.enablePlugins = true;
		
    };
    
    TestModal.prototype.saveToArray = function(data){
    	var self = this;
    	
    	console.log(data);
    	
    	var thumbTemp = [];
    	var ringTemp = [];
    	var middleTemp = [];
    	var indexTemp = [];
    	var pinkyTemp = [];
    	var rootTemp = [];
    	
    	for (var i = 0; i < data.length; i++){
    		if (data[i].fingerType == 'THUMB'){
    			for (var t = 0; t < data[i].fingerPointsList.length; t++){
    				thumbTemp.push([parseFloat(data[i].fingerPointsList[t].x),parseFloat(data[i].fingerPointsList[t].y)]);
    			}
    		} else if (data[i].fingerType == 'RING'){
    			for (var t = 0; t < data[i].fingerPointsList.length; t++){
    				ringTemp.push([parseFloat(data[i].fingerPointsList[t].x),parseFloat(data[i].fingerPointsList[t].y)]);
    			}
    		} else if (data[i].fingerType == 'MIDDLE'){
    			for (var t = 0; t < data[i].fingerPointsList.length; t++){
    				middleTemp.push([parseFloat(data[i].fingerPointsList[t].x),parseFloat(data[i].fingerPointsList[t].y)]);
    			}
    		} else if (data[i].fingerType == 'INDEX'){
    			for (var t = 0; t < data[i].fingerPointsList.length; t++){
    				indexTemp.push([parseFloat(data[i].fingerPointsList[t].x),parseFloat(data[i].fingerPointsList[t].y)]);
    			}
    		} else if (data[i].fingerType == 'PINKY'){
    			for (var t = 0; t < data[i].fingerPointsList.length; t++){
    				pinkyTemp.push([parseFloat(data[i].fingerPointsList[t].x),parseFloat(data[i].fingerPointsList[t].y)]);
    			}
    		} else if (data[i].fingerType == 'ROOT'){
    			for (var t = 0; t < data[i].fingerPointsList.length; t++){
    				rootTemp.push([parseFloat(data[i].fingerPointsList[t].x),parseFloat(data[i].fingerPointsList[t].y)]);
    			}
    		}
    	}
    	
    	self.tempChartConfig.thumbData = Object.values(thumbTemp);
    	self.tempChartConfig.ringData  = Object.values(ringTemp);
    	self.tempChartConfig.middleData = Object.values(middleTemp);
    	self.tempChartConfig.indexData = Object.values(indexTemp);
    	self.tempChartConfig.pinkyData = Object.values(pinkyTemp);
    	self.tempChartConfig.rootData = Object.values(rootTemp);
    	
    	return self;
    };
    
    
    
    TestModal.prototype.compositionComplete = function(){
    	 var self = this;
    	   var d = self.tempChartConfig;
    	  
    	   test.getTest(self.testId, false).then(function(data) {
   			finger.getFingerList(data.leftHand.id, false).then(function(temp1){
   				self.saveToArray(temp1);
   	    	    // uses jqplot, jqplot.barRenderer, jqplot.categoryAxisRenderer, jqplot.canvasAxisTickRenderer, jqplot.dateAxisRenderer, jqplot.highlighter, jqplot.pointLabels (I think)
   				$.jqplot("chart", [d.thumbData, d.ringData, d.middleData, d.indexData, d.pinkyData, d.rootData], {
   	    	      // Turns on animation for all series in this plot.
   	    	      animate: true,
   	    	      animateReplot: true,
   	    	      cursor: {
   	    	    	  show: false
   	    	        },
   	    	     legend: {
   	             /* show: true,
   	              placement: 'outside'*/
   	    	    	labels:['Thumb', 'Ring', 'Middle', 'Index', 'Pinky', 'Root'],
   	    	    	show: true,
   	                location: 'e',
   	                renderer: $.jqplot.EnhancedLegendRenderer,
   	                rendererOptions: {
   	                	numberColumns: 1
   	                }
   	    	     },
   	    	  seriesDefaults: {
   	            fill: false,
   	            showMarker: true,
   	         highlighter: {
   	          show: true,
   	          //formatString:'%s', 
   	          //tooltipLocation:'sw', 
   	          useAxesFormatters:false
   	      }
   	        },
   	    	        series:[
	   	    	         {	label: 'Thumb'	, highlighter: {formatString: "%d"}},
		   	             {	label: 'Ring'	, highlighter: {formatString: "%d"}},
		   	             {	label: 'Middle'	, highlighter: {formatString: "%d"}},
		   	             {	label: 'Index'	, highlighter: {formatString: "%d"}},
		   	         	 {	label: 'Pinky'	, highlighter: {formatString: "%d"}},
		   	             {	label: 'Root'	, highlighter: {formatString: "%d"}},
		   	             {
   	    	                pointLabels: {
   	    	                    show: true
   	    	                },
   	    	                renderer: $.jqplot.BarRenderer,
   	    	                showHighlight: false,
/*   	    	                yaxis: 'yaxis',*/
   	    	                rendererOptions: {
   	    	                    // Speed up the animation a little bit.
   	    	                    // This is a number of milliseconds.  
   	    	                    // Default for bar series is 3000.  
   	    	                    animation: {
   	    	                        speed: 2500
   	    	                    },
   	    	                    barWidth: 15,
   	    	                    barPadding: -15,
   	    	                    barMargin: 0,
   	    	                    highlightMouseOver: false
   	    	                }
   	    	            }, 
   	    	            {
   	    	                rendererOptions: {
   	    	                    // speed up the animation a little bit.
   	    	                    // This is a number of milliseconds.
   	    	                    // Default for a line series is 2500.
   	    	                    animation: {
   	    	                        speed: 2000
   	    	                    }
   	    	                }
   	    	            }
   	    	        ],
   	    	        axesDefaults: {
   	    	            pad: 0
   	    	        },
   	    	        axes: {
   	    	            // These options will set up the x axis like a category axis.
   	    	        xaxis:{
   	    	             tickOptions:{ 
   	    	               angle: -30
   	    	             },
   	    	             tickRenderer:$.jqplot.CanvasAxisTickRenderer,
   	    	               label:'Time(s)', 
   	    	             labelOptions:{
   	    	               fontFamily:'Helvetica',
   	    	               fontSize: '14pt'
   	    	             },
   	    	             labelRenderer: $.jqplot.CanvasAxisLabelRenderer
   	    	             }, 
   	    	         yaxis:{
   	    	            renderer:$.jqplot.LogAxisRenderer,
   	    	            tickOptions:{
   	    	                labelPosition: 'middle', 
   	    	                angle:-30
   	    	            },
   	    	            tickRenderer:$.jqplot.CanvasAxisTickRenderer,
   	    	            labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
   	    	            labelOptions:{
   	    	                fontFamily:'Helvetica',
   	    	                fontSize: '14pt'
   	    	            },
   	    	            label:'Amplitude (mm)'
   	    	          }
   	    	        },
/*   	    	     highlighter: {//On mouseover highlighter attributes
   	              show: true, 
   	              showLabel: true, 
   	              tooltipAxes: 'x',
   	              tooltipLocation : 'ne'
   	          }*/
   	    	    });
   			});
       	});
    	
    };
    
    TestModal.prototype.cancel = function() {
        dialog.close(this);
    };
    
    TestModal.show = function(testId) {
    	return dialog.show(new TestModal(testId));
    };
    
	return TestModal;
});