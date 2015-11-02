var S = {
	snr : {
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
						//data.labels.push(new Date(parseFloat(l[0])*1000));
						for (i=1;i<l.length;i++){
							data.series[i-1].data.push([parseFloat(l[0]),parseFloat(l[i])]);
						}
				});
				console.log(data);
		    	return data;
			}	
		},
		oeil : {
			init : function() {
				S.snr.oeil.update();
				//TODO generate all select of SNR
			},
			update : function() {
				$("#diagramme-oeil-by-snr .oeil").html("");
				console.log("Updating graphique ...");
				formSym = $("#diagramme-oeil-by-snr #formSym").val();
				nbSym = $("#diagramme-oeil-by-snr #nbSym").val();
				nbEch = $("#diagramme-oeil-by-snr #nbEch").val();
				SNR = $("#diagramme-oeil-by-snr #SNR").val();
				console.log("Paramètre graphique : ",formSym,nbSym,nbEch,SNR);
				urlImgEmetteur = "data/img/sondeDiagrammeOeilApresEmetteur-"+formSym+"-"+nbSym+"-"+nbEch+"-"+SNR+".0.png";
				urlImgTransmetteur = "data/img/sondeDiagrammeOeilApresTransmetteur-"+formSym+"-"+nbSym+"-"+nbEch+"-"+SNR+".0.png";
				$("#diagramme-oeil-by-snr .oeil").html("<img src='"+urlImgEmetteur+"' /><img src='"+urlImgTransmetteur+"' />")
			}
		},
		chart : {
			chart : {},
			init : function(){
		    	/*
		    	$("#chart-teb-by-snr .chart").attr("width",($(window).width()-100)+"px");
		    	$("#chart-teb-by-snr .chart").attr("height",($(window).height()-100)+"px");
		    	*/
		    	S.snr.chart.update();
			},
			draw : function(data,options){
			    	$("#chart-teb-by-snr .chart").highcharts({
				            chart: {
				            	type: 'line',
				                zoomType: 'x'
				            },
					        title: {
					            text: 'TEB en fonction du SNR',
					            x: -20 //center
					        },
					        subtitle: {
					            text: 'Ampl. : -1 1, NbEch : '+options.nbEch+', NbSymbole : '+options.nbSym,
					            x: -20
					        },
					        xAxis: {
					            title: {
					                text: 'SNR (dB)'
					            }
					        },
					        yAxis: {
					            type: $("#chart-teb-by-snr #typeEchelle").val(),
					            title: {
					                text: 'TEB'
					            },
					            plotLines: [{
					                value: 0,
					                width: 1,
					                color: '#808080'
					            }], 
					            max : ($("#chart-teb-by-snr #nbSym").val()>=10000)?0.5:null
					        },
				            plotOptions: {
					            line: {
				                    cursor: 'pointer',
				                    marker: {
					                    enabled: true
					                },
				                    point: {
							            events: {
				                            click: function (e) {
				                                hs.htmlExpand(null, {
				                                    pageOrigin: {
				                                        x: e.pageX || e.clientX,
				                                        y: e.pageY || e.clientY
				                                    },
				                                    headingText: this.series.name,
				                                    maincontentText: "<img src='data/img/sondeDiagrammeOeilApresEmetteur-"+this.series.name.split(" ")[6].split(")")[0]+"-"+$("#chart-teb-by-snr #nbSym").val()+"-"+$("#chart-teb-by-snr #nbEch").val()+"-"+(this.x-60)+".0.png'/>"+
				                                    					"<img src='data/img/sondeDiagrammeOeilApresTransmetteur-"+this.series.name.split(" ")[6].split(")")[0]+"-"+$("#chart-teb-by-snr #nbSym").val()+"-"+$("#chart-teb-by-snr #nbEch").val()+"-"+(this.x-60)+".0.png'/>",
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
				console.log("Updating snr graphique ...");
				nbSym = $("#chart-teb-by-snr #nbSym").val();
				nbEch = $("#chart-teb-by-snr #nbEch").val();
				console.log("Paramètre graphique : ",nbSym,nbEch);
				urlData = "data/csv/teb-by-snr-"+nbSym+"-"+nbEch+".csv";
				$.get(urlData+"?"+Date.now(),function(csv){
					S.snr.chart.draw(S.snr.tool.parseData(csv,nbSym,nbEch),{nbSym:nbSym,nbEch:nbEch});
				});
			}
		}
	},
	multi : {
		tool : {
			parseData : function(csv,nbSym,nbEch){
				data = {
		    		labels :  [],
			    	series :  [
					        {
					        	info : {form : "RZ"},
					            name: "TEB en fonction du Retard (Signal RZ)",
					            marker : {
					            	radius : 3
					            },
					            data: []
					        },
					        {
					        	info : {form : "NRZ"},
					            name: "TEB en fonction du Retard (Signal NRZ)",
					            marker : {
					            	radius : 3
					            },
					            data: []
					        },
					        {
					        	info : {form : "NRZT"},
					            name: "TEB en fonction du Retard (Signal NRZT)",
					            marker : {
					            	radius : 3
					            },
					            data: []
					        }]
		    	};
				csv.split("\n").slice(1, -1).forEach(function (line, index) {
	//					if(index == 0 || index == 72) return;
						l = line.split(",");
						//data.labels.push(new Date(parseFloat(l[0])*1000));
						for (i=1;i<l.length;i++){
							data.series[i-1].data.push([parseFloat(l[0]),parseFloat(l[i])]);
						}
				});
				console.log(data);
		    	
		    	return data;
			}
		},
		chart : {
			chart : {},
			init : function(){
		    	S.multi.chart.update();
			},
			draw : function(data,options){
				
			    	$("#chart-teb-by-multi .chart").highcharts({
				            chart: {
				            	type: 'line',
				                zoomType: 'x'
				            },
					        title: {
					            text: 'TEB en fonction du Retard',
					            x: -20 //center
					        },
					        subtitle: {
					            text: 'Ampl. : -1 1, Ampl. Retard: '+options.multiAmpl+', NbEch : '+options.nbEch+', NbSymbole : '+options.nbSym+ ', -noMultiCorrection ',
					            x: -20
					        },
					        xAxis: {
					            title: {
					                text: 'Retard'
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
					            }], 
					        },
				            plotOptions: {
					            line: {
				                    cursor: 'pointer',
				                    marker: {
					                    enabled: true
					                },
				                    point: {}
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
				$("#chart-teb-by-multi .chart").html("").removeAttr("data-highcharts-chart");
				console.log("Updating multi graphique ...");
				nbSym = $("#chart-teb-by-multi #nbSym").val();
				nbEch = $("#chart-teb-by-multi #nbEch").val();
				//perRetard = $("#chart-teb-by-multi #perRetard").val();
				multiAmpl = $("#chart-teb-by-multi #multiAmpl").val();
				
				console.log("Paramètre graphique : ",multiAmpl,nbSym,nbEch);
				urlData = "data/csv/teb-by-multi-"+multiAmpl+"-"+nbSym+"-"+nbEch+".csv";
				$.get(urlData+"?"+Date.now(),function(csv){
					S.multi.chart.draw(S.multi.tool.parseData(csv,multiAmpl,nbSym,nbEch),{multiAmpl:multiAmpl,nbSym:nbSym,nbEch:nbEch});
				});
			}
		}
	},
	"snr-transducteur" : {
		tool : {
			parseData : function(csv,csvtransducteur,nbSym,nbEch){
				data = {
		    		labels :  [],
			    	series :  [
					        {
					        	info : {form : "RZ"},
					            name: "TEB en fonction du SNR (Signal RZ) transducteur",
					            marker : {
					            	radius : 3
					            },
					            data: []
					        },
					        {
					        	info : {form : "NRZ"},
					            name: "TEB en fonction du SNR (Signal NRZ) transducteur",
					            marker : {
					            	radius : 3
					            },
					            data: []
					        },
					        {
					        	info : {form : "NRZT"},
					            name: "TEB en fonction du SNR (Signal NRZT) transducteur",
					            marker : {
					            	radius : 3
					            },
					            data: []
					        },
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
		    	csv = csv.split("\n").slice(1, -1);
		    	csvtransducteur = csvtransducteur.split("\n").slice(1, -1);
		    	for (index = 0; index < csvtransducteur.length; ++index) {
						l = csvtransducteur[index].split(",");
						l2 = csv[index].split(",");
						for (i=1;i<l.length;i++){
							console.log(i-1,i-1+l.length-1);
							data.series[i-1].data.push([parseFloat(l[0]),parseFloat(l[i])]);
							data.series[i-1+l.length-1].data.push([parseFloat(l[0]),parseFloat(l2[i])]);
						}
				}
		    	
				console.log(data);
		    	return data;
			}	
		},
		chart : {
			chart : {},
			init : function(){
		    	/*
		    	$("#chart-teb-by-snr-transducteur .chart").attr("width",($(window).width()-100)+"px");
		    	$("#chart-teb-by-snr-transducteur .chart").attr("height",($(window).height()-100)+"px");
		    	*/
		    	S["snr-transducteur"].chart.update();
			},
			draw : function(data,options){
			    	$("#chart-teb-by-snr-transducteur .chart").highcharts({
				            chart: {
				            	type: 'line',
				                zoomType: 'x'
				            },
					        title: {
					            text: 'TEB en fonction du SNR',
					            x: -20 //center
					        },
					        subtitle: {
					            text: 'Ampl. : -1 1, NbEch : '+options.nbEch+', NbSymbole : '+options.nbSym + ", -transducteur",
					            x: -20
					        },
					        xAxis: {
					            title: {
					                text: 'SNR (dB)'
					            }
					        },
					        yAxis: {
					            type: $("#chart-teb-by-snr-transducteur #typeEchelle").val(),
					            title: {
					                text: 'TEB'
					            },
					            plotLines: [{
					                value: 0,
					                width: 1,
					                color: '#808080'
					            }], 
					            max : ($("#chart-teb-by-snr-transducteur #nbSym").val()>=10000)?0.5:null
					        },
				            plotOptions: {
					            line: {
				                    cursor: 'pointer',
				                    marker: {
					                    enabled: true
					                },
				                    point: {
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
				$("#chart-teb-by-snr-transducteur .chart").html("").removeAttr("data-highcharts-chart");
				console.log("Updating snr-transducteur graphique ...");
				nbSym = $("#chart-teb-by-snr-transducteur #nbSym").val();
				nbEch = $("#chart-teb-by-snr-transducteur #nbEch").val();
				console.log("Paramètre graphique : ",nbSym,nbEch);
				urlData = "data/csv/teb-by-snr-transducteur-"+nbSym+"-"+nbEch+".csv";
				urlDatatransducteur = "data/csv/teb-by-snr-transducteur-"+nbSym+"-"+nbEch+".csv";
				$.get(urlData+"?"+Date.now(),function(csv){
					$.get(urlDatatransducteur+"?"+Date.now(),function(csvtransducteur){
						S["snr-transducteur"].chart.draw(S["snr-transducteur"].tool.parseData(csv,csvtransducteur,nbSym,nbEch),{nbSym:nbSym,nbEch:nbEch});
					});
				});
			}
		}
	},
}

$(function(){
	S.snr.chart.init();
	S.snr.oeil.init();
	S.multi.chart.init();
	S["snr-transducteur"].chart.init();
})
