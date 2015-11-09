var S = S || {};


S["gain-transducteur"] = {
		tool : {
			parseData : function(csv,csvtransducteur,nbSym,nbEch){
				data = {
		    		labels :  [],
			    	series :  [
					        {
					        	info : {form : "RZ"},
					            name: "Gain en fonction du SNR (Signal RZ) avec transducteur",
					            marker : {
					            	radius : 3
					            },
					            data: []
					        },
					        {
					        	info : {form : "NRZ"},
					            name: "Gain en fonction du SNR (Signal NRZ) avec transducteur",
					            marker : {
					            	radius : 3
					            },
					            data: []
					        },
					        {
					        	info : {form : "NRZT"},
					            name: "Gain en fonction du SNR (Signal NRZT) avec transducteur",
					            marker : {
					            	radius : 3
					            },
					            data: []
					        }]
		    	};
		    	csv = csv.split("\n").slice(1, -1);
		    	csvtransducteur = csvtransducteur.split("\n").slice(1, -1);
		    	var methode = $("#chart-gain-by-snr-transducteur #typeCalcul").val();
		    	for (index = 0; index < csvtransducteur.length; ++index) {
						l = csvtransducteur[index].split(",");
						l2 = csv[index+11].split(",");
						for (i=1;i<l.length;i++){
							//console.log(i-1,i-1+l.length-1);
							if(methode == "ratio"){
								if(parseFloat(l[i]) != 0)
									data.series[i-1].data.push([parseFloat(l[0]),parseFloat(l2[i])/parseFloat(l[i])]);
								else 
									data.series[i-1].data.push([parseFloat(l[0]),0]);
							}else if(methode == "ecart"){
									data.series[i-1].data.push([parseFloat(l[0]),parseFloat(l2[i])-parseFloat(l[i])]);
							}
							
							//data.series[i-1+l.length-1].data.push([parseFloat(l[0]),parseFloat(l2[i])]);
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
		    	$("#chart-gain-by-snr-transducteur .chart").attr("width",($(window).width()-100)+"px");
		    	$("#chart-gain-by-snr-transducteur .chart").attr("height",($(window).height()-100)+"px");
		    	*/
		    	S["gain-transducteur"].chart.update();
			},
			draw : function(data,options){
			    	$("#chart-gain-by-snr-transducteur .chart").highcharts({
				            chart: {
				            	type: 'line',
				                zoomType: 'x'
				            },
					        title: {
					            text: 'Gain avec transducteur en fonction du SNR ('+$("#chart-gain-by-snr-transducteur #typeCalcul").val()+')',
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
					            type: $("#chart-gain-by-snr-transducteur #typeEchelle").val(),
					            title: {
					                text: 'GAIN'
					            },
					            plotLines: [{
					                value: 0,
					                width: 1,
					                color: '#808080'
					            }], 
					            max : null
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
				$("#chart-gain-by-snr-transducteur .chart").html("").removeAttr("data-highcharts-chart");
				console.log("Updating snr-transducteur graphique ...");
				nbSym = $("#chart-gain-by-snr-transducteur #nbSym").val();
				nbEch = $("#chart-gain-by-snr-transducteur #nbEch").val();
				console.log("Paramètre graphique : ",nbSym,nbEch);
				urlData = "data/csv/teb-by-snr-"+nbSym+"-"+nbEch+".csv";
				urlDatatransducteur = "data/csv/teb-by-snr-transducteur-"+nbSym+"-"+nbEch+".csv";
				$.get(urlData+"?"+Date.now(),function(csv){
					$.get(urlDatatransducteur+"?"+Date.now(),function(csvtransducteur){
						S["gain-transducteur"].chart.draw(S["gain-transducteur"].tool.parseData(csv,csvtransducteur,nbSym,nbEch),{nbSym:nbSym,nbEch:nbEch});
					});
				});
			}
		}
	}