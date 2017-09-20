define(['durandal/app','knockout', 'plugins/dialog', 
	'modules/userservice', 'modules/test', 'jqPlot'], 
		function (app, ko, dialog, userService, test, jqplot) {
	
	var TestModal = function(testId) {
		this.testId = testId;
		this.chartConfig = {};
	};
	
	TestModal.prototype.activate = function() {
		var self = this;

	    self.chartConfig = {
	      data1: [
	    	  [0.2, 12.4], [0.4, 11.5], [0.6, 2.6], [0.8, 8.9], [1.0, 3.4],
	    	    [1.2, 5.9], [1.4, 6.3], [1.5, 7.4], [1.6, 5.4], [1.8, 3.4], [2.0, 2.7]]
	    };
	    
    };
    
    
    TestModal.prototype.compositionComplete = function(){
    	 var self = this;
    	   var c = self.chartConfig;
    	    
    	    // uses jqplot, jqplot.barRenderer, jqplot.categoryAxisRenderer, jqplot.canvasAxisTickRenderer, jqplot.dateAxisRenderer, jqplot.highlighter, jqplot.pointLabels (I think)
    	   $.jqplot("chart", [c.data1], {
    	      // Turns on animation for all series in this plot.
    	      animate: true,
    	      animateReplot: true,
    	      cursor: {
    	    	  show: false
    	        },
    	        series:[
    	            {
    	                pointLabels: {
    	                    show: true
    	                },
    	                renderer: $.jqplot.BarRenderer,
    	                showHighlight: false,
    	                yaxis: 'y2axis',
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
    	            xaxis: {
    	                tickInterval: 1,
    	                drawMajorGridlines: false,
    	                drawMinorGridlines: true,
    	                drawMajorTickMarks: false,
    	                rendererOptions: {
    	                tickInset: 0.5,
    	                minorTicks: 1
    	            }
    	            },
    	            yaxis: {
    	                tickOptions: {
    	                    formatString: "$%'d"
    	                },
    	                rendererOptions: {
    	                    forceTickAt0: true
    	                }
    	            }
    	        },
    	        highlighter: {
    	            show: true, 
    	            sizeAdjust: 7.5
    	        }
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