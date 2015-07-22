"use strict";

var Api = require("./../lib/AsyncApi");
var ArgumentParser = require("argparse").ArgumentParser;
var NameTranslationParameters = require("./../lib/NameTranslationParameters");

var parser = new ArgumentParser({
  addHelp: true,
  description: "Translate a name from one language to another"
});
parser.addArgument(
  ["--key"],
  {
    help: "Rosette API key",
    defaultValue: "7eb3562318e5242b5a89ad80011f1e22",
    //required: true
  }
);
parser.addArgument(
  ["--service_url"],
  {
    help: "Optional user service URL"
  }
);
var args = parser.parseArgs();

var translationParams = new NameTranslationParameters();
translationParams.setItem("name", "معمر محمد أبو منيار القذافي‎");
translationParams.setItem("entityType", "PERSON");
translationParams.setItem("targetLanguage", "eng");

console.log(translationParams);

var api = new Api(args.key, args.service_url);
api.translatedName(translationParams, function(res) {
  console.log(res);
});


