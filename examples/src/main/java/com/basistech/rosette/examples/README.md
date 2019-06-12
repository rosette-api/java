Rosette API Java Examples
=========================

Each example class can be run independently.

Each example file demonstrates one of the capabilities of the Rosette Platform.

A note on pre-requisites. Rosette API only suports TLS 1.2 so ensure your toolchain also supports it.

##Maven ##
If you use Maven, everything should have been setup and you can start running the examples using `mvn exec:java`.
Otherwise you can compile and run these examples by hand:

- make sure you have JRE 1.7+, verify by `java -version`
- download <TODO: nexus url>
- `cd src/main/java/com/basistech/rosette/examples`
- `javac -cp .:<path-to-rosette-api-jar-file> *.java`
- `java -cp .:<path-to-rosette-api-jar-file> -Drosette.api.key=<your-api-key> com.basistech.rosette.examples.<XyzExample>`

## Docker ##
[Docker files](https://github.com/rosette-api/java/examples/docker)

### Summary
To simplify the running of the Java examples, the Dockerfile will build an image and install the rosette-api library from the *published source*.

### Basic Usage
Build the docker image, e.g. `docker build --rm -t basistech/java:1.1 .`

Run an example as `docker run --rm -e API_KEY=api-key -v "path-to-java-dir:/source" basistech/java:1.1`

To test against a specific source file, add `-e FILENAME=filename` before the `-v`.
To test against an alternate url, add `-e ALT_URL=alternate_url`.



