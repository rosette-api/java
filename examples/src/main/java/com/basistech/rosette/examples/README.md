Rosette API Java Examples
=========================

Each example class can be run independently.

If you use Maven, everything should have been setup and you can start running the examples using `mvn exec:java`.
Otherwise you can compile and run these examples by hand:

- make sure you have JRE 1.7, verify by `java -version`
- download <TODO: nexus url>
- `cd src/main/java/com/basistech/rosette/examples`
- `javac -cp .:<path-to-rosette-api-jar-file> *.java`
- `java -cp .:<path-to-rosette-api-jar-file> -Drosette.api.key=<your-api-key> com.basistech.rosette.examples.<XyzExample>`
 
| File Name                                   | Description
| -------------                               |------------- 
| Base64InputExample.java                     | Entities using base64 encoded string
| CategoriesExample.java                      | Gets the category of a document at a URL
| EntitiesExample.java                        | Extracts entities
| InfoExample.java                            | Gets information about Rosette API
| LanguageExample.java                        | Detects language
| MorphologyCompleteExample.java              | Gets the complete morphological analysis
| MorphologyCompoundComponentsExample.java    | Gets the de-compounded words
| MorphologyHanReadingsExample.java           | Gets the Chinese/Han readings
| MorphologyLemmasExample.java                | Gets the lemmas
| MorphologyPartsOfSpeechExample.java         | Gets the part-of-speech tags
| NameDeduplicationExample.java               | Gets a list of cluster IDs given a list of names
| NameSimilarityExample.java                  | Matches two names and produces a match score
| NameTranslationExample.java                 | Translates a name
| PingExample.java                            | Pings the Rosette API to check for availability
| SentencesExample.java                       | Gets the sentences
| SentimentExample.java                       | Gets the sentiment of a local file
| TokenExample.java                           | Gets the tokens
