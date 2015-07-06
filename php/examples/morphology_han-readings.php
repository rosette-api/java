<?php

/**
 * Example code to call Rosette API to get Chinese readings for words in a piece of text.
 **/
require_once dirname(__FILE__).'/../source/rosette/api/Api.php';
use rosette\api\Api;
use rosette\api\DocumentParameters;
use rosette\api\RosetteConstants;
use rosette\api\RosetteException;

$options = getopt(null, array('key:', 'url::'));
if (!isset($options['key'])) {
    echo 'Usage: php '.__FILE__." --key <api_key> --url=<alternate_url>\n";
    exit();
}

$api = isset($options['url']) ? new Api($options['key'], $options['url']) : new Api($options['key']);
$params = new DocumentParameters();
$params->set('content', '北京大学生物系主任办公室内部会议');

try {
    $result = $api->morphology($params, RosetteConstants::$MorphologyOutput['HAN_READINGS']);
    var_dump($result);
} catch (RosetteException $e) {
    error_log($e);
}
