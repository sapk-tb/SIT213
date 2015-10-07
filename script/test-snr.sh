#!/bin/bash
NB_ECH=30
NB_SYM=10000
ARGS="-form NRZ -mess $NB_SYM -nbEch $NB_ECH -ampl -1 1"

cd..

#Signal*2 = bruit 
./simulateur $ARGS -snr 3  

#Signal = bruit 
./simulateur $ARGS -snr 0  

#Signal = 2*bruit 
./simulateur $ARGS -snr -3   

#Signal = 10*bruit 
./simulateur $ARGS -snr -10     