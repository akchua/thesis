define(['durandal/app','knockout','plugins/dialog', 
	'modules/userservice', 'modules/test', 'modules/finger','jquery','jqPlot',
	'barRenderer', 'highlighter', 'cursor', 'enhancedLegendRenderer'], 
		function (app, ko, dialog, 
				userService, test, finger, $, jqplot) {
	
	var TestModal = function(testId, isLeftHand) {
		this.testId = testId;
		this.isLeftHandData = isLeftHand;
		
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
		
		this.handConfig = {
				thumbAmp : ko.observable(),
				thumbFreq : ko.observable(),
				ringAmp : ko.observable(),
				ringFreq : ko.observable(),
				middleAmp : ko.observable(),
				middleFreq : ko.observable(),
				indexAmp : ko.observable(),
				indexFreq : ko.observable(),
				pinkyAmp : ko.observable(),
				pinkyFreq : ko.observable(),
				rootAmp : ko.observable(),
				rootFreq : ko.observable()
		};
	};
	
	TestModal.prototype.activate = function() {
		var self = this;
		
    };
    
    TestModal.prototype.saveToArray = function(data){
    	var self = this;
    	
    	var thumbTemp = [];
    	var ringTemp = [];
    	var middleTemp = [];
    	var indexTemp = [];
    	var pinkyTemp = [];
    	var rootTemp = [];
    	
    	for (var i = 0; i < data.length; i++){
    		if (data[i].fingerType == 'THUMB'){
    			self.handConfig.thumbAmp(data[i].averageAmplitude);
    			self.handConfig.thumbFreq(data[i].averageFrequency);
    			for (var t = 0; t < data[i].fingerPointsList.length; t++){
    				thumbTemp.push([parseFloat(data[i].fingerPointsList[t].x),parseFloat(data[i].fingerPointsList[t].y)]);
    			}
    		} else if (data[i].fingerType == 'RING'){
    			self.handConfig.ringAmp(data[i].averageAmplitude);
    			self.handConfig.ringFreq(data[i].averageFrequency);
    			for (var t = 0; t < data[i].fingerPointsList.length; t++){
    				ringTemp.push([parseFloat(data[i].fingerPointsList[t].x),parseFloat(data[i].fingerPointsList[t].y)]);
    			}
    		} else if (data[i].fingerType == 'MIDDLE'){
    			self.handConfig.middleAmp(data[i].averageAmplitude);
    			self.handConfig.middleFreq(data[i].averageFrequency);
    			for (var t = 0; t < data[i].fingerPointsList.length; t++){
    				middleTemp.push([parseFloat(data[i].fingerPointsList[t].x),parseFloat(data[i].fingerPointsList[t].y)]);
    			}
    		} else if (data[i].fingerType == 'INDEX'){
    			self.handConfig.indexAmp(data[i].averageAmplitude);
    			self.handConfig.indexFreq(data[i].averageFrequency);
    			for (var t = 0; t < data[i].fingerPointsList.length; t++){
    				indexTemp.push([parseFloat(data[i].fingerPointsList[t].x),parseFloat(data[i].fingerPointsList[t].y)]);
    			}
    		} else if (data[i].fingerType == 'PINKY'){
    			self.handConfig.pinkyAmp(data[i].averageAmplitude);
    			self.handConfig.pinkyFreq(data[i].averageFrequency);
    			for (var t = 0; t < data[i].fingerPointsList.length; t++){
    				pinkyTemp.push([parseFloat(data[i].fingerPointsList[t].x),parseFloat(data[i].fingerPointsList[t].y)]);
    			}
    		} else if (data[i].fingerType == 'ROOT'){
    			self.handConfig.rootAmp(data[i].averageAmplitude);
    			self.handConfig.rootFreq(data[i].averageFrequency);
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
		   
		   $.jqplot.config.enablePlugins = true;
		  
		   test.getTest(self.testId, false).then(function(data) {
			   var handTemp = null;
			   if (self.isLeftHandData)	handTemp = data.leftHand.id;
			   else {	handTemp = data.rightHand.id;	}
			finger.getFingerList(handTemp, false).then(function(temp1){
				self.saveToArray(temp1);
				var data = [d.thumbData, d.ringData, d.middleData, d.indexData, d.pinkyData, d.rootData];
				if(temp1.length == 0){	data= [[null]];	}
				$.jqplot("chart", /*[d.thumbData, d.ringData, d.middleData, d.indexData, d.pinkyData, d.rootData]*/data, {
	    	      animate: true,
	    	      animateReplot: true,
	    	      cursor: {
	    	    	  show: false	
	    	     },
	    	     legend: {
	    	    	labels:['Thumb', 'Ring', 'Middle', 'Index', 'Pinky', 'Root'],
	    	    	show: true,
	                location: 'e',
	                renderer: $.jqplot.EnhancedLegendRenderer,
	                rendererOptions: {
	                	numberColumns: 1}
	    	     },
	   	    	  seriesDefaults: {
	   	            fill: false,
	   	            showMarker: true,
		   	        highlighter: {
		   	        show: true,
		   	        useAxesFormatters:false	}
	   	        },
		        series:[
	    	         {	label: 'Thumb'	},	
	    	         {	label: 'Ring'	},
	   	             {	label: 'Middle'	},
	   	             {	label: 'Index'	},
	   	         	 {	label: 'Pinky'	},
	   	             {	label: 'Root'	},
	   	             {
		             pointLabels: {
		            	 show: false
		             },
	                showHighlight: false,
	                rendererOptions: {
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
	                    animation: {
	                        speed: 2000
	                    }
	                }
	            }],
	    	        axesDefaults: {
	    	            pad: 0
	    	        },
	    	        axes: {
		    	        xaxis:{
		    	             label:'Time(s)', 
		    	        }, 
		    	        yaxis:{
		    	            renderer:$.jqplot.LogAxisRenderer,
		    	            tickRenderer:$.jqplot.CanvasAxisTickRenderer,
		    	            tickOptions: {
		    	                angle: -90,
		    	            },
		    	            label:'Amplitude (mm)'
		    	          }
	    	        },
	    	     highlighter: {
	    	      show: true,
	    	      useAxesFormatters: false,
	    	      tooltipFormatString: '%s'
	    	    }
	    	    });
			});
	   	});

    };
    
    TestModal.prototype.cancel = function() {
        dialog.close(this);
    };
    
    TestModal.show = function(testId, isLeftHand) {
    	return dialog.show(new TestModal(testId, isLeftHand));
    };
    
	return TestModal;
});