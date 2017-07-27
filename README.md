[![Build Status](https://travis-ci.org/rosette-api/java.svg?branch=master)](https://travis-ci.org/rosette-api/java)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.basistech.rosette/rosette-api-java-binding/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.basistech.rosette/rosette-api-java-binding)

# Java client binding for Rosette API #

## Installation ##
If you use Maven, include this dependency in your `pom.xml`:

```xml
<dependency>
    <groupId>com.basistech.rosette</groupId>
    <artifactId>rosette-api</artifactId>
    <version>1.7.1</version>
</dependency>
```

The version will change as new versions of the binding are released. Note that versions of the form `x.y.Nxx`, where `N` is greater than 100, are internal testing versions; do not use them without consultation with Basis Technology Corp.

The source code on the master branch is the current state of development; it is not recommended for general use.
If you prefer to build from source, please use an appropriate release tag.

## Basic Usage ##
To check out more examples, see [examples](examples/src/main/java/com/basistech/rosette/examples)

## API Documentation ##
Check out the [documentation](http://rosette-api.github.io/java)

## Docker ##
A Docker image for running the examples against the compiled source library is available on Docker Hub.

Command: `docker run -e API_KEY=api-key -v "<binding root directory>:/source" rosetteapi/docker-java`

Additional environment settings:
`-e ALT_URL=<alternative URL>`
`-e FILENAME=<single filename>`

## Additional Information ##
For more, visit [Rosette API site](https://developer.rosette.com)
