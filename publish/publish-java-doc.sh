#!/bin/bash -e
#
# This assumes that you have run a build and so the doc is sitting in java/target/site/apidocs
#

branch=gh-pages

if [ "$#" -ne 1 ]; then
    echo "Usage: $0 [Git release tag name, eg 0.5.1]"
    exit 1
fi

version=$1
repo=git@github.com:rosette-api/java.git

if [ "$(uname)" == "Darwin" ] ; then
   opt="-t $branch"
fi
tmp=$(mktemp -d $opt)

shopt -s extglob
(cd "$tmp"; git clone $repo)
(cd "$tmp/java"; git checkout -B $branch; git rm -rf .!(git|.))
tar -cf - java/target/site/apidocs | tar xf - -C "$tmp/java" --strip-components 4
(cd "$tmp/java"; git add .; git commit -m "publish java apidocs $version"; git push -f)
rm -rf "$tmp"
