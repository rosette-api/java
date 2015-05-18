Meaning and use of this repository (python web client)
=============================

Without loading the Git repo into your file system, you can't start.  If you haven't
done that already, do so, so that this file appears in the top-level directory,
named 'python'.

Acquire prerequisites.
=================
You will need python 2.7 and python 3.4; the latter should be findable as "python3" in
the default search path, or none of this will work.   This is due to the vexing need
to test all pytha on any platform.  Means for acquiring python 3 will differ by
OS platform.

You must acquire and install the python "virtualenv" package in your
python 2.7; the building and testing will use to protect you from
having to install yet more, and test the availability of those latter
packages.

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

    sh scripts/publish-api.sh new-branch-name
    
And then you likely want to make a pull request on github for your
version.

## Publishing docs to github.com

    sh scripts/publish-doc.sh

## Merge and tag in the public repo

## Push to the public package repo

Contact lxu for pypi credentials.

    python setup.py register   # only needed for the very first time
    python setup.py sdist upload


To run the tests again
==================

With the file tree in the above state (from mvn, not an install), cd to the
"setup" subdirectory.  To test in python 2,

    ./test_from_shell.sh

To test from Python 3

    ./test_from_shell_py3.sh

These tests will hit jugmaster.basistech.net; if you don't like that, change or rewrite
either or both of these one-line scripts to use some other URL.  If a user key is needed,
bind the shell variable `RAAS_USER_KEY` to its value (this is part of the test api, not
the binding api).

To clean up
============
Run `mvn clean` in this directory.  Temporary output including the epydoc will
be expunged.  The virtual envirnoments used to test will be expunged.  
