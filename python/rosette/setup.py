#!/usr/bin/env python

NAME = "rosette_api"
DESCRIPTION = "Rosette API Python client SDK"
AUTHOR = "Basis Techology Corp."
HOMEPAGE = "https://developer.rosette.com"
PLATFORMS = "Python 2.7 and Python 3.4/later"

from setuptools import setup
setup(name=NAME,
      author=AUTHOR,
      description=DESCRIPTION,
      license="Apache License",
      long_description=DESCRIPTION,
      package_dir={
        'rosette': '.'
      },
      packages=['rosette'],
      platforms=PLATFORMS,
      url=HOMEPAGE,
      version='0.5'
     )

