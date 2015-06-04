<?php
/**
 * Example code to call Rosette API to get a document's sentiment from a local file
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
$content = <<<EOF
<html><head><title>Performance Report</title></head>
<body><p>This article is clean, concise, and very easy to read.</p></body></html>
EOF;
$temp = tmpfile();  // write above html content to a temp file
fwrite($temp, $content);
$params->loadDocumentFile(stream_get_meta_data($temp)["uri"]);

try {
    $result = $api->sentiment($params);
    var_dump($result);
} catch (RosetteException $e) {
    error_log($e);
}

fclose($temp);  // clean up the temp file
