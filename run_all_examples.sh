#!/bin/bash -e

bindings=(java nodejs php python)
key=$1

for i in ${bindings[@]}
do
    cd $i
    ./run_examples.sh $key
    cd ..
done
