Meaning and use of this repository (python binding)
=============================

Without loading the Git repo into your file system, you can't start.  If you haven't
done that already, do so, so that this file appears in the top-level directory,
named 'python'.

Acquire prerequisites.
=================
- versions of python
-- 2.6 & 2.7 come with OS X
-- 3.4 can use [homebrew](http://brew.sh) to install
-- 3.3 install with `brew install --debug src/misc/python33.rb`, you will get a brew link error, just do `(cd /usr/local/bin; ln -s ../Cellar/python33/3.3.6_1/bin/python3.3)
- pip is available on your $PATH
- tox is available on your $PATH

Run mvn
==============
No matter what your goal, you must run mvn in this directory, with no arguments.
"mvn clean" beforehand or as one target is optional.  mvn will set up and build
whatever so requires.

Epydoc documentation will be produced to the target/html subdirectory of this
directory.

To use the code where it now lives
=============
Write your python setup to include the "rosette" subdirectory of this
directory as the home of the "rosette" package, and follow the documentation.
Should work for Python 2 or 3.

To use such a distribution on another system
=================

The mvn build creates `./target/dist/rosette_api-0.5.1.dev0.tar.gz`
(as appropriate for the version in `rosette/__init.py__`). 

Put the .tar.gz it in a fresh directory and tar -xf on it.

Then

    python setup.py install

The rosette package should appear in further uses of whichever python you put
this in.

5. Copying to the Open Source rosette-api repository on github.com
==============================================

## Make a release branch

Use the gitflow release branch convention.

## Set the version in  `rosette/__init__.py` 

Make sure that `rosette/__init__.py` has the version you want to
publish, and it has to be > than the latest one exists in pypi or
upload to pypi will fail.

## Make a tag

## Push the content to a branch in the rosette-api repo on github.com

    (cd ..; sh scripts/publish-api.sh python new-branch-name)
    
And then you likely want to make a pull request on github for your
version.

## Publishing docs to github.com

    (cd ..; sh scripts/publish-doc.sh python)

## Merge and tag in the public repo

## Push to the public package repo

Contact lxu for pypi credentials.

    python setup.py register   # only needed for the very first time
    python setup.py sdist upload

To clean up
============
Run `mvn clean` in this directory.  Temporary output including the epydoc will
be expunged.  The virtual environments used to test will be expunged.  
