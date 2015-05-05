<?php
namespace hmmmath\Fibonacci;

use ArrayAccess;
use Iterator;
use hmmmath\Exception\BadMethodCallException;

class FibonacciSequence implements Iterator, ArrayAccess
{
    /** @var FibonacciNumber */
    private $number;

    /** @var FibonacciNumber */
    private $initial;

    /** @var integer */
    private $key = 0;

    public function __construct($start = 0, $increment = 1)
    {
        $this->number = $this->initial = new FibonacciNumber($start, $increment);
    }

    /** @return integer */
    public function current()
    {
        return $this->number->getCurrent();
    }

    /** @return integer */
    public function key()
    {
        return $this->key;
    }

    /** @return boolean */
    public function valid()
    {
        return true;
    }

    public function rewind()
    {
        $this->key = 0;
        $this->number = $this->initial;
    }

    public function next()
    {
        ++$this->key;
        $this->number = $this->number->getNext();
    }

    public function offsetExists($offset)
    {
        return true;
    }

    public function offsetGet($offset)
    {
        $number = $this->initial;
        while ($offset-- > 0) {
            $number = $number->getNext();
        }

        return $number->getCurrent();
    }

    public function offsetSet($offset, $value)
    {
        throw BadMethodCallException::invalidMethod(__CLASS__, __FUNCTION__);
    }

    public function offsetUnset($offset)
    {
        throw BadMethodCallException::invalidMethod(__CLASS__, __FUNCTION__);
    }
}
