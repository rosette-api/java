"use strict";

var githubAPI = require("github");
var prompt = require("prompt");

var fs = require("fs");

var owner = "rosette-api";

var schema = {
  properties: {
    username: {
      required: true
    },
    password: {
      hidden: true,
      message: "password (this will be hidden)",
      required: true
    },
    repo: {
      required: true
    }
  }
};

prompt.start();

prompt.get(schema, function (err, result) {

  var github = new githubAPI({
    // required
    version: "3.0.0",
    // optional
    protocol: "https",
    host: "api.github.com", // should be api.github.com for GitHub
    timeout: 5000
  });

  github.authenticate({
    type: "basic",
    username: result.username,
    password: result.password
  });

  var dir = __dirname + "/../nodejs/production_code/target/github-publish/";
  var packContents = fs.readFileSync(dir + "package.json");
  var version = JSON.parse(packContents).version;

  github.releases.createRelease(
    {owner: owner,
      repo: result.repo,
      tag_name: "v" + version
    }, function(err, res) {
      console.log(res.id);
      github.authenticate({
        type: "basic",
        username: result.username,
        password: result.password
      });

      var name = "rosette-api-" + version + ".tgz";
      github.releases.uploadAsset({
        owner: owner,
        repo: result.repo,
        id: res.id,
        name: name,
        filePath: dir + name
      }, function(err, res) {
        if (err) {
          console.log("ERROR");
          console.log(err);
        }
      });
    });

});