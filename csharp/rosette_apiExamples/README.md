.Net(C#) Examples
==================

These examples are can be run independently to demonstrate the Rosette API functionality.

Prerequisite: Rosette API must be installed. 

If you are using Visual Studios, you can use the Nuget Package Manager.
Search for rosette_api in the Online Packages and install.

If you are using Nuget Command line: `nuget install rosette_api`

You can now run your desired endpoint file to see it in action.

Using Visual Studios, create a new command line project and copy/paste the .cs file or code into your project. 
Edit the `apikey` variable to use your API key. Build and run the code to see the output. 

Using command line, copy/paste the .cs file to the same location that you installed the rosette_api dll. 
Compile the file using `csc $endpoint.cs /r:rosette_api.dll /r:System.Net.Http.dll`. This will output an .exe file.
Run the file using `endpoint.exe yourapikeyhere` 

Each example, when run, prints its output to the console.

| File Name                     | What it does                                          | 
| -------------                 |-------------                                        | 
| categories.cs                    | Gets the category of a document at a URL              | 
| entities.cs                      | Gets the entities from a piece of text                | 
| entities_linked.cs               | Gets the linked (to Wikipedia) entities from a piece of text |
| info.cs                          | Gets information about Rosette API                    | 
| language.cs                      | Gets the language of a piece of text                  | 
| matched-name.cs                  | Gets the similarity score of two names                | 
| morphology_complete.cs               | Gets the complete morphological analysis of a piece of text| 
| morphology_compound-components.cs    | Gets the de-compounded words from a piece of text     | 
| morphology_han-readings.cs           | Gets the Chinese words from a piece of text           | 
| morphology_lemmas.cs                 | Gets the lemmas of words from a piece of text         | 
| morphology_parts-of-speech.cs        | Gets the part-of-speech tags for words in a piece of text | 
| ping.cs                          | Pings the Rosette API to check for reachability       | 
| sentences.cs                     | Gets the sentences from a piece of text               |
| sentiment.cs                     | Gets the sentiment of a local file                    | 
| tokens.cs                        | Gets the tokens (words) from a piece of text          | 
| translated-name.cs               | Translates a name from one language to another        |

