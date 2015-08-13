<?php

/**
 * Example code to call Rosette API to get sentences in a piece of text.
 **/
require_once dirname(__FILE__) . '/../source/rosette/api/Api.php';
use rosette\api\Api;
use rosette\api\DocumentParameters;
use rosette\api\RosetteException;

$options = getopt(null, array('key:', 'url::'));
if (!isset($options['key'])) {
    echo 'Usage: php ' . __FILE__ . " --key <api_key> --url=<alternate_url>\n";
    exit();
}

$api = isset($options['url']) ? new Api($options['key'], $options['url']) : new Api($options['key']);
$params = new DocumentParameters();
$content = "${sentences_data}";
$params->set('content', $content);

try {
    $result = $api->sentences($params);
    var_dump($result);
} catch (RosetteException $e) {
    error_log($e);
}
