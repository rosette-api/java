/*
 * Example code to call Rosette API to get information such as version and build.
 */

"use strict";

var Api = require("rosette-api").Api;
var ArgumentParser = require("argparse").ArgumentParser;

var parser = new ArgumentParser({
  addHelp: true,
  description: "Get information about Rosette API"
});
parser.addArgument(["--key"], {help: "Rosette API key", required: true});
var args = parser.parseArgs();

var api = new Api(args.key);
api.info(function(err, res) {
  if (err) {
    throw err;
  }
  else {
    console.log(res);
  }
});
