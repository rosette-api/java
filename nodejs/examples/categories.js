/*
 * Example code to call Rosette API to get a document's (located at given URL) category.
 */

"use strict";

var Api = require("rosette-api").Api;
var ArgumentParser = require("argparse").ArgumentParser;
var DocumentParameters = require("rosette-api").DocumentParameters;

var parser = new ArgumentParser({
  addHelp: true,
  description: "Get the category of a piece of a document at a URL"
});
parser.addArgument(["--key"], {help: "Rosette API key", required: true});
parser.addArgument(["--url"], {help: "Optional URL for data",
    defaultValue: "https://en.wikipedia.org/wiki/Basis_Technology_Corp."});
var args = parser.parseArgs();

var docParams = new DocumentParameters();
docParams.setItem("contentUri", args.url);

var api = new Api(args.key);
api.categories(docParams, function(err, res) {
  if (err) {
    console.log("ERROR! " + err);
  }
  else {
    console.log(res);
  }
});
