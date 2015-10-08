#!/bin/bash


function get-teb-by-snr {
    NB_ECH=30
    NB_SYM=10000
    ARGS="-form NRZ -mess $NB_SYM -nbEch $NB_ECH -ampl -1 1"
    ./simulateur $ARGS -snr $1 | grep TEB | cut -d":" -f2 | tr -d " "
}
function generate-teb-by-snr {
    echo "SNR,TEB"
    for snr in $(seq $1 1 $2)
    do
	TEB=$(get-teb-by-snr $snr)
	echo "$snr,$TEB"
    done
}
git clone https://github.com/sapk-tb/SIT213.git tmp
cd tmp

    generate-teb-by-snr -60 10 > ../data/teb-by-snr.csv

cd ..
rm -Rf tmp
