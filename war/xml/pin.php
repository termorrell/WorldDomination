<?php
    $data = file_get_contents('pin.svg');
    $svgElements = new SimpleXMLElement($data);

    foreach ($svgElements as $element) {
        echo 'pin: { path: \'' . $element['d'] . '\'},<br>';
    }
?>
