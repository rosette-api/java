#!/bin/bash
curl -o- https://raw.githubusercontent.com/creationix/nvm/v0.25.4/install.sh | bash
. ~/.nvm/nvm.sh

# Test against node version 0.12.0
nvm install 0.12.0
mvn clean install

# Test against node version io.js v1.0.0
nvm install iojs-v1.0.0
mvn clean install

# Test against node version io.js v2.5.0
nvm install iojs-v2.5.0
mvn clean install

nvm use system
nvm deactivate
