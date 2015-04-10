#!/bin/sh
# shell script to set up a virtual environment
# this is the py3 script
# add additional required packages in the pip commands below.

vroot=$1
rosettedir=$2
python=$vroot/bin/python

rm -rf $vroot
echo VENV3.SH
virtualenv -p python3 $vroot
. $vroot/bin/activate
$python --version
$python $rosettedir/setup.py install
pip install nose
easy_install nose-pathmunge
