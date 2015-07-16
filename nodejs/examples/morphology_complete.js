"use strict";

var Api = require("./../lib/rosetteApi");
var ArgumentParser = require('../node_modules/argparse').ArgumentParser;
var DocumentParameters = require("./../lib/DocumentParameters");

var parser = new ArgumentParser({
  addHelp:true,
  description: "Get the complete morphological analysis of a piece of text"
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
var content = "The quick brown fox jumped over the lazy dog. Yes he did.";
docParams.setItem("content", content);

var api = new Api(args.key, args.service_url);
var result = api.morphology(docParams);

console.log(result);
