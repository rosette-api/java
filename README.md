<a href="https://www.babelstreet.com/rosette"><img src="https://charts.babelstreet.com/icon.png" width="47" height="60"/></a>
# Rosette by Babel Street

---

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.basistech.rosette/rosette-api/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.basistech.rosette/rosette-api-java-binding)

Rosette uses natural language processing, statistical modeling, and machine learning to analyze unstructured and semi-structured text across hundreds of language-script combinations, revealing valuable information and actionable data. Rosette provides endpoints for extracting entities and relationships, translating and comparing the similarity of names, categorizing and adding linguistic tags to text and more. Rosette Server is the on-premises installation of Rosette, with access to Rosette's functions as RESTful web service endpoints. This solves cloud security worries and allows customization (models/indexes) as needed for your business.


## Rosette API Access
- Rosette Cloud [Sign Up](https://developer.babelstreet.com/signup)
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
them without consultation with Babel Street.(???)

#### Examples
View small example programs for each Rosette endpoint in the
[examples](examples/src/main/java/com/basistech/rosette/examples) directory.

#### Documentation & Support
- [Binding API](https://rosette-api.github.io/java/)
- [Rosette Platform API](https://docs.babelstreet.com/API/en/index-en.html)
- [Binding Release Notes](https://github.com/rosette-api/java/wiki/Release-Notes)
- [Rosette Platform Release Notes](https://docs.babelstreet.com/Release/en/rosette-cloud.html)
- [Support](https://babelstreet.my.site.com/support/s/)
- [Binding License: Apache 2.0](LICENSE.txt)

## Binding Developer Information
If you are modifying the binding code, please refer to the [developer README](DEVELOPER.md) file.

