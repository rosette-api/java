---
#PHP Binding
---
#Summary

All of the Rosette API PHP Binding functionality is accessed through \rosette\api\Api.  Endpoints are provided as methods of the API class and are subject to change as the Rosette API evolves.

#Installation

Current residence: https://git.basistech.net/raas/ws-client-bindings/tree/master/php

After checkout, it will be necessary to install the required dependencies.  The PHP binding has one dependency, Requests, which encapsulates the HTTP functionality.  It also has one development dependency, Mock HTTP Server for PHP, which provides a PHP server instance for validating HTTP calls.

Install the dependencies:
```
> composer update
```
No composer?  See below.

This will read the composer.json file and install the required vendor libraries as well as set up a ./vendor/autoload.php which is used for phpunit bootstrapping and can also be included in client code to provide dependency resolution.

#Basic Usage

Reference is ./tests/rosette/api/apiTest.php.


Instantiate an instance of \rosette\api\Api, providing the Rosette API URL and the required User Key, e.g.
```
$api = new $Api($url, $userKey);
```
Call the function for the desired input, providing the necessary parameters, if any, e.g
```
$result = $Api->Language('Four score and seven years ago');
```
Process the result, which will be a named array based on the JSON that is returned from Rosette API.  If there are any problems along the way, a RosetteException will be thrown and can be handled in a normal fashion.
# Unit Testing
./tests/rosette/api/apiTest.php is set up to be used with PHPUnit.  To run, cd to the root location of the PHP Binding tree (source, test and vendor are subdirectories) and type:
```
phpunit -v --bootstrap ./vendor/autoload.php ./tests/rosette/api/APITest.php
```
It will run through the tests and provide a message if any fail.  The tests currently run on a built-in php server that is started when the tests begin.  To test against a different server, simply edit the $testUrl listed in the test class variables.
```
public $testUrl = 'http://localhost:8082';
```
Note: When running against the mock server, both the $testUrl and the setupBeforeClass() entry must match.  To use a different IP and/or port, edit accordingly.
# Installing PHPUnit (and Composer)
If PHPUnit is not installed, the easiest way to install it is by using Composer.  If Composer isn't installed, install it as:
```
curl -sS https://getcomposer.org/installer | php
mv composer.phar /usr/local/bin/composer
```
Then run:
```
composer global require "phpunit/phpunit=4.6.*"
```
Note: Make sure you have ~/.composer/vendor/bin/ in your path.
# Documentation
The code is documented such that documentation may be generated using phpDocumentor.  To install phpDocumentor:
```
pear channel-discover pear.phpdoc.org
pear install phpdoc/phpDocumentor
```
To document the source:
```
phpdoc -d ./source -t ./docs
```
The output will be placed in ./docs
