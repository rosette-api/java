Python Examples
==================

To start with, examples contains python examples that are also embedded in the markdown
end-user documentation for the Rosette API.

The examples are in the python/examples/ directory.

Run `python setup.py install` in the `virtualenv` directory.

Assuming you have installed requests and enum34, you can now run your desired endpoint.py file to see it in action.
For example, run python/examples/categories.py if you want to see the categories
functionality demonstrated.

All files require you to input your Rosette API User Key after --key to run.  
For example: `python ping.py --key 1234567890`  
All also allow you to input your own service URL if desired.  
For example: `python ping.py --key 1234567890 --service_url http://www.myurl.com`    
Some (specified below) allow an additional input of either a file (.html or .txt) or a URL with `--file` or `--url`

Each example, when run, prints its output to the console.

####Endpoint Descriptions

| Endpoint                      | What it does                              | Additional Input Allowed?  |
| -------------                 |:-------------:                            | -----:    |
| categories                    | Gets the category of a document at a URL   | yes, URL  |
| entities                      | Gets the entities from a piece of text     | no        |
| entities/linked               | Gets the linked (to Wikipedia) entities from a piece of text | no|
| info                          | Gets information about Rosette API        | no|
| language                      | Gets the language of a piece of text      | no|
| matched-name                  | Gets the similarity score of two names    | no|
| morpho/complete               | Gets the complete morphological analysis of a piece of text| no|
| morpho/compound-components    | Gets the de-compounded words from a piece of text | no|
| morpho/han-readings           | Gets the Chinese words from a piece of text| no|
| morpho/lemmas                 | Gets the lemmas of words from a piece of text| no|
| morpho/parts-of-speech        | Gets the part-of-speech tags for words in a piece of text | no|
| ping                          | Pings the Rosette API to check for reachability| no|
| sentences                     | Gets the sentences from a piece of text | no|
| sentiment                     | Gets the sentiment of a local file | yes, .html or .txt file |
| tokens                        | Gets the tokens (words) from a piece of text | no|
| tranlsated-name               | Translates a name from one language to another | no|