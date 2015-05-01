NAME = "Rosette"
DESCRIPTION = "Python client binding for Basis Technology Rosette web service"
AUTHOR = "Basis Techology Corp."
HOMEPAGE = "http://www.basistech.com"
PLATFORMS = "Python 2.7 and Python 3.4/later"

from setuptools import setup
setup(name=NAME,
      author=AUTHOR,
      description=DESCRIPTION,
      license="Python (Apache License)",
      long_description=DESCRIPTION,
      package_dir={
        'rosette': '.'
      },
      packages=['rosette'],
      platforms=PLATFORMS,
      url=HOMEPAGE,
      version='0.5',

      )
