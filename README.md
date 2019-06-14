<a href="https://www.rosette.com"><img src="https://s3.amazonaws.com/styleguide.basistech.com/logos/rosette-logo.png" width="181" height="47" /></a>

---

[![Build Status](https://travis-ci.org/rosette-api/java.svg?branch=master)](https://travis-ci.org/rosette-api/java)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.basistech.rosette/rosette-api/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.basistech.rosette/rosette-api-java-binding)

## Rosette API
The Rosette Text Analytics Platform uses natural language processing, statistical modeling, and machine learning to
analyze unstructured and semi-structured text across 364 language-encoding-script combinations, revealing valuable
information and actionable data. Rosette provides endpoints for extracting entities and relationships, translating and
comparing the similarity of names, categorizing and adding linguistic tags to text and more.

## Rosette API Access
- Rosette Cloud [Sign Up](https://developer.rosette.com/signup)
- Rosette Enterprise [Evaluation](https://www.rosette.com/product-eval/)

## Quick Start

#### Maven
```xml
<dependency>
    <groupId>com.basistech.rosette</groupId>
    <artifactId>rosette-api</artifactId>
    <version>${rosette.api.java.binding.version}</version>
</dependency>
```

Set `${rosette.api.java.binding.version}` in the `<properties>` block.  The latest version available is displayed
in the Maven Central badge at the top of this page.

#### Test Releases
Versions, of the form `x.y.z`, where `z` is greater than or equal to `100`, are internal testing versions.  Do not use
them without consultation with Basis Technology Corp.

#### Examples
View small example programs for each Rosette endpoint in the
[examples](examples/src/main/java/com/basistech/rosette/examples) directory.

#### Documentation & Support
- [Binding API](https://rosette-api.github.io/java/)
- [Rosette Platform API](https://developer.rosette.com/features-and-functions)
- [Binding Release Notes](https://github.com/rosette-api/java/wiki/Release-Notes)
- [Rosette Platform Release Notes](https://support.rosette.com/hc/en-us/articles/360018354971-Release-Notes)
- [Binding/Rosette Platform Compatibility](https://developer.rosette.com/features-and-functions?java#)
- [Support](https://support.rosette.com)
- [Binding License: Apache 2.0](LICENSE.txt)

## Binding Developer Information
If you are modifying the binding code, please refer to the [developer README](DEVELOPER.md) file.

