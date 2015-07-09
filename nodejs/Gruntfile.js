"use strict";

module.exports = function(grunt) {

  // Project configuration.
  grunt.initConfig({
    nodeunit: {
      files: ["test/**/*_test.js"]
    },
    eslint: {
      lib: {
        src: ["lib/**/*.js"]
      },
      test: {
        src: ["test/**/*.js"]
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
        src: ["lib/*.js", "test/*.js"],
        options: {
          destination: "doc"
        }
      }
    },
    watch: {
      gruntfile: {
        files: "<%= eslint.gruntfile.src %>",
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
  grunt.loadNpmTasks("grunt-jsdoc");

  // Task definititions.
  // run `grunt <task>` in command line and it will run the sequence in brackets
  grunt.registerTask("default", ["eslint", "nodeunit", "jsdoc"]);
  grunt.registerTask("test", ["nodeunit"]);
  grunt.registerTask("document", ["jsdoc"]);
  grunt.registerTask("lint", ["eslint"]);

};
