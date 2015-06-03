#!/bin/sh -e
# don't install, just run as it. Leave venv clean.
PYTHONPATH=target/build RAAS_SERVICE_URL="http://jugmaster.basistech.net/rest/v1" ../virtualenv/target/vroot/bin/py.test ../tests
# Should we also test with setup.py install? Yes
(cd ..;virtualenv/target/vroot/bin/python setup.py install)
RAAS_SERVICE_URL="http://jugmaster.basistech.net/rest/v1" ../virtualenv/target/vroot/bin/py.test ../tests


