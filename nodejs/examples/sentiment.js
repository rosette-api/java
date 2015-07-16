"use strict";

var Api = require("./../lib/rosetteApi");
var ArgumentParser = require('../node_modules/argparse').ArgumentParser;
var DocumentParameters = require("./../lib/DocumentParameters");

var parser = new ArgumentParser({
  addHelp:true,
  description: "Get the sentiment of the text in a local file"
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
parser.addArgument(
  ["--file"],
  {
    help: "Optional input file for data",
    defaultValue: "http://www.basistech.com/about/"
  }
);
var args = parser.parseArgs();

var docParams = new DocumentParameters();
docParams.loadDocumentFile(args.file);

var api = new Api(args.key, args.service_url);
var result = api.sentiment(docParams);

console.log(result);
