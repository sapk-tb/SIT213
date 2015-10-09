var S = {
	tool : {
		parseData : function(csv,nbSym,nbEch){
			data = {
	    		labels :  [],
		    	series :  [
				        {
				        	info : {form : "RZ"},
				            name: "TEB en fonction du SNR (Signal RZ)",
				            marker : {
				            	radius : 3
				            },
				            data: []
				        },
				        {
				        	info : {form : "NRZ"},
				            name: "TEB en fonction du SNR (Signal NRZ)",
				            marker : {
				            	radius : 3
				            },
				            data: []
				        },
				        {
				        	info : {form : "NRZT"},
				            name: "TEB en fonction du SNR (Signal NRZT)",
				            marker : {
				            	radius : 3
				            },
				            data: []
				        }]
	    	};
			csv.split("\n").slice(1, -1).forEach(function (line, index) {
//					if(index == 0 || index == 72) return;
					l = line.split(",");
					data.labels.push(l[0]);
					for (i=1;i<l.length;i++){
						data.series[i-1].data.push(parseFloat(l[i]));
					}
			});
	    	return data;
		}	
	},
	chart : {
		chart : {},
		init : function(){
	    	/*
	    	$("#chart-teb-by-snr .chart").attr("width",($(window).width()-100)+"px");
	    	$("#chart-teb-by-snr .chart").attr("height",($(window).height()-100)+"px");
	    	*/
	    	S.chart.update();
		},
		draw : function(data,options){
		    	$("#chart-teb-by-snr .chart").highcharts({
			            chart: {
			                zoomType: 'x'
			            },
				        title: {
				            text: 'TEB en fonction du SNR',
				            x: -20 //center
				        },
				        subtitle: {
				            text: 'NbEch: '+options.nbEch+', NbSymbole : '+options.nbSym,
				            x: -20
				        },
				        xAxis: {
				            categories: data.labels,
				            title: {
				                text: 'SNR (dB)'
				            }
				        },
				        yAxis: {
				            title: {
				                text: 'TEB'
				            },
				            plotLines: [{
				                value: 0,
				                width: 1,
				                color: '#808080'
				            }]/*, max : 0.5*/
				        },
			            plotOptions: {
			                series: {
			                    cursor: 'pointer',
			                    point: {
			            events: {
                            click: function (e) {
                                hs.htmlExpand(null, {
                                    pageOrigin: {
                                        x: e.pageX || e.clientX,
                                        y: e.pageY || e.clientY
                                    },
                                    headingText: this.series.name,
                                    maincontentText: "<img src='data/img/sondeDiagrammeOeilApresEmetteur-"+this.series.name.split(" ")[6].split(")")[0]+"-"+$("#chart-teb-by-snr #nbSym").val()+"-"+$("#chart-teb-by-snr #nbEch").val()+"-"+this.x+".0.png'/>"+
                                    					"<img src='data/img/sondeDiagrammeOeilApresTransmetteur-"+this.series.name.split(" ")[6].split(")")[0]+"-"+$("#chart-teb-by-snr #nbSym").val()+"-"+$("#chart-teb-by-snr #nbEch").val()+"-"+this.x+".0.png'/>",
                                    width: 200
                                });
                            }
                        }
			                    }
			                }
			            },
				        legend: {
				            layout: 'vertical',
				            align: 'right',
				            verticalAlign: 'top',
				            floating : true,
				            borderWidth: 0
				        },
				        series: data.series
				});
		},
		update : function(){
			$("#chart-teb-by-snr .chart").html("").removeAttr("data-highcharts-chart");
			console.log("Updating graphique ...");
			nbSym = $("#chart-teb-by-snr #nbSym").val();
			nbEch = $("#chart-teb-by-snr #nbEch").val();
			console.log("Paramètre graphique : ",nbSym,nbEch);
			urlData = "data/teb-by-snr-"+nbSym+"-"+nbEch+".csv";
			$.get(urlData+"?"+Date.now(),function(csv){
				S.chart.draw(S.tool.parseData(csv,nbSym,nbEch),{nbSym:nbSym,nbEch:nbEch});
			});
		}
	}
}

$(function(){
	S.chart.init();
})
