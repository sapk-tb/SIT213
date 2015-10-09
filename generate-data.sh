#!/bin/bash


function get-teb-by-snr {
    NB_SYM=${3:-999999}
    NB_ECH=${4:-30}
    FORM=${2:-"NRZ"}
    ARGS="-form $FORM -mess $NB_SYM -nbEch $NB_ECH -ampl -1 1 -stat-img "
    ./simulateur $ARGS -snr $1 | grep TEB | cut -d":" -f2 | tr -d " "
}
function generate-teb-by-snr {
	# generate-teb-by-snr $from $to $nbSym $nbEch
    echo "SNR,TEB_RZ,TEB_NRZ,TEB_NRZT"
    for snr in $(seq $1 1 $2)
    do
        TEB_RZ=$(get-teb-by-snr $snr "RZ" $3 $4)
        TEB_NRZ=$(get-teb-by-snr $snr "NRZ" $3 $4)
		TEB_NRZT=$(get-teb-by-snr $snr "NRZT" $3 $4)
		echo "$snr,$TEB_RZ,$TEB_NRZ,$TEB_NRZT"
    done
}
git clone https://github.com/sapk-tb/SIT213.git tmp
cd tmp
for nbSym in 9 99 999 9999 99999 999999
	do
	for nbEch in 3 5 10 15 30 60
		do
		echo "generate-teb-by-snr -60 10 $nbSym $nbEch"
    	time generate-teb-by-snr -60 10 $nbSym $nbEch > "../data/teb-by-snr-$nbSym-$nbEch.csv"
    done
done
cd ..
rm -Rf tmp
