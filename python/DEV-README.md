Meaning and use of this repository (python binding)
=============================

Without loading the Git repo into your file system, you can't start.  If you haven't
done that already, do so, so that this file appears in the top-level directory,
named 'python'.

Acquire prerequisites.
=================
- versions of python
  - 2.6 & 2.7 come with OS X
  - 3.4 can use [homebrew](http://brew.sh) to install
  - 3.3 install with `brew install --debug src/misc/python33.rb`, you will get a brew link error,
    just do `(cd /usr/local/bin; ln -s ../Cellar/python33/3.3.6_1/bin/python3.3)`
- pip is available on your `$PATH`
- tox is available on your `$PATH`

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

To clean up
============
Run `mvn clean` in this directory.  Temporary output including the epydoc will
be expunged.  The virtual environments used to test will be expunged.  
