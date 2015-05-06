#!/bin/sh -e
# don't install, just run as it. Leave venv clean.
PYTHONPATH=target/build3 RAAS_SERVICE_URL="http://jugmaster.basistech.net/rest/v1" ../virtualenv/target/vroot3/bin/py.test ../tests
# now test with install
(cd ..;virtualenv/target/vroot/bin/python setup.py install)
RAAS_SERVICE_URL="http://jugmaster.basistech.net/rest/v1" ../virtualenv/target/vroot/bin/py.test ../tests


