#!/bin/bash -e
# This provides the command line strings from running the functional tests in a Docker
# container.  The "run" will result in a # prompt.  
#   cd to /test  
#   Run run_examples.sh
#   exit to quit the container
# This assumes that the script is run from the nodejs directory.
# TODO:
#   Update the script to be OS neutral
#   Evaluate separating the image to be used as a base for the other language functional tests

sudo docker build -t="basistech/nodejs_test" .
sudo docker run -it --add-host="api.rosette.com:127.0.0.1" -v $PWD:/test basistech/nodejs_test
