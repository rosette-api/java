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
    defaultValue: "https://en.wikipedia.org/wiki/Basis_Technology_Corp."
  }
);
var args = parser.parseArgs();

var docParams = new DocumentParameters();
docParams.setItem("contentUri", args.url);

var API = new Api(args.key, args.service_url);
API.categories(docParams, function(res) {
  console.log(res);
});
