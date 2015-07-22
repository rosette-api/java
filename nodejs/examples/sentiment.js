"use strict";

var Api = require("./../lib/Api");
var DocumentParameters = require("./../lib/DocumentParameters");
var ArgumentParser = require("argparse").ArgumentParser;
var fs = require("fs");

var parser = new ArgumentParser({
  addHelp: true,
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
    help: "Optional input file for data"
  }
);
var args = parser.parseArgs();

var docParams = new DocumentParameters();

var useTemp = false;
// Create a temp example file if none provided
if (!args.file) {
  useTemp = true;
  var dirname = "tmp";
  var counter = 0;
  while (fs.existsSync(dirname + counter)) {
    counter++;
  }
  dirname = dirname + counter;
  fs.mkdir(dirname);

  var fileContents = "<html><head><title>Performance Report</title></head>";
  fileContents += "<body><p>This article is clean, concise, and very easy to read.</p></body></html>";
  fs.writeFileSync(dirname + "/example.html", fileContents);
  args.file = dirname + "/example.html";
}

docParams.loadDocumentFile(args.file);

if (useTemp) {
  fs.unlinkSync(dirname + "/example.html");
  fs.rmdirSync(dirname);
}
var api = new Api(args.key, args.service_url);
api.sentiment(docParams, function(res) {
  console.log(res);
});

