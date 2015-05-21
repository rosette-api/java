---
#PHP Binding
---
#Summary

All of the Rosette API PHP Binding functionality is accessed through \rosette\api\Api.  Endpoints are provided as methods of the API class and are subject to change as the Rosette API evolves.

#Installation

Current residence: TBD

After checkout, it will be necessary to install the required dependencies.  The PHP binding has one dependency, Requests, which encapsulates the HTTP functionality.

Install the  using composer:
```
> composer update
```
No composer?  See [Installing Composer](https://getcomposer.org/doc/00-intro.md#installation-linux-unix-osx)

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
