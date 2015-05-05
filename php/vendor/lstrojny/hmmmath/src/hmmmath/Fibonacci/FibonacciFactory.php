<?php
namespace hmmmath\Fibonacci;

use hmmmath\Exception\InvalidArgumentException;
use Traversable;
use LimitIterator;

class FibonacciFactory
{
    /**
     * Create fibonacci sequence
     *
     * The sequence is infinite but the factory method optionally takes a third argument to limit the sequence. A fourth
     * parameter can be passed to specify an offset for the sequence.
     *
     * @param integer $start
     * @param integer $increment
     * @param integer $limit
     * @param integer $offset
     * @return Traversable
     */
    public static function sequence($start = 0, $increment = 1, $limit = 0, $offset = 0)
    {
        $sequence = new FibonacciSequence($start, $increment);

        InvalidArgumentException::assertParameterType(3, 'integer', $limit, 'unsigned');
        InvalidArgumentException::assertParameterType(4, 'integer', $offset, 'unsigned');
        if ($limit > 0) {
            $sequence = new LimitIterator($sequence, $offset, $limit);
        }

        return $sequence;
    }

    /**
     * Create fibonacci number
     *
     * @param integer $start
     * @param integer $increment
     * @return FibonacciNumber
     */
    public static function number($start = 0, $increment = 1)
    {
        return new FibonacciNumber($start, $increment);
    }

    final private function __construct()
    {
    }

    final private function __destruct()
    {
    }

    final private function __clone()
    {
    }
}
