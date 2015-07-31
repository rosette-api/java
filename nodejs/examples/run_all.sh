#!/bin/bash
npm install ../production_code/rosette-api-0.5.0.tgz
npm install
for f in *.js
do
    node $f --key $1
done
