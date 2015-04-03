<?php
require_once("vendor/autoload.php");

use predictionio\EventClient;

$accessKey = 'V6OHYLmGIXTZrwkNXl1sFbMrTxDWexEZPskFqzDjc8Fwf6LdvIesbTeXdNBNKL1v';
$client = new EventClient($accessKey, 'http://localhost:7070');

$handle = fopen("./20news-bydate-train-stanford-classifier.txt", "r");
if ($handle) {
  while (($line = fgets($handle)) !== false) {
    $exploded = explode("\t", $line);
    $response = $client->createEvent(array(
      'event' => '$set',
      'entityType' => 'news',
      'entityId' => trim($exploded[1]),
      'properties' => array(
        'label' => trim($exploded[0]),
        'text' => trim($exploded[2])
      )
    ));
    print_r($response);
  }
  fclose($handle);
} else {
  print "Could not open file";
} 

?>
