<?php
return Symfony\CS\Config\Config::create()
    ->fixers(array('long_array_syntax', 'ordered_use', 'concat_with_spaces', 'strict', 'strict_param'))
    ->finder(Symfony\CS\Finder\DefaultFinder::create()
            ->path('/(^source\/)|(^tests\/)|(^examples\/)/')
            ->name('*.php'));
