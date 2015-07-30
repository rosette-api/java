"use strict";

var Api = require("rosette-api").Api;
var ArgumentParser = require("argparse").ArgumentParser;
var NameTranslationParameters = require("rosette-api").NameTranslationParameters;

var parser = new ArgumentParser({
  addHelp: true,
  description: "Translate a name from one language to another"
});
parser.addArgument(["--key"], {help: "Rosette API key", required: true});
var args = parser.parseArgs();

var translationParams = new NameTranslationParameters();
translationParams.setItem("name", "معمر محمد أبو منيار القذافي‎");
translationParams.setItem("entityType", "PERSON");
translationParams.setItem("targetLanguage", "eng");

var api = new Api(args.key);
api.translatedName(translationParams, function(err, res) {
  if (err) {
    console.log("ERROR! " + err);
  }
  else {
    console.log(res);
  }
});


