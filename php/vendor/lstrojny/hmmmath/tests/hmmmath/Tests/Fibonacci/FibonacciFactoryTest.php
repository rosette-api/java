<?php
namespace hmmmath\Tests\Fibonacci;

use hmmmath\Fibonacci\FibonacciFactory;
use InterNations\Component\Testing\AbstractTestCase;
use LimitIterator;

class FibonacciFactoryTest extends AbstractTestCase
{
    public function testCreateDefaultSequence()
    {
        $sequence = FibonacciFactory::sequence();
        $this->assertInstanceOf('hmmmath\Fibonacci\FibonacciSequence', $sequence);
        $this->assertSame(
            [0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377],
            iterator_to_array(new LimitIterator($sequence, 0, 15)),
            'Simple sequence'
        );
    }

    public function testSpecificSequence()
    {
        $sequence = FibonacciFactory::sequence(0, 2);
        $this->assertInstanceOf('hmmmath\Fibonacci\FibonacciSequence', $sequence);
        $this->assertSame(
            [0, 2, 2, 4, 6, 10, 16, 26, 42, 68, 110, 178, 288, 466, 754],
            iterator_to_array(new LimitIterator($sequence, 0, 15)),
            'Sequence x2'
        );
    }

    public function testLimitingSequence()
    {
        $sequence = FibonacciFactory::sequence(0, 1, 5);
        $this->assertInstanceOf('LimitIterator', $sequence);
        $this->assertSame(
            [0, 1, 1, 2, 3],
            iterator_to_array($sequence),
            'Limited sequence'
        );
        $this->assertSame(
            [0, 1, 1, 2, 3],
            iterator_to_array($sequence),
            'Rewind behavior'
        );
    }

    public function testSequenceWithLimitAndOffset()
    {
        $sequence = FibonacciFactory::sequence(0, 1, 3, 2);
        $this->assertInstanceOf('LimitIterator', $sequence);
        $this->assertSame(
            [2 => 1, 3 => 2, 4 => 3],
            iterator_to_array($sequence),
            'Limited sequence'
        );
        $this->assertSame(
            [2 => 1, 3 => 2, 4 => 3],
            iterator_to_array($sequence),
            'Rewind behavior'
        );
    }

    public function testExceptionIsThrownIfLimitIsNotAPositiveNumber()
    {
        $this->setExpectedException(
            'hmmmath\Exception\InvalidArgumentException',
            'Expected parameter #3 to be of type "integer<unsigned>", "integer" given'
        );
        FibonacciFactory::sequence(0, 1, -1);
    }

    public function testExceptionIsThrownIfLimitIsNotAPositiveNumber_2()
    {
        $this->setExpectedException(
            'hmmmath\Exception\InvalidArgumentException',
            'Expected parameter #3 to be of type "integer<unsigned>", "string" given'
        );
        FibonacciFactory::sequence(0, 1, 'foo');
    }

    public function testExceptionIsThrownIfOffsetIsNotAPositiveNumber()
    {
        $this->setExpectedException(
            'hmmmath\Exception\InvalidArgumentException',
            'Expected parameter #4 to be of type "integer<unsigned>", "integer" given'
        );
        FibonacciFactory::sequence(0, 1, 0, -1);
    }

    public function testExceptionIsThrownIfOffsetIsNotAPositiveNumber_2()
    {
        $this->setExpectedException(
            'hmmmath\Exception\InvalidArgumentException',
            'Expected parameter #4 to be of type "integer<unsigned>", "string" given'
        );
        FibonacciFactory::sequence(0, 1, 0, 'foo');
    }

    public function testCreateNumber()
    {
        $number = FibonacciFactory::number();
        $this->assertSame(0, $number->getCurrent());
        $this->assertSame(1, $number->getNext()->getCurrent());
        $this->assertSame(1, $number->getNext()->getNext()->getCurrent());
        $this->assertSame(2, $number->getNext()->getNext()->getNext()->getCurrent());
        $this->assertSame(3, $number->getNext()->getNext()->getNext()->getNext()->getCurrent());
        $this->assertSame(5, $number->getNext()->getNext()->getNext()->getNext()->getNext()->getCurrent());
    }
}
