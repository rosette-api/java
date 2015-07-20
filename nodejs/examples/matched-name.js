"use strict";

var Api = require("./../lib/Api");
var ArgumentParser = require("argparse").ArgumentParser;
var NameMatchingParameters = require("./../lib/NameMatchingParameters");

var parser = new ArgumentParser({
  addHelp: true,
  description: "Get the similarity score of two names"
});
parser.addArgument(
  ["--key"],
  {
    help: "Rosette API key",
    required: true
  }
);
parser.addArgument(
  ["--service_url"],
  {
    help: "Optional user service URL"
  }
);
var args = parser.parseArgs();

var name1 = {"text": "Michael Jackson", "language": "eng", "entityType": "PERSON"};
var name2 = {"text": "迈克尔·杰克逊", "entityType": "PERSON"};
var matchParams = new NameMatchingParameters(name1, name2);

var api = new Api(args.key, args.service_url);
var result = api.matchedName(matchParams);

console.log(result);
