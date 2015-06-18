Java Examples
=============

Each example class can be run independently from the Terminal.

Download the Jave Rosette API jar.
 
From the Java directory in the java-api module, run the following command to compile the example classes, 
excluding the angle brackets:

`javac -cp <path-to-java-rosette-api-jar> /com/basistech/rosette/example/*.java`

To run a single example, run the following command. Each example requires a Rosette API key and prints its output to the console.
`java -cp .:..<path-to-java-rosette-api-jar> -Drosette.api.key=<your-api-key> com/basistech/rosette/example/<your-example>`

Each example may also take a following optional service url parameter:
`java -cp .:..<path-to-java-rosette-api-jar> -Drosette.api.key=<your-api-key> com/basistech/rosette/example/<your-example> 
-service-url <your-service-url>`

| File Name                                   | Description                                     | Optional Parameters
| -------------                               |-------------                                    |--------------------
| CategoriesExample.java                      | Gets the category of a document at a URL        |-url <your-url>
| EntitiesExample.java                        | Gets the entities                               |
| EntitiesLinkedExample.java                  | Gets the linked (to Wikipedia) entities         |
| InfoExample.java                            | Gets information about Rosette API              |
| LanguageExample.java                        | Gets the language                               |
| MorphologyCompleteExample.java              | Gets the complete morphological analysis        |
| MorphologyCompoundComponentsExample.java    | Gets the de-compounded words                    |
| MorphologyHanReadingsExample.java           | Gets the Chinese words/Han readings             |
| MorphologyLemmasExample.java                | Gets the lemmas of words                        |
| MorphologyPartOfSpeechExample.java          | Gets the part-of-speech tags                    |
| NameMatcherExample.java                     | Gets the comparison of two names                |
| PingExample.java                            | Pings the Rosette API to check for availability |
| SentenceExample.java                        | Gets the sentences                              |
| SentimentExample.java                       | Gets the sentiment                              |
| TokenExample.java                           | Gets the tokens/words                           |