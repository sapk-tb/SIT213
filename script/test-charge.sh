#!/bin/bash
NB_ECH=30
NB_SYM=10000
ARGS="-form NRZ  -snr 0 -ampl -1 1"
#time ./simulateur -form NRZ  -snr 0 -ampl -1 1  -mess 100000  -nbEch 30

time ./simulateur $ARGS  -mess 1000  -nbEch 30

time ./simulateur $ARGS  -mess 10000  -nbEch 30

time ./simulateur $ARGS  -mess 100000  -nbEch 30

time ./simulateur $ARGS  -mess 1000000  -nbEch 30


time ./simulateur $ARGS  -mess 10000  -nbEch 3

time ./simulateur $ARGS  -mess 10000  -nbEch 30

time ./simulateur $ARGS  -mess 10000  -nbEch 300

time ./simulateur $ARGS  -mess 100000  -nbEch 300
    