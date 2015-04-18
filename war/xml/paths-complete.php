<?php
    $data = file_get_contents('risk-paths-complete.svg');
    $svgElements = new SimpleXMLElement($data);
    $svgElements->registerXPathNamespace('svg', 'http://www.w3.org/2000/svg');

    $outlines = $svgElements->g[0]->path;
    $continents = $svgElements->g[1]->path;
    $territories = $svgElements->g[2]->path;
    $centres = $svgElements->g[3]->path;
    $lines = $svgElements->g[4]->line;

    echo count($continents) . '<br>';
    echo count($territories) . '<br>';
    echo count($centres) . '<br>';
    echo count($lines) . '<br>';
    echo count($outlines) . '<br>';

    foreach ($continents as $element) {
        echo $element['id'] . ': { path: \'' . $element['d'] . '\', fill: \'' . $element['fill'] . '\'},<br>';
    }

    foreach ($territories as $element) {

        $center;
        foreach ($centres as $centerElement) {
            if ($element['id'] . 'Centre' == $centerElement['id']) {
                $center = $centerElement['d'];
                $center = str_replace('M', '', $center);
            }
        }

        echo $element['id'] . ': { path: \'' . $element['d'] . '\', fill: \'' . $element['fill'] . '\', center: { x: ' . substr($center, 0, strpos($center, ',')) . ', y: ' .  substr($center, strpos($center, ',') + 1, strlen($center)) . '}},<br>';
    }

    foreach ($lines as $element) {
        echo $element['id'] . ': { x1: ' . $element['x1'] . ', y1: ' . $element['y1'] . ', x2: ' . $element['x2'] . ', y2: ' . $element['y2'] . '},<br>';
    }

    foreach($outlines as $element) {
        echo $element['id'] . ': { path: \'' . $element['d'] . '\'},<br>';
    }
