<a href="https://www.babelstreet.com/rosette">
<picture>
  <source media="(prefers-color-scheme: light)" srcset="https://charts.babelstreet.com/icon.png">
  <source media="(prefers-color-scheme: dark)" srcset="https://charts.babelstreet.com/icon-white.png">
  <img alt="Babel Street Logo" width="60" height="60">
</picture>
</a>

# Analytics by Babel Street

---

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.basistech.rosette/rosette-api/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.basistech.rosette/rosette-api-java-binding)

Our product is a full text processing pipeline from data preparation to extracting the most relevant information and 
analysis utilizing precise, focused AI that has built-in human understanding. Text Analytics provides foundational 
linguistic analysis for identifying languages and relating words. The result is enriched and normalized text for 
high-speed search and processing without translation.

Text Analytics extracts events and entities — people, organizations, and places — from unstructured text and adds the 
structure of associating those entities into events that deliver only the necessary information for near real-time 
decision making. Accompanying tools shorten the process of training AI models to recognize domain-specific events.

The product delivers a multitude of ways to sharpen and expand search results. Semantic similarity expands search 
beyond keywords to words with the same meaning, even in other languages. Sentiment analysis and topic extraction help 
filter results to what’s relevant.

## Analytics API Access
- Analytics Cloud [Sign Up](https://developer.babelstreet.com/signup)
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
them without consultation with Babel Street.

#### Examples
View small example programs for each Rosette endpoint in the
[examples](examples/src/main/java/com/basistech/rosette/examples) directory.

#### Documentation & Support
- [Binding API](https://rosette-api.github.io/java/)
- [Analytics Platform API](https://docs.babelstreet.com/API/en/index-en.html)
- [Binding Release Notes](https://github.com/rosette-api/java/wiki/Release-Notes)
- [Analytics Platform Release Notes](https://docs.babelstreet.com/Release/en/rosette-cloud.html)
- [Support](https://babelstreet.my.site.com/support/s/)
- [Binding License: Apache 2.0](LICENSE.txt)

## Binding Developer Information
If you are modifying the binding code, please refer to the [developer README](DEVELOPER.md) file.

