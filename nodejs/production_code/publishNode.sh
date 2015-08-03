#!/usr/bin/env bash

node updateVersion.js

grunt clean

npm publish

npm install
