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
$content = <<<EOF
This land is your land This land is my land
From California to the New York island;
From the red wood forest to the Gulf Stream waters

This land was made for you and Me.

As I was walking that ribbon of highway,
I saw above me that endless skyway:
I saw below me that golden valley:
This land was made for you and me.
EOF;
$params->set('content', $content);

try {
    $result = $api->sentences($params);
    var_dump($result);
} catch (RosetteException $e) {
    error_log($e);
}
