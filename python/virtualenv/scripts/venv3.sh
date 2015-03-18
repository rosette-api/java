#!/bin/sh
# shell script to set up a virtual environment
# TODO: what version of python do we want?
# add additional required packages in the pip commands below.

vroot=$1

rm -rf $vroot
virtualenv -p python3 $vroot
. $vroot/bin/activate
pip install nose
pip install requests
pip install enum34
easy_install nose-pathmunge
