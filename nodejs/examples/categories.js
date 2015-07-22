"use strict";

var Api = require("./../lib/Api");
var ArgumentParser = require("argparse").ArgumentParser;
var DocumentParameters = require("./../lib/DocumentParameters");

var parser = new ArgumentParser({
  addHelp: true,
  description: "Get the category of a piece of a document at a URL"
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
  ["--url"],
  {
    help: "Optional URL for data",
    defaultValue: "http://www.basistech.com/about"
  }
);
var args = parser.parseArgs();

var docParams = new DocumentParameters();
console.log(args.url);
docParams.setItem("contentUri", args.url);
console.log(docParams.params);

var API = new Api(args.key, args.service_url);
//API.categories(docParams, function(res) {
//  console.log(res);
//});
var result = API.categories(docParams);
console.log(result);
