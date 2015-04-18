<?php
    $data = file_get_contents('backgroundLayer.svg');
    $svgElements = new SimpleXMLElement($data);

    foreach ($svgElements as $element) {
        echo $element['id'] . ': { path: \'' . $element['d'] . '\', fill: \'' .$element['fill'] . '\'},<br>';
    }
?>
