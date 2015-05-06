#!/bin/sh
#
# This assumes that you have run a build and so the doc is sitting in target/html
#
docroot=../rosette_api_site/python
git rm -rf $docroot/*
(cd target/html; find . -print | cpio -pdmv ../../../$docroot/python)
echo Adding
(cd $docroot; git add *)
echo Committing
(cd $docroot; git commit)
echo Leaving it up to you to push from ../rosette_api_site


