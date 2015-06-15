#!/bin/bash
# shell script to set up a virtual environment
# add additional required packages in the pip commands below.
# this is the version for the default python

vroot=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)/../target/vroot
expyv=2.7

rm -rf $vroot
virtualenv $vroot
. $vroot/bin/activate
python check_version.py $expyv
if [[ $? != 0 ]] ; then
    exit 1
fi
# TODO: don't put this in the test env, just in a doc-build env.
$vroot/bin/pip install epydoc
# later
#$vroot/bin/pip install sphinx
$vroot/bin/pip install pytest
$vroot/bin/pip install HTTPretty
(cd ..; $vroot/bin/python setup.py install)
$vroot/bin/python ../tests/mocked_test_api.py
