#!/bin/sh
# shell script to set up a virtual environment
# this is the py3 script
# add additional required packages in the pip commands below.

vroot=$1
rosettedir=$2
expyv=3.

rm -rf $vroot
virtualenv -p python3 $vroot
. $vroot/bin/activate
python check_version.py $expyv
if [[ $? != 0 ]] ; then
    exit 1
fi
python $rosettedir/setup.py install
pip install nose
easy_install nose-pathmunge
