"use strict";

var Api = require("./../lib/AsyncApi");
var ArgumentParser = require("argparse").ArgumentParser;

var parser = new ArgumentParser({
  addHelp: true,
  description: "Send ping to check for reachability of Rosette API"
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

var api = new Api(args.key, args.service_url);
api.ping(function(res) {
  console.log(res);
});

