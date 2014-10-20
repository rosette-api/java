#!/bin/sh
venv=$1
shift
. $venv/bin/activate
exec $*
