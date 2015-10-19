#!/bin/bash -e

current_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd target/github-publish/examples
for f in *.py
do
    if [ "$#" -gt 1 ]; then
        echo $current_dir
        $current_dir/.tox/py27/bin/python $f --key $1 --service_url $2
    else
        $current_dir/.tox/py27/bin/python $f --key $1
    fi
done

