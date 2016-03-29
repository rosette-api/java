---
# Docker Image for Java Examples
---
### Summary
To simplify the running of the Java examples, the Dockerfile will build an image and install the rosette-api library from the *published source*.

### Basic Usage
Build the docker image, e.g. `docker build --rm -t basistech/java:1.1 .`

Run an example as `docker run --rm -e API_KEY=api-key -v "path-to-java-dir:/source" basistech/java:1.1`

To test against a specific source file, add `-e FILENAME=filename` before the `-v`, to test against an alternate url, add `-e ALT_URL=alternate_url`.
