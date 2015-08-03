"use strict";

var fs = require("fs");

var packageName = __dirname + "/package.json";

var contents = fs.readFileSync(packageName).toString();

// Get new version
var version = JSON.parse(contents).version;
var patch = parseInt(version.split(".")[2]);
patch++;
version = version.split(".", 2).join(".") + "." + patch.toString();

// Concat and write to get the new package.json
var begin = contents.split("version")[0];
var end = contents.split("homepage")[1];
fs.writeFileSync(packageName, begin + "version\": \"" + version + "\",\n  \"homepage" + end);
