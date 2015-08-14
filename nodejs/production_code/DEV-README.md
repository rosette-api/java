---
#PHP Binding
---
#Summary

All of the Rosette API Node.js Binding functionality is accessed through `rosette-api`.
Endpoints are provided as methods of the Api class and are subject to change as the Rosette API evolves.
The node.js binding requires node version >= 0.12.0 or any io.js version.

#Installation

Current residence: https://git.basistech.net/raas/ws-client-bindings/tree/master/nodejs/production_code

After checkout, it will be necessary to install the required dependencies.
The node binding has no dependencies, but some development dependencies.

Install the dependencies:
```
npm install
```
npm should be included with your node installation.

This will read the `package.json` file and install the required libraries in `node_modules`

#Basic Usage

Examples are in `../examples/*.js`.

Require needed modules from `rosette-api` if you used npm to install it (`npm install rosette-api`) or require it with 
a path ending with the production_code directory. 
```
var Api = require("rosette-api").Api
```
or 
```
var Api = require("../production_code").Api
```
You will need the Api module with others optional (DocumentParameters, NameMatchingParameters, NameTranslationParameters,
Name, and rosetteConstants).

Instantiate an instance of `Api`, providing the required User Key, e.g.
```
var api = new Api(user_key);
```
Call the function for the desired input, providing the necessary parameters, including an error first callback, e.g
```
var result = api.language('Four score and seven years ago', callback);
```
Process the result, which will be JSON that is returned from Rosette API.
If there are any problems along the way, a RosetteException will be thrown and can be handled in a normal fashion.

# Unit Testing
`./tests/mockApiTest.js` is set up to be used with Nodeunit.  To run, cd to the production_code directory or any of its 
subdirectories and run
```
grunt test
```
It will run through the tests using mocked data and provide a message if any fail.

# Documentation
The code is documented such that documentation may be generated using one of the common documentation
tools, e.g. JSDoc .  

JSDoc is the current method and the output will be placed in `./target/html` when run with maven.

# Release to Packagist
Packagist is configured to automatically poll php binding's github repo, so nothing to do here
other than following the code publishing procedure.