/*
 * Example code to call Rosette API to get match score (similarity) for two names.
 */

"use strict";

var Api = require("rosette-api").Api;
var ArgumentParser = require("argparse").ArgumentParser;
var NameMatchingParameters = require("rosette-api").NameMatchingParameters;

var parser = new ArgumentParser({
  addHelp: true,
  description: "Get the similarity score of two names"
});
parser.addArgument(["--key"], {help: "Rosette API key", required: true});
var args = parser.parseArgs();

var name1 = {"text": "${matched_name_data1}", "language": "eng", "entityType": "PERSON"};
var name2 = {"text": "${matched_name_data2}", "entityType": "PERSON"};
var matchParams = new NameMatchingParameters(name1, name2);

var api = new Api(args.key);
api.matchedName(matchParams, function(err, res) {
  if (err) {
    throw err;
  }
  else {
    console.log(res);
  }
});
