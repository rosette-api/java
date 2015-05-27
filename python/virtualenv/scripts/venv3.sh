#!/bin/bash
# shell script to set up a virtual environment
# this is the py3 script
# add additional required packages in the pip commands below.

vroot=$1
expyv=3.

rm -rf $vroot
virtualenv -p python3 $vroot
. $vroot/bin/activate
python check_version.py $expyv
if [[ $? != 0 ]] ; then
    exit 1
fi
#x$vroot/bin/pip install epydoc
#$vroot/bin/pip install sphinx
$vroot/bin/pip install pytest
