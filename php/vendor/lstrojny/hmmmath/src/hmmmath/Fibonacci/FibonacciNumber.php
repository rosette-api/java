<?php
namespace hmmmath\Fibonacci;

use hmmmath\Exception\InvalidArgumentException;

class FibonacciNumber
{
    /** @var integer */
    private $start;

    /** @var integer */
    private $increment;

    /**
     * @param integer $start
     * @param integer $increment
     */
    public function __construct($start, $increment)
    {
        $this->start = InvalidArgumentException::assertParameterType(1, 'integer', $start);
        $this->increment = InvalidArgumentException::assertParameterType(2, 'integer', $increment);
    }

    /**
     * Get current number in fibonacci sequence
     *
     * @return integer
     */
    public function getCurrent()
    {
        return $this->start;
    }

    /**
     * Get next in fibonacci sequence
     *
     * @return FibonacciNumber
     */
    public function getNext()
    {
        return new self($this->start + $this->increment, $this->start);
    }
}
