"use strict";

module.exports = function(grunt) {

  // Project configuration.
  grunt.initConfig({
    nodeunit: {
      all: ["tests/**/*Test.js"],
      mocked: ["tests/mockApiTest.js"]
    },
    eslint: {
      lib: {
        src: ["lib/**/*.js", "examples/**/*.js"]
      },
      test: {
        src: ["tests/**/*.js"]
      },
      options: {
        configFile: "conf/eslint.json"
      },
      gruntfile: {
        src: "Gruntfile.js"
      }
    },
    jsdoc: {
      dist: {
        src: ["lib/*.js", "tests/*.js"],
        options: {
          destination: "target/html"
        }
      }
    },
    instrument: {
      files: "lib/*.js",
      options: {
        lazy: true,
        basePath: "target/instrumented"
      }
    },
    storeCoverage: {
      options: {
        dir: "target"
      }
    },
    makeReport: {
      src: "target/coverage.json",
      options: {
        type: "lcov",
        dir: "target/reports",
        print: "detail"
      }
    },
    watch: {
      gruntfile: {
        files: "<%= eslint.gruntfile.lib %>",
        tasks: ["eslint:gruntfile"]
      },
      lib: {
        files: "<%= eslint.lib.src %>",
        tasks: ["eslint:lib", "nodeunit"]
      },
      test: {
        files: "<%= eslint.test.src %>",
        tasks: ["eslint:test", "nodeunit"]
      }
    }
  });

  // These plugins provide necessary tasks.
  grunt.loadNpmTasks("grunt-contrib-nodeunit");
  grunt.loadNpmTasks("grunt-contrib-watch");
  grunt.loadNpmTasks("grunt-eslint");
  grunt.loadNpmTasks("grunt-istanbul");
  grunt.loadNpmTasks("grunt-jsdoc");

  // Task definitions.
  // run `grunt <task>` in command line and it will run the sequence in brackets
  grunt.registerTask("default", ["eslint", "test", "jsdoc"]);
  grunt.registerTask("doc", ["jsdoc"]);
  grunt.registerTask("lint", ["eslint"]);
  grunt.registerTask("test", ["instrument", "nodeunit", "storeCoverage", "makeReport"]); // with coverage report
  grunt.registerTask("mockedTests", ["instrument", "nodeunit:mocked", "storeCoverage", "makeReport"]); // just mocked

};
