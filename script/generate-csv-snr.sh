#!/bin/bash

NB_ECH=30
NB_SYM=10000
ARGS="-form NRZ -mess $NB_SYM -nbEch $NB_ECH -ampl -1 1"

for i in {-20..10}
do
   ./simulateur $ARGS -snr $i | grep SNR
done
