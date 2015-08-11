<?php
return Symfony\CS\Config\Config::create()
    ->fixers(array('long_array_syntax', 'ordered_use', 'concat_with_spaces', 'strict', 'strict_param', 'no_empty_lines_after_phpdocs', '-single_blank_line_before_namespace'))
    ->finder(Symfony\CS\Finder\DefaultFinder::create()
            ->path('/(^source\/)|(^tests\/)|(^examples\/)/')
            ->name('*.php'));
