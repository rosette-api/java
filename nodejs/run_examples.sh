#!/bin/bash -e

cd target/github-publish/examples
npm install
for f in *.js
do
    node $f --key $1
done
