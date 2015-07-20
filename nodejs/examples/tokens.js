"use strict";

var Api = require("./../lib/Api");
var ArgumentParser = require("argparse").ArgumentParser;
var DocumentParameters = require("./../lib/DocumentParameters");

var parser = new ArgumentParser({
  addHelp: true,
  description: "Get the words in a piece of text"
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
var result = api.tokens(docParams);

console.log(result);
