# hmmmath [![Build Status](https://travis-ci.org/lstrojny/hmmmath.png?branch=master)](https://travis-ci.org/lstrojny/hmmmath)

Delicious math component for PHP


## Fibonacci number sequences

```php
<?php
use hmmmath\Fibonacci\FibonacciFactory;

foreach (FibonacciFactory::sequence(0, 1) as $number) {
    var_dump($number);
}
```

Will output:
```
int(0)
int(1)
int(1)
int(2)
int(3)
int(5)
int(8)
int(13)
int(21)
int(34)
int(55)
int(89)
int(144)
...
```
