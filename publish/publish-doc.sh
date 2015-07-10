#!/bin/bash -e
#
# This assumes that you have run a build and so the doc is sitting in <binding>/target/html
#

branch=gh-pages

if [ "$#" -ne 2 ]; then
    echo "Usage: $0 [java|js|php|python|ruby|...] [BRANCH_NAME, eg 0.5.1]"
    exit 1
fi

binding=$1
version=$2
repo=git@github.com:rosette-api/$binding.git
script_dir=$(dirname "${BASH_SOURCE[0]}")
tmp=$(dirname "$script_dir")/target/github-doc
clone=$tmp/$binding
html_path=$binding/target/html
strip_level=3
if [ "$binding" = "java" ] ; then
    html_path=$binding/target/site/apidocs
    strip_level=4
fi

shopt -s extglob
rm -rf "$tmp"
mkdir -p "$tmp"
(cd "$tmp"; git clone $repo)
(cd "$clone"; git checkout -B $branch; git rm -rf .!(git|.))
tar -cf - $html_path | tar xf - -C "$clone" --strip-components $strip_level
(cd "$clone"; git add .; git commit -m "publish $binding apidocs $version"; git push)
