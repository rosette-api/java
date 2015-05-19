---
#PHP Binding
---
#Summary

All of the Rosette API PHP Binding functionality is accessed through \BasisTechnology\Rosette\API.  Endpoints are provided as functions off of the API class and are subject to change as the Rosette API evolves.

#Installation

After obtaining from github, run from the root directory
```
> composer update
```
This will read the composer.json file and install the required libraries as well as set up the autoload.php which is used in the phpunit bootstrapping and can also be included in client code.

#Basic Usage

A good reference is apiTest.php.


Instantiate an instance of the api, providing the Rosette API URL and the required User Key, e.g.
```
$api = new $Api($url, $userKey);
```
Call the function for the desired input, providing the necessary parameters, if any, e.g
```
$result = $Api->Language('Four score and seven years ago');
```
Process the result, which will be a named array based on the JSON that is returned from Rosette API.  If there are any problems along the way, a RosetteException will be thrown and can be handled in a normal fashion.
# Unit Testing
apiTest.php is set up to be used with PHPUnit.  To run, cd to the root location of the PHP Binding tree (source, test and vendor are subdirectories) and type:
```
phpunit -v --bootstrap ./vendor/autoload.php ./tests/rosette/api/APITest.php
```
It will run through the tests and provide a message if any fail.  The tests currently run on a built-in php server that is started when the tests begin.  To test against a different server, simply edit the $testUrl listed in the test class variables.
```
public $testUrl = 'http://localhost:8082';
```
# Installing PHPUnit
If PHPUnit is not installed, the easiest way to install it is by using Composer.  If Composer isn't installed, install it as:
```
curl -sS https://getcomposer.org/installer | php
mv composer.phar /usr/local/bin/composer
```
Then run:
```
composer global require "phpunit/phpunit=4.6.*"
```
#Vendor directory
The vendor directory contains the libraries necessary for both the api and unit testing.  Only ./vendor/rmccue/requests is required by the api itself.  The other vendor libraries are necessary for supporting the built-in server mocking.


