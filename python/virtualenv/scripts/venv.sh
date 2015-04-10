#!/bin/sh
# shell script to set up a virtual environment
# add additional required packages in the pip commands below.
# this is the version for the default python

vroot=$1
rosettedir=$2
python=$vroot/bin/python


rm -rf $vroot
echo VENV.SH
python --version
virtualenv $vroot
. $vroot/bin/activate
$python --version
pip install nose
$python $rosettedir/setup.py install
pip install epydoc
easy_install nose-pathmunge

