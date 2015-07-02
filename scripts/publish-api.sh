#!/bin/bash -e
# This creates a new branch in the public repository that contains the current code.
# It would be good to make a tag and be checked out to the tag when running this.
# Note that we have -e, so this will give up if the branch exists already.

public=git@github.com:basis-technology-corp/rosette-api.git

if [ "$#" -ne 2 ]; then
    echo "Usage: $0 [java|js|php|python|ruby|...] [NEW_BRANCH_NAME]"
    exit 1
fi

binding=$1
branch=$2
script_dir=$(dirname "${BASH_SOURCE[0]}")
tmp=$(dirname "$script_dir")/target/github-src
clone=$tmp/rosette-api
rm -rf "$tmp"
mkdir -p "$tmp"
(cd "$tmp"; git clone $public)
(cd "$clone"; git checkout -b $branch)
(cd "$clone"; git rm -rf $binding; mkdir $binding)
tar cf - $binding/target/github-publish | tar xf - -C "$clone/$binding" --strip-components 3
(cd "$clone"; git add $binding; git commit -m "publish $branch"; git push -u)
