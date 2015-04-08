from setuptools import setup
setup(name='ws-client-binding',
      version='${project.version}',
      packages=['rosette'],
      package_dir={
        'rosette': '../rosette'
      },
      setup_requires=['nose>=1.0', 'nose-pathmunge>=0.1.2']
      )
