"use strict";

var Api = require("./../lib/rosetteApi");
var ArgumentParser = require('../node_modules/argparse').ArgumentParser;
var DocumentParameters = require("./../lib/DocumentParameters");

var parser = new ArgumentParser({
  addHelp:true,
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
var content = "This land is your land This land is my land \n \
From California to the New York island; \n \
From the red wood forest to the Gulf Stream waters \n \
 \n \
This land was made for you and Me. \n \
 \n \
As I was walking that ribbon of highway, \n \
I saw above me that endless skyway: \n \
I saw below me that golden valley: \n \
This land was made for you and me.";

docParams.setItem("content", content);

var api = new Api(args.key, args.service_url);
var result = api.sentences(docParams);

console.log(result);
