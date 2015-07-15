"use strict";

module.exports = function(grunt) {

  // Project configuration.
  grunt.initConfig({
    nodeunit: {
      files: ["tests/**/*Test.js"]
    },
    eslint: {
      lib: {
        src: ["lib/**/*.js"]
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
        basePath: "instrumented"
      }
    },
    storeCoverage: {
      options: {
        dir: "instrumented"
      }
    },
    makeReport: {
      src: "instrumented/coverage.json",
      options: {
        type: "lcov",
        dir: "reports",
        print: "detail"
      }
    },
    watch: {
      gruntfile: {
        files: "<%= eslint.gruntfile.lib %>",
        tasks: ["jshint:gruntfile"]
      },
      lib: {
        files: "<%= eslint.lib.src %>",
        tasks: ["jshint:lib", "nodeunit"]
      },
      test: {
        files: "<%= eslint.test.src %>",
        tasks: ["jshint:test", "nodeunit"]
      }
    }
  });

  // These plugins provide necessary tasks.
  grunt.loadNpmTasks("grunt-contrib-nodeunit");
  grunt.loadNpmTasks("grunt-contrib-watch");
  grunt.loadNpmTasks("grunt-eslint");
  grunt.loadNpmTasks("grunt-istanbul");
  grunt.loadNpmTasks("grunt-jsdoc");

  // Task definititions.
  // run `grunt <task>` in command line and it will run the sequence in brackets
  grunt.registerTask("default", ["eslint", "test", "jsdoc"]);
  grunt.registerTask("document", ["jsdoc"]);
  grunt.registerTask("lint", ["eslint"]);
  grunt.registerTask("test", ["instrument", "nodeunit", "storeCoverage", "makeReport"]); // with coverage report

};
