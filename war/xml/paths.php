<?php
    $data = file_get_contents('risk-paths.svg');
    $svgElements = new SimpleXMLElement($data);

    foreach ($svgElements as $element) {
        $continents =   array('Europe', 'Asia', 'NorthAmerica', 'Australia', 'SouthAmerica', 'Africa');
        if (!in_array($element['id'], $continents ))
            echo $element['id'] . ': { path: \'' . $element['d'] . '\'},<br>';
    }
?>
