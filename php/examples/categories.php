<?php
/**
 * Example code to call Rosette API to get a document's (located at given URL) category
 **/

require_once("vendor/autoload.php");    // assuming composer.json is properly configured with Rosette API
use rosette\api\Api;
use rosette\api\DocumentParameters;
use rosette\api\RosetteException;

$options = getopt(null, array("key:", "url::"));
if (!isset($options["key"])) {
    echo "Usage: php " . __FILE__ . " --key <api_key> --url=<alternate_url>\n";
    exit();
}

$api = isset($options["url"]) ? new Api($options["key"], $options["url"]) : new Api($options["key"]);
$api->setVersionChecked(true);
$params = new DocumentParameters();
$params->params["contentUri"] = "http://www.basistech.com/about";

try {
    $result = $api->categories($params);
    var_dump($result);
} catch (RosetteException $e) {
    error_log($e);
}
