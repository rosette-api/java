#!/bin/sh
#  Setups.py have to execute in a give directory; not convenient from mvn.
if [ $1 == --cwd ] ; then
   cd $2
   shift
   shift
fi
venv=$1
shift
. $venv/bin/activate
exec $*
