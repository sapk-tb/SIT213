var S = S || {};


S["snr-transducteur"] = {
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
						l2 = csv[index+11].split(",");
						for (i=1;i<l.length;i++){
							console.log(i-1,i-1+l.length-1);
							data.series[i-1].data.push([parseFloat(l[0]),parseFloat(l[i])]);
							data.series[i-1+l.length-1].data.push([parseFloat(l[0]),parseFloat(l2[i])]);
							//data.series[i-1+l.length-1].data.push([parseFloat(l[0]),parseFloat(l2[i])]); (décaler au meme début que ltransducteur)
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
				urlData = "data/csv/teb-by-snr-"+nbSym+"-"+nbEch+".csv";
				urlDatatransducteur = "data/csv/teb-by-snr-transducteur-"+nbSym+"-"+nbEch+".csv";
				$.get(urlData+"?"+Date.now(),function(csv){
					$.get(urlDatatransducteur+"?"+Date.now(),function(csvtransducteur){
						S["snr-transducteur"].chart.draw(S["snr-transducteur"].tool.parseData(csv,csvtransducteur,nbSym,nbEch),{nbSym:nbSym,nbEch:nbEch});
					});
				});
			}
		}
	}