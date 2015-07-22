"use strict";

var Api = require("./../lib/AsyncApi");
var ArgumentParser = require("argparse").ArgumentParser;
var DocumentParameters = require("./../lib/DocumentParameters");

var parser = new ArgumentParser({
  addHelp: true,
  description: "Get linked entities from a piece of text"
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

var docParams = new DocumentParameters();
var content = "President Obama urges the Congress and Speaker Boehner to pass the $50 billion spending bill";
content += "based on Christian faith by July 1st or Washington will become totally dysfunctional,";
content += "a terrible outcome for American people.";
docParams.setItem("content", content);

var api = new Api(args.key, args.service_url);
api.entities(docParams, true, function(res) {
  console.log(res);
});
