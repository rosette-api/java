---
#PHP Binding
---
#Summary

All of the Rosette API PHP Binding functionality is accessed through `\rosette\api\Api`.  Endpoints are provided as methods of the API class and are subject to change as the Rosette API evolves.

#Installation

Current residence: https://git.basistech.net/raas/ws-client-bindings/tree/master/php

After checkout, it will be necessary to install the required dependencies.  The PHP binding has one dependency, Requests, which encapsulates the HTTP functionality.  It also has one development dependency, Mock HTTP Server for PHP, which provides a PHP server instance for validating HTTP calls.

Install the dependencies:
```
composer update
```
No composer?  See [Installing Composer](https://getcomposer.org/doc/00-intro.md#installation-linux-unix-osx). Make sure after this `composer` is on your `$PATH`.

This will read the `composer.json` file and install the required vendor libraries as well as set up a `./vendor/autoload.php` which is used for phpunit bootstrapping and can also be included in client code to provide dependency resolution.

#Basic Usage

Reference is `./tests/rosette/api/apiTest.php`.


Instantiate an instance of `\rosette\api\Api`, providing the Rosette API URL and the required User Key, e.g.
```
$api = new $Api($url, $userKey);
```
Call the function for the desired input, providing the necessary parameters, if any, e.g
```
$result = $Api->Language('Four score and seven years ago');
```
Process the result, which will be a named array based on the JSON that is returned from Rosette API.  If there are any problems along the way, a RosetteException will be thrown and can be handled in a normal fashion.

# Unit Testing
`./tests/rosette/api/apiTest.php` is set up to be used with PHPUnit.  To run, cd to the root location of the PHP Binding tree (`source`, `test` and `vendor` are subdirectories) and type:
```
phpunit -v --bootstrap ./vendor/autoload.php ./tests/rosette/api/APITest.php
```
It will run through the tests and provide a message if any fail.  The tests currently run on a built-in php server that is started when the tests begin.  To test against a different server, simply edit the $testUrl listed in the test class variables.
```
public $testUrl = 'http://localhost:8082';
```
Note: When running against the mock server, both the `$testUrl` and the `setupBeforeClass()` entry must match.  To use a different IP and/or port, edit accordingly.

# Installing PHPUnit

See [Installing PHPUnit](https://phpunit.de/manual/current/en/installation.html). Make sure after this `phpunit` is on your `$PATH`.

# Documentation
The code is documented such that documentation may be generated using one of the common documentation tools, e.g. phpDocumentor, dOxygen .  

[phpDocumentor Installation Instructions](https://phpunit.de/manual/current/en/installation.html). Make sure after this `phpdoc` is on your `$PATH`.

[dOxygen Installation Instructions](http://www.stack.nl/~dimitri/doxygen/download.html#gitrepos)

Example - To document the source using phpDocumentor:
```
phpdoc -d ./source -t ./target/html
```
The output will be placed in `./target/html`.

If it gives your warnings like this:

```
PHP Warning:  date_default_timezone_get(): It is not safe to rely on the system's timezone settings. ...
```

You can mute these by editing your `php.ini` file and add a line like this: `date.timezone = America/New_York`. If you don't know where `php.ini` is, run `php -i | php.ini`.
