#!/bin/bash


function get-teb-by-multi {
    DT=${1:-"0"}
    AR=${2:-"0"}
    NB_SYM=${3:-100}
    NB_ECH=${4:-30}
    FORM=${5:-"NRZ"}
    ARGS="-form $FORM -mess $NB_SYM -nbEch $NB_ECH -ampl -1 1 -ti 1 $DT $AR -noMultiCorrection"
    ./simulateur $ARGS | grep TEB | cut -d":" -f2 | tr -d " "
}

function generate-teb-by-multi {
    # generate-teb-by-multi $from $to $pas $nbSym $nbEch $ar
    for dt in $(seq $1 $3 $2 | tr "," ".")
    do
        TEB_RZ=$(get-teb-by-multi $dt $6 $4 $5 "RZ")
        TEB_NRZ=$(get-teb-by-multi $dt $6 $4 $5 "NRZ")
        TEB_NRZT=$(get-teb-by-multi $dt $6 $4 $5 "NRZT")
	echo "$dt,$TEB_RZ,$TEB_NRZ,$TEB_NRZT"
    done
}

function generate-teb-by-multi-loop {
	for nbSym in 100 10000 1000000 10000000
		do
		for nbEch in 3 5 10 15 30 60
			do
			for multiAmpl in 0.1 0.3 0.5 1
				do
				OUTPUT="$BASE_DIR/data/csv/teb-by-multi-$multiAmpl-$nbSym-$nbEch.csv"
		    	echo "DT,TEB_RZ,TEB_NRZ,TEB_NRZT" > "$OUTPUT"                
				
				echo "generate-teb-by-multi 0 120 1 $nbSym $nbEch $multiAmpl"
		        time generate-teb-by-multi  0 120 1 $nbSym $nbEch $multiAmpl  >> "$OUTPUT"
		    	done
	    	done
	done
}

function get-teb-by-snr-with-multi {
    SNR=${1:-"10"}
    NB_SYM=${2:-100}
    NB_ECH=${3:-30}
    FORM=${4:-"NRZ"}
    MULTI=${5:-"-ti 1 10 0.5"}
    ARGS="-form $FORM -mess $NB_SYM -nbEch $NB_ECH -snr $SNR -ampl -1 1 $MULTI -noMultiCorrection"
    ./simulateur $ARGS | grep TEB | cut -d":" -f2 | tr -d " "
}

function generate-teb-by-snr-with-multi {
    # generate-teb-by-snr $from $to $pas $nbSym $nbEch $form
    FORM=${6:-"NRZ"}
    for snr in $(seq $1 $3 $2 | tr "," ".")
    do
        TEB_T1=$(get-teb-by-snr $snr $FORM $4 $5 "")
        TEB_T2=$(get-teb-by-snr $snr $FORM $4 $5 "-ti 1 12 1")
        TEB_T3=$(get-teb-by-snr $snr $FORM $4 $5 "-ti 1 60 1")
		TEB_T4=$(get-teb-by-snr $snr $FORM $4 $5 "-ti 1 45 0.7 -ti 2 60 0.4")
	echo "$snr,$TEB_T1,$TEB_T2,$TEB_T3,$TEB_T4"
    done
}


function generate-teb-by-snr-with-multi-loop {
	for nbSym in 10000000
		do
		for nbEch in 10 15 30 60
			do
			for form in "RZ" "NRZ" "NRZT"
				do
				OUTPUT="$BASE_DIR/data/csv/teb-by-snr-with-multi-$form-$nbSym-$nbEch.csv"
	    		echo "SNR,TEB_T1,TEB_T2,TEB_T3,TEB_T4" > "$OUTPUT"                
				
	#			echo "generate-teb-by-snr-with-multi -60 -30 5 $nbSym $nbEch $form"
	#	                time generate-teb-by-snr-with-multi  -60 -30 5 $nbSym $nbEch $form >> "$OUTPUT"
		
	#			echo "generate-teb-by-snr-with-multi -28 -21 2 $nbSym $nbEch $form"
	#	    		time generate-teb-by-snr-with-multi  -28 -21 2 $nbSym $nbEch $form >> "$OUTPUT"
		
				echo "generate-teb-by-snr-with-multi -20 -11 1 $nbSym $nbEch $form"
		    		time generate-teb-by-snr-with-multi  -20 -11 1 $nbSym $nbEch $form >> "$OUTPUT"
		
				echo "generate-teb-by-snr-with-multi -10 -3.5 0.5 $nbSym $nbEch $form"
		    		time generate-teb-by-snr-with-multi  -10 -3.5 0.5 $nbSym $nbEch $form >> "$OUTPUT"
		
				echo "generate-teb-by-snr-with-multi -3 4.9 0.1 $nbSym $nbEch $form"
		    		time generate-teb-by-snr-with-multi  -3 4.9 0.1 $nbSym $nbEch $form >> "$OUTPUT"
		
				echo "generate-teb-by-snr-with-multi 5 10 0.5 $nbSym $nbEch $form"
		    		time generate-teb-by-snr-with-multi  5 10 0.5 $nbSym $nbEch $form >> "$OUTPUT"
		    	done
	    	done
	done
}

function get-teb-by-snr {
    NB_SYM=${3:-1000000}
    NB_ECH=${4:-30}
    FORM=${2:-"NRZ"}
    OTHERS_ARGS=${5:-""}
    ARGS="-form $FORM -mess $NB_SYM -nbEch $NB_ECH -ampl -1 1"
    ./simulateur $ARGS $OTHERS_ARGS -snr $1 | grep TEB | cut -d":" -f2 | tr -d " "
}

