#!/bin/bash
#
# This assumes that you have run a build and so the doc is sitting in target/html
#

repo=git@github.com:basis-technology-corp/rosette-api.git
branch=gh-pages

(cd target; git clone $repo $branch; cd $branch; git checkout $branch)
(cd target/html; find . -print | cpio -pdmv ../$branch)
(cd target/$branch; git add .; git commit; git push)
