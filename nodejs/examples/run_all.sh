#!/bin/bash
for f in *.js
do
    node $f --key $1
done
