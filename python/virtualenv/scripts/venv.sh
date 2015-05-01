#!/bin/sh
# shell script to set up a virtual environment
# add additional required packages in the pip commands below.
# this is the version for the default python

vroot=$1
rosettedir=$2
expyv=2.7

rm -rf $vroot
virtualenv $vroot
. $vroot/bin/activate
python check_version.py $expyv
if [[ $? != 0 ]] ; then
    exit 1
fi
pip install nose
python $rosettedir/setup.py install
pip install epydoc
easy_install nose-pathmunge

