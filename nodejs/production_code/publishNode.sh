#!/usr/bin/env bash

node updateVersion.js

cd target/github-publish

# Publish to npm
npm publish
# Pack to be published to github
npm pack
