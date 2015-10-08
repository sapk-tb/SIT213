#!/bin/bash


function get-teb-by-snr {
    NB_ECH=30
    NB_SYM=99999
    FORM=${2:-"NRZ"}
    ARGS="-form $FORM -mess $NB_SYM -nbEch $NB_ECH -ampl -1 1"
    ./simulateur $ARGS -snr $1 | grep TEB | cut -d":" -f2 | tr -d " "
}
function generate-teb-by-snr {
    echo "SNR,TEB_RZ,TEB_NRZ,TEB_NRZT"
    for snr in $(seq $1 1 $2)
    do
        TEB_RZ=$(get-teb-by-snr $snr "RZ")
        TEB_NRZ=$(get-teb-by-snr $snr "NRZ")
	TEB_NRZT=$(get-teb-by-snr $snr "NRZT")
	echo "$snr,$TEB_RZ,$TEB_NRZ,$TEB_NRZT"
    done
}
git clone https://github.com/sapk-tb/SIT213.git tmp
cd tmp

    generate-teb-by-snr -60 10 > ../data/teb-by-snr.csv

cd ..
rm -Rf tmp
