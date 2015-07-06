<?php

/**
 * Example code to call Rosette API to get information such as version and build.
 **/
require_once dirname(__FILE__).'/../vendor/autoload.php'; // assuming composer.json is properly configured with Rosette API
use rosette\api\Api;

$options = getopt(null, array('key:', 'url::'));
if (!isset($options['key'])) {
    echo 'Usage: php '.__FILE__." --key <api_key> --url=<alternate_url>\n";
    exit();
}

$api = isset($options['url']) ? new Api($options['key'], $options['url']) : new Api($options['key']);

try {
    $result = $api->info();
    var_dump($result);
} catch (RosetteException $e) {
    error_log($e);
}
