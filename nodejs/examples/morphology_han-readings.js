"use strict";

var Api = require("./../lib/Api");
var ArgumentParser = require("argparse").ArgumentParser;
var DocumentParameters = require("./../lib/DocumentParameters");
var rosetteConstants = require("./../lib/rosetteConstants.js");

var parser = new ArgumentParser({
  addHelp: true,
  description: "Get Chinese readings of words in a piece of text"
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
var content = "北京大学生物系主任办公室内部会议";
docParams.setItem("content", content);

var api = new Api(args.key, args.service_url);
api.morphology(docParams, rosetteConstants.morpholoyOutput.HAN_READINGS, function(res) {
  console.log(res);
});

