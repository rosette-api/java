#!/bin/bash -e

cd production_code
mvn clean install
cd target/github-publish/examples
npm install
for f in *.js
do
    node $f --key $1
done
