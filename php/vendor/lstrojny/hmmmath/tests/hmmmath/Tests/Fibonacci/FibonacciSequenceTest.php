<?php
namespace hmmmath\Tests\Fibonacci;

use hmmmath\Fibonacci\FibonacciSequence;
use InterNations\Component\Testing\AbstractTestCase;
use LimitIterator;

class FibonacciSequenceTest extends AbstractTestCase
{
    public function testClassicSequence()
    {
        $sequence = new FibonacciSequence();

        $this->assertSame(
            [0, 1, 1, 2, 3, 5, 8, 13, 21, 34],
            iterator_to_array(new LimitIterator($sequence, 0, 10)),
            'Simple sequence'
        );
        $this->assertSame(
            [0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377],
            iterator_to_array(new LimitIterator($sequence, 0, 15)),
            'Retry to test rewind()'
        );
    }

    public function testClassicalSequence_2()
    {
        $sequence = new FibonacciSequence(0, 1);

        $this->assertSame(
            [0, 1, 1, 2, 3, 5, 8, 13, 21, 34],
            iterator_to_array(new LimitIterator($sequence, 0, 10)),
            'Simple sequence'
        );
        $this->assertSame(
            [0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377],
            iterator_to_array(new LimitIterator($sequence, 0, 15)),
            'Retry to test rewind()'
        );
    }

    public function testVaryIncrement()
    {
        $sequence = new FibonacciSequence(0, 2);

        $this->assertSame(
            [0, 2, 2, 4, 6, 10, 16, 26, 42, 68],
            iterator_to_array(new LimitIterator($sequence, 0, 10)),
            'Simple sequence'
        );
        $this->assertSame(
            [0, 2, 2, 4, 6, 10, 16, 26, 42, 68],
            iterator_to_array(new LimitIterator($sequence, 0, 10)),
            'Retry to test rewind()'
        );
    }

    public function testArrayAccessOnSequence()
    {
        $sequence = new FibonacciSequence();

        $this->assertTrue(isset($sequence[0]));
        $this->assertSame(0, $sequence[0]);
        $this->assertTrue(isset($sequence[1]));
        $this->assertSame(1, $sequence[1]);
        $this->assertTrue(isset($sequence[2]));
        $this->assertSame(1, $sequence[2]);
        $this->assertTrue(isset($sequence[3]));
        $this->assertSame(2, $sequence[3]);
        $this->assertTrue(isset($sequence[4]));
        $this->assertSame(3, $sequence[4]);
        $this->assertTrue(isset($sequence[5]));
        $this->assertSame(5, $sequence[5]);
        $this->assertTrue(isset($sequence[6]));
        $this->assertSame(8, $sequence[6]);
        $this->assertTrue(isset($sequence[7]));
        $this->assertSame(13, $sequence[7]);
    }

    public function testExceptionThrownOnOffsetSet()
    {
        $sequence = new FibonacciSequence();

        $this->setExpectedException(
            'hmmmath\Exception\BadMethodCallException',
            'Bad method call to hmmmath\Fibonacci\FibonacciSequence::offsetSet()'
        );
        $sequence[0] = 'test';
    }

    public function testExceptionIsThrownOnOffsetUnset()
    {
        $sequence = new FibonacciSequence();

        $this->setExpectedException(
            'hmmmath\Exception\BadMethodCallException',
            'Bad method call to hmmmath\Fibonacci\FibonacciSequence::offsetUnset()'
        );
        unset($sequence[0]);
    }
}
