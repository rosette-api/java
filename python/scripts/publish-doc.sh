#!/bin/bash
#
# This assumes that you have run a build and so the doc is sitting in target/html
#

repo=git@github.com:basis-technology-corp/rosette-api.git
branch=gh-pages

(cd target; git clone $repo $branch; cd $branch/python; git checkout $branch; git rm -rf .)
(cd target/html; find . -print | cpio -pdmv ../$branch/python)
(cd target/$branch/python; git add .; git commit; git push)
