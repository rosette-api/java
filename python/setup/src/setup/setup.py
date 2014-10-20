from setuptools import setup
setup(name='ws-client-binding',
      version='${project.version}',
      packages=['rosette'],
      package_dir={
        'rosette': '../rosette'
      },
      requires=["requests (>= 0.14.0)"],
      setup_requires=['nose>=1.0', 'nose-pathmunge>=0.1.2']
      )