function generate-teb-by-snr {
    # generate-teb-by-snr $from $to $pas $nbSym $nbEch
    for snr in $(seq $1 $3 $2 | tr "," ".")
    do
        TEB_RZ=$(get-teb-by-snr $snr "RZ" $4 $5 $6)
        TEB_NRZ=$(get-teb-by-snr $snr "NRZ" $4 $5 $6)
	TEB_NRZT=$(get-teb-by-snr $snr "NRZT" $4 $5 $6)
	echo "$snr,$TEB_RZ,$TEB_NRZ,$TEB_NRZT"
    done
}

function generate-teb-by-snr-loop {
	for nbSym in 10000000
		do
		for nbEch in 10 15 30 60
			do
			OUTPUT="$BASE_DIR/data/csv/teb-by-snr-$nbSym-$nbEch.csv"
#	    		echo "SNR,TEB_RZ,TEB_NRZ,TEB_NRZT" > "$OUTPUT"                
			
#			echo "generate-teb-by-snr -60 -30 5 $nbSym $nbEch"
#	                time generate-teb-by-snr  -60 -30 5 $nbSym $nbEch >> "$OUTPUT"
	
#			echo "generate-teb-by-snr -28 -21 2 $nbSym $nbEch"
#	    		time generate-teb-by-snr  -28 -21 2 $nbSym $nbEch >> "$OUTPUT"
	
#			echo "generate-teb-by-snr -20 -11 1 $nbSym $nbEch"
#	    		time generate-teb-by-snr  -20 -11 1 $nbSym $nbEch >> "$OUTPUT"
	
#			echo "generate-teb-by-snr -10 -3.5 0.5 $nbSym $nbEch"
#	    		time generate-teb-by-snr  -10 -3.5 0.5 $nbSym $nbEch >> "$OUTPUT"
	
			echo "generate-teb-by-snr -3 4.9 0.1 $nbSym $nbEch"
	    		time generate-teb-by-snr  -3 4.9 0.1 $nbSym $nbEch >> "$OUTPUT"
	
			echo "generate-teb-by-snr 5 10 0.5 $nbSym $nbEch"
	    		time generate-teb-by-snr  5 10 0.5 $nbSym $nbEch >> "$OUTPUT"
	
	    	done
	done
}
function generate-teb-by-snr-transducteur-loop {
	for nbSym in 100 10000 1000000 10000000
		do
		for nbEch in 3 5 10 15 30 60
                        do
                        OUTPUT="$BASE_DIR/data/csv/teb-by-snr-transducteur-$nbSym-$nbEch.csv"
                        echo "SNR,TEB_RZ,TEB_NRZ,TEB_NRZT" > "$OUTPUT"

 #                       echo "generate-teb-by-snr -60 -30 5 $nbSym $nbEch -transducteur"
 #                       time generate-teb-by-snr  -60 -30 5 $nbSym $nbEch "-transducteur" >> "$OUTPUT"

 #                       echo "generate-teb-by-snr -28 -21 2 $nbSym $nbEch -transducteur"
 #                       time  generate-teb-by-snr  -28 -21 2 $nbSym $nbEch "-transducteur"  >> "$OUTPUT"

                        echo "generate-teb-by-snr -20 -11 1 $nbSym $nbEch -transducteur"
                        time  generate-teb-by-snr  -20 -11 1 $nbSym $nbEch "-transducteur"  >> "$OUTPUT"

                        echo "generate-teb-by-snr -10 -3.5 0.5 $nbSym $nbEch -transducteur"
                        time  generate-teb-by-snr  -10 -3.5 0.5 $nbSym $nbEch "-transducteur"  >> "$OUTPUT"

                        echo "generate-teb-by-snr -3 4.9 0.1 $nbSym $nbEch  -transducteur"
                        time  generate-teb-by-snr  -3 4.9 0.1 $nbSym $nbEch "-transducteur"  >> "$OUTPUT"

                        echo "generate-teb-by-snr 5 10 0.5 $nbSym $nbEch -transducteur"
                        time  generate-teb-by-snr  5 10 0.5 $nbSym $nbEch "-transducteur"  >> "$OUTPUT"
                done
        done
}

function generate-oeil {
    # generate-oeil $SNR $nbSym $nbEch
    SNR=${1:-3}
    NB_SYM=${2:-10000}
    NB_ECH=${3:-30}
    ARGS="-mess $NB_SYM -nbEch $NB_ECH -ampl -1 1 -stat-img ../data/img/ 1024 -nbSymParOeil 2"
    xvfb-run -a ./simulateur $ARGS -snr $SNR -form "RZ"
    xvfb-run -a ./simulateur $ARGS -snr $SNR -form "NRZ"
    xvfb-run -a ./simulateur $ARGS -snr $SNR -form "NRZT"
}


function generate-oeil-loop {
	for nbSym in 10 100 
		do
		for nbEch in 3 10 30
			do
			for SNR in 10 5 3 1 0 -1 -3 -5 -10
				do
				echo "generate-oeil $SNR $nbSym $nbEch"
		    		time generate-oeil $SNR $nbSym $nbEch
	    		done
	    	done
	done
}

#rm data/img/*
#rm data/csv/*
mkdir data/img
mkdir data/csv

export SIMU_JAVA_OPTIONS="-Xmx14G" 
export SIMU_NB_MAX_SYMBOLE="12" 

BASE_DIR=$(pwd);

git clone https://github.com/sapk-tb/SIT213.git tmp
cd tmp
git checkout etape-5

#generate-teb-by-snr-loop
generate-teb-by-snr-with-multi-loop
#generate-teb-by-snr-transducteur-loop
#generate-teb-by-multi-loop
#generate-oeil-loop
cd ..
#rm -Rf tmp
