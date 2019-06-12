[![Build Status](https://travis-ci.org/rosette-api/java.svg?branch=master)](https://travis-c## Rosette API Access
- Rosette Cloud [Sign Up](https://developer.rosette.com/signup)
- Rosette Enterprise [Evaluation](https://www.rosette.com/product-eval/)

i.org/rosette-api/java)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.basistech.rosette/rosette-api-java-binding/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.basistech.rosette/rosette-api-java-binding)

# Java client binding for Rosette API #

## Rosette API
The Rosette Text Analytics Platform uses natural language processing, statistical modeling, and machine learning to
analyze unstructured and semi-structured text across 364 language-encoding-script combinations, revealing valuable
information and actionable data. Rosette provides endpoints for extracting entities and relationships, translating and
comparing the similarity of names, categorizing and adding linguistic tags to text and more.

## Rosette API Access
- Rosette Cloud [Sign Up](https://developer.rosette.com/signup)
- Rosette Enterprise [Evaluation](https://www.rosette.com/product-eval/)

## Quick Start

### Installation ##
If you use Maven, include this dependency in your `pom.xml`:

```xml
<dependency>
    <groupId>com.basistech.rosette</groupId>
    <artifactId>rosette-api</artifactId>
    <version>${rosette.api.java.binding.version}</version>
</dependency>
```

where `${rosette.api.java.binding.version}` is the [latest version available from Maven Central](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.basistech.rosette%22%20AND%20a%3A%22rosette-api%22).

The version will change as new versions of the binding are released. Note that versions of the form `x.y.Nxx`, where `N` is greater than 100, are internal testing versions; do not use them without consultation with Basis Technology Corp.

If the version you are using is not the latest from Maven Central. Please check for its
[**compatibilty with api.rosette.com**](https://developer.rosette.com/features-and-functions?java).
If you have an on-premise version of Rosette API server, please contact support for binding
compatibility with your installation.

The source code on the master branch is the current state of development; it is not recommended for general use.
If you prefer to build from source, please use an appropriate release tag.

#### Examples
View small example programs for each Rosette endpoint
in the [examples](https://github.com/rosette-api/java/tree/develop/examples) directory.

#### Documentation & Support
- [Binding API](https://rosette-api.github.io/java/)
- [Rosette Platform API](https://developer.rosette.com/features-and-functions)
- [Binding Release Notes](https://github.com/rosette-api/java/wiki/Release-Notes)
- [Rosette Platform Release Notes](https://support.rosette.com/hc/en-us/articles/360018354971-Release-Notes)
- [Binding/Rosette Platform Compatibility](https://developer.rosette.com/features-and-functions?java#)
- [Support](https://support.rosette.com)
- [Binding License: Apache 2.0](https://github.com/rosette-api/java/blob/develop/LICENSE.txt)

## Binding Developer Information
If you are modifying the binding code, please refer to the [developer README](https://github.com/rosette-api/java/tree/develop/DEVELOPER.md) file.


## Basic Usage ##
To check out more examples, see [examples](examples/src/main/java/com/basistech/rosette/examples)

## API Documentation ##
Check out the [documentation](http://rosette-api.github.io/java)

## Release Notes
See the [Wiki](https://github.com/rosette-api/java/wiki/Release-Notes).

## Docker ##
A Docker image for running the examples against the compiled source library is available on Docker Hub.

Command: `docker run -e API_KEY=api-key -v "<binding root directory>:/source" rosetteapi/docker-java`

Additional environment settings:
`-e ALT_URL=<alternative URL>`
`-e FILENAME=<single filename>`

## Additional Information ##
For more, visit [Rosette API site](https://developer.rosette.com)
