#!/bin/bash


function get-teb-by-snr {
    NB_SYM=${3:-999999}
    NB_ECH=${4:-30}
    FORM=${2:-"NRZ"}
    ARGS="-form $FORM -mess $NB_SYM -nbEch $NB_ECH -ampl -1 1"
    ./simulateur $ARGS -snr $1 | grep TEB | cut -d":" -f2 | tr -d " "
}
function generate-teb-by-snr {
    # generate-teb-by-snr $from $to $pas $nbSym $nbEch
    for snr in $(seq $1 $3 $2 | tr "," ".")
    do
        TEB_RZ=$(get-teb-by-snr $snr "RZ" $4 $5)
        TEB_NRZ=$(get-teb-by-snr $snr "NRZ" $4 $5)
	TEB_NRZT=$(get-teb-by-snr $snr "NRZT" $4 $5)
	echo "$snr,$TEB_RZ,$TEB_NRZ,$TEB_NRZT"
    done
}
function generate-oeil {
    # generate-oeil $SNR $nbSym $nbEch
    SNR=${1:-3}
    NB_SYM=${2:-9999}
    NB_ECH=${3:-30}
    ARGS="-mess $NB_SYM -nbEch $NB_ECH -ampl -1 1 -stat-img ../data/img/ 1024 "
    xvfb-run -a ./simulateur $ARGS -snr $SNR -form "RZ"
    xvfb-run -a ./simulateur $ARGS -snr $SNR -form "NRZ"
    xvfb-run -a ./simulateur $ARGS -snr $SNR -form "NRZT"
}
rm data/img/*
rm data/csv/*
mkdir data/img
mkdir data/csv


BASE_DIR=$(pwd);

git clone https://github.com/sapk-tb/SIT213.git tmp
cd tmp
git checkout etape-5
for nbSym in 9 99 999 9999 99999 999999
	do
	for nbEch in 3 5 10 15 30 60
		do
		OUTPUT="$BASE_DIR/data/csv/teb-by-snr-$nbSym-$nbEch.csv"
    		echo "SNR,TEB_RZ,TEB_NRZ,TEB_NRZT" > "$OUTPUT"                
		
		echo "generate-teb-by-snr -60 -30 5 $nbSym $nbEch"
                time generate-teb-by-snr  -60 -21 2 $nbSym $nbEch >> "$OUTPUT"

		echo "generate-teb-by-snr -32 -21 2 $nbSym $nbEch"
    		time generate-teb-by-snr  -60 -21 2 $nbSym $nbEch >> "$OUTPUT"

		echo "generate-teb-by-snr -20 -11 1 $nbSym $nbEch"
    		time generate-teb-by-snr  -20 -11 1 $nbSym $nbEch >> "$OUTPUT"

		echo "generate-teb-by-snr -10 -4 0.5 $nbSym $nbEch"
    		time generate-teb-by-snr  -10 -3 0.5 $nbSym $nbEch >> "$OUTPUT"

		echo "generate-teb-by-snr -3 4 0.3 $nbSym $nbEch"
    		time generate-teb-by-snr  -3 4 0.3 $nbSym $nbEch >> "$OUTPUT"

		echo "generate-teb-by-snr 5 10 1 $nbSym $nbEch"
    		time generate-teb-by-snr  5 10 1 $nbSym $nbEch >> "$OUTPUT"

    	done
done
for nbSym in 9 99 999
	do
	for nbEch in 3 10 30 60
		do
		for SNR in 10 5 3 1 0 -1 -3 -5 -10
			do
			echo "generate-oeil $SNR $nbSym $nbEch"
	    		time generate-oeil $SNR $nbSym $nbEch
    		done
    	done
done
cd ..
rm -Rf tmp
