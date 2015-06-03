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
tmp=$(dirname "$script_dir")/target/github
(cd "$tmp"; git clone $repo $branch; cd $branch/$binding; git checkout $branch; git rm -rf .)
(cd $binding/target/html; find . -print | cpio -pdmv "$tmp/$branch/$binding")
(cd "$tmp/$branch/$binding"; git add .; git commit; git push)
