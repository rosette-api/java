#!/bin/sh
# This creates a new branch in the public repository that contains the current code.
# It would be good to make a tag and be checked out to the tag when running this.

public=git@github.com:basis-technology-corp/rosette-api.git

if [ "$#" -ne 1 ]; then
    echo "Usage: publish-api BRANCH_NAME"
    exit 1
fi

branch=$1
tmp=target/public
clone=$tmp/rosette-api
rm -rf $tmp
mkdir -p $tmp
(cd $tmp; git clone $public)
(cd $clone; git checkout -b $branch)
# assume static short list of files; could be made more complex later
cp README.md $clone/python
cp rosette/api.py $clone/python
cp rosette/setup.py $clone/python
(cd $clone; git add python/README.md python/api.py python/setup.py; git commit; git push -u)



