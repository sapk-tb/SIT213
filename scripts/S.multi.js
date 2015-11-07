var S = S || {};

S.multi = {
		tool : {
			parseData : function(csv,nbSym,nbEch){
				data = {
		    		labels :  [],
			    	series :  [
					        {
					        	info : {form : "RZ"},
					            name: "TEB en fonction du Retard (Signal RZ) -noMultiCorrection ",
					            marker : {
					            	radius : 3
					            },
					            data: []
					        },
					        {
					        	info : {form : "NRZ"},
					            name: "TEB en fonction du Retard (Signal NRZ) -noMultiCorrection ",
					            marker : {
					            	radius : 3
					            },
					            data: []
					        },
					        {
					        	info : {form : "NRZT"},
					            name: "TEB en fonction du Retard (Signal NRZT) -noMultiCorrection ",
					            marker : {
					            	radius : 3
					            },
					            data: []
					        }
					        /*
					        ,{
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
					        }
					        */
					        ]
		    	};
				csv.split("\n").slice(1, -1).forEach(function (line, index) {
	//					if(index == 0 || index == 72) return;
						l = line.split(",");
						//data.labels.push(new Date(parseFloat(l[0])*1000));
						for (i=1;i<l.length;i++){
							data.series[i-1].data.push([parseFloat(l[0]),parseFloat(l[i])]);
							//.series[i-1+l.length-1].data.push([parseFloat(l[0]),0]); //TODO check that it is really 0
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
	}