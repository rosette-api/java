#!/bin/bash
# shell script to set up a virtual environment
# this is the py3 script
# add additional required packages in the pip commands below.

vroot=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)/../target/vroot3
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
$vroot/bin/pip install HTTPretty
(cd ..; $vroot/bin/python setup.py install)
$vroot/bin/python ../tests/mocked_test_api.py
