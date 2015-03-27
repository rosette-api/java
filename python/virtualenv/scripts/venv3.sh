#!/bin/sh
# shell script to set up a virtual environment
# this is the py3 script
# add additional required packages in the pip commands below.

vroot=$1

rm -rf $vroot
virtualenv -p python3 $vroot
. $vroot/bin/activate
pip install nose
easy_install nose-pathmunge
