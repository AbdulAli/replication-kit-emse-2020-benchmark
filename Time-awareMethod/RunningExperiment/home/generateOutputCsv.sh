#!/bin/bash

awk 'FNR > 1' ~/benchmark/results-csv/JURECZKO*.csv  >> ~/benchmark/results-csv/output.csv
