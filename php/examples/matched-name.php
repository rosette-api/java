<?php
/**
 * Example code to call Rosette API to get match score (similarity) for two names
 **/

require_once(dirname(__FILE__) . "/../vendor/autoload.php"); // assuming composer.json is properly configured with Rosette API
use rosette\api\Api;
use rosette\api\Name;
use rosette\api\NameMatchingParameters;
use rosette\api\RosetteException;

$options = getopt(null, array("key:", "url::"));
if (!isset($options["key"])) {
    echo "Usage: php " . __FILE__ . " --key <api_key> --url=<alternate_url>\n";
    exit();
}

$api = isset($options["url"]) ? new Api($options["key"], $options["url"]) : new Api($options["key"]);
$params = new NameMatchingParameters(new Name("Michael Jackson"), new Name("迈克尔·杰克逊"));

try {
    $result = $api->matchedName($params);
    var_dump($result);
} catch (RosetteException $e) {
    error_log($e);
}
