#!/bin/bash -e
#
# This assumes that you have run a build and so the doc is sitting in <binding>/target/html
#

branch=gh-pages

if [ "$#" -ne 2 ]; then
    echo "Usage: $0 [js|php|python|ruby|...] [BRANCH_NAME, eg 0.5.1]"
    exit 1
fi

binding=$1
version=$2
repo=git@github.com:rosette-api/$binding.git
script_dir=$(dirname "${BASH_SOURCE[0]}")
tmp=$(dirname "$script_dir")/target/github-doc
clone=$tmp/$binding
if [ "$binding" = "java" ] ; then
    echo "JavaDoc needs to be published to gh-pages with mvn release to avoid -SNAPSHOT version"
    exit 1
fi

shopt -s extglob
rm -rf "$tmp"
mkdir -p "$tmp"
(cd "$tmp"; git clone $repo)
(cd "$clone"; git checkout -B $branch; git rm -rf .!(git|.))
tar -cf - $binding/target/html | tar xf - -C "$clone" --strip-components 3
(cd "$clone"; git add .; git commit -m "publish $binding apidocs $version"; git push -f)
