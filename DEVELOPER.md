## Developer Notes

#### Building and Releasing
To be updated..


#### Internal Releasing
TBD..

#### Old Stuff
[Docker](examples/docker) bits for running examples.  Maybe just remove?

A duplicate copy of the examples docker notes is below...

Docker files can be found [here](https://github.com/rosette-api/java/tree/master/examples/docker)

To simplify the running of the Java examples, the Dockerfile will build an image and install the rosette-api library from the *published source*.

Build the docker image, e.g. `docker build --rm -t basistech/java:1.1 .`

Run an example as `docker run --rm -e API_KEY=api-key -v "path-to-java-dir:/source" basistech/java:1.1`

To test against a specific source file, add `-e FILENAME=filename` before the `-v`.
To test against an alternate url, add `-e ALT_URL=alternate_url`.

#### TODOs
...

