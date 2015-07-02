#!/bin/bash -e
#
# This assumes that you have run a build and so the doc is sitting in <binding>/target/html
#

repo=git@github.com:basis-technology-corp/rosette-api.git
branch=gh-pages

if [ "$#" -ne 1 ]; then
    echo "Usage: $0 [java|js|php|python|ruby|...]"
    exit 1
fi

binding=$1
script_dir=$(dirname "${BASH_SOURCE[0]}")
tmp=$(dirname "$script_dir")/target/github-doc
clone=$tmp/rosette-api
rm -rf "$tmp"
mkdir -p "$tmp"
(cd "$tmp"; git clone $repo)
(cd "$clone"; git checkout $branch; git rm -rf $binding; mkdir $binding)
tar -cf - $binding/target/html | tar xf - -C "$clone/$binding" --strip-components 3
(cd "$clone/$binding"; git add .; git commit -m "publish $binding docs"; git push)
