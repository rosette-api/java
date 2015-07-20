"use strict";

var Api = require("./../lib/Api");
var ArgumentParser = require("argparse").ArgumentParser;
var DocumentParameters = require("./../lib/DocumentParameters");

var parser = new ArgumentParser({
  addHelp: true,
  description: "Get sentences from a piece of text"
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
var content = "This land is your land This land is my land\n";
content += "From California to the New York island;\n";
content += "From the red wood forest to the Gulf Stream waters\n";
content += "\n";
content += "This land was made for you and Me.\n";
content += "\n";
content += "As I was walking that ribbon of highway,\n";
content += "I saw above me that endless skyway:\n";
content += "I saw below me that golden valley:\n";
content += "This land was made for you and me.";

docParams.setItem("content", content);

var api = new Api(args.key, args.service_url);
var result = api.sentences(docParams);

console.log(result);
