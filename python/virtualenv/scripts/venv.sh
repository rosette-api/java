#!/bin/sh
# shell script to set up a virtual environment
# add additional required packages in the pip commands below.
# this is the version for the default python

vroot=$1

rm -rf $vroot
virtualenv $vroot
. $vroot/bin/activate
pip install nose
pip install epydoc
pip install enum34
easy_install nose-pathmunge
