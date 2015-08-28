/*
 * Example code to call Rosette API to get de-compounded words from a piece of text.
 */

"use strict";

var Api = require("rosette-api").Api;
var ArgumentParser = require("argparse").ArgumentParser;
var DocumentParameters = require("rosette-api").DocumentParameters;
var rosetteConstants = require("rosette-api").rosetteConstants;

var parser = new ArgumentParser({
  addHelp: true,
  description: "Get de-compounded words from a piece of text"
});
parser.addArgument(["--key"], {help: "Rosette API key", required: true});
var args = parser.parseArgs();

var docParams = new DocumentParameters();
var content = "${morphology_compound_components_data}";
docParams.setItem("content", content);

var api = new Api(args.key);
api.morphology(docParams, rosetteConstants.morpholoyOutput.COMPOUND_COMPONENTS, function(err, res) {
  if (err) {
    throw err;
  }
  else {
    console.log(res);
  }
});
