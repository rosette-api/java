#!/bin/bash -e

cd target/github-publish/examples
echo "Enter your password to allow sudo to pip install rosette_api"
sudo pip install rosette_api
for f in *.py
do
    python $f --key $1
done

echo "Uninstalling rosette_api"
sudo pip uninstall rosette_api
echo "Logged out of sudo"
sudo -k
