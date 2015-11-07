var S = S || {};


S.multisnr = {
		tool : {
			parseData : function(csv,csvNoCor,nbSym,nbEch,form){
				data = {
		    		labels :  [],
			    	series :  [
					        {
					        	info : {form : form},
					            name: "TEB en fonction du SNR (Signal "+form+" sans multi-trajet)",
					            marker : {
					            	radius : 3
					            },
					            data: []
					        },
					        {
					        	info : {form : form},
					            name: "TEB en fonction du SNR (Signal "+form+" avec -ti 1 12 1)",
					            marker : {
					            	radius : 3
					            },
					            data: []
					        },
					        {
					        	info : {form : form},
					            name: "TEB en fonction du SNR (Signal "+form+" avec -ti 1 60 1)",
					            marker : {
					            	radius : 3
					            },
					            data: []
					        },
					        {
					        	info : {form : form},
					            name: "TEB en fonction du SNR (Signal "+form+" avec -ti 1 45 0.7 -ti 2 60 0.4)",
					            marker : {
					            	radius : 3
					            },
					            data: []
					        },
					        {
					        	info : {form : form},
					            name: "TEB en fonction du SNR (Signal "+form+" corrigé sans multi-trajet)",
					            marker : {
					            	radius : 3
					            },
					            data: []
					        },
					        {
					        	info : {form : form},
					            name: "TEB en fonction du SNR (Signal "+form+" corrigé avec -ti 1 12 1)",
					            marker : {
					            	radius : 3
					            },
					            data: []
					        },
					        {
					        	info : {form : form},
					            name: "TEB en fonction du SNR (Signal "+form+" corrigé avec -ti 1 60 1)",
					            marker : {
					            	radius : 3
					            },
					            data: []
					        },
					        {
					        	info : {form : form},
					            name: "TEB en fonction du SNR (Signal "+form+" corrigé avec -ti 1 45 0.7 -ti 2 60 0.4)",
					            marker : {
					            	radius : 3
					            },
					            data: []
					        },
					        ]
		    	};
				
				
		    	csv = csv.split("\n").slice(1, -1);
		    	csvNoCor = csvNoCor.split("\n").slice(1, -1);
		    	for (index = 0; index < csv.length; ++index) {
						l = csvNoCor[index].split(",");
						l2 = csv[index].split(",");
						//console.log(l,l2);
						for (i=1;i<l.length;i++){
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
		    	S.multisnr.chart.update();
			},
			draw : function(data,options){
			    	$("#chart-teb-by-snr-with-multi .chart").highcharts({
				            chart: {
				            	type: 'line',
				                zoomType: 'x'
				            },
					        title: {
					            text: 'TEB en fonction du SNR pour signal '+options.form,
					            x: -20 //center
					        },
					        subtitle: {
					            text: 'Ampl. : -1 1, NbEch : '+options.nbEch+', NbSymbole : '+options.nbSym+', Form : '+options.form,
					            x: -20
					        },
					        xAxis: {
					            title: {
					                text: 'SNR (dB)'
					            }
					        },
					        yAxis: {
					            type: $("#chart-teb-by-snr-with-multi #typeEchelle").val(),
					            title: {
					                text: 'TEB'
					            },
					            plotLines: [{
					                value: 0,
					                width: 1,
					                color: '#808080'
					            }], 
					            max : ($("#chart-teb-by-snr-with-multi #nbSym").val()>=10000)?0.5:null
					        },
				            plotOptions: {
					            line: {
				                    cursor: 'pointer',
				                    marker: {
					                    enabled: true
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
				$("#chart-teb-by-snr-with-multi .chart").html("").removeAttr("data-highcharts-chart");
				console.log("Updating snr-with-multi graphique ...");
				nbSym = $("#chart-teb-by-snr-with-multi #nbSym").val();
				nbEch = $("#chart-teb-by-snr-with-multi #nbEch").val();
				form = $("#chart-teb-by-snr-with-multi #form").val();
				console.log("Paramètre graphique : ",nbSym,nbEch,form);
				urlData = "data/csv/teb-by-snr-with-multi-"+form+"-"+nbSym+"-"+nbEch+".csv";
				urlDataNoCor = "data/csv/teb-by-snr-with-multi-noMultiCorrection-"+form+"-"+nbSym+"-"+nbEch+".csv";
				$.get(urlData+"?"+Date.now(),function(csv){
					$.get(urlDataNoCor+"?"+Date.now(),function(csvNoCor){
						S.multisnr.chart.draw(S.multisnr.tool.parseData(csv,csvNoCor,nbSym,nbEch,form),{nbSym:nbSym,nbEch:nbEch,form:form});
					});
				});
			}
		}
};