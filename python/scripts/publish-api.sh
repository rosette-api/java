#!/bin/bash -e
# This creates a new branch in the public repository that contains the current code.
# It would be good to make a tag and be checked out to the tag when running this.
# Note that we have -e, so this will give up if the branch exists already.

public=git@github.com:basis-technology-corp/rosette-api.git

if [ "$#" -ne 1 ]; then
    echo "Usage: publish-api NEW_BRANCH_NAME"
    exit 1
fi

branch=$1
tmp=$PWD/target/public
clone=$tmp/rosette-api
rm -rf "$tmp"
mkdir -p "$tmp"
(cd "$tmp"; git clone $public)
(cd "$clone"; git checkout -b $branch)
(cd "$clone"; git rm -rf python)
(cd setup/target/publish-publish; find . -type f -print | cpio -pdmv "$clone/python")
(cd "$clone"; git add python; git commit; git push -u)
