<?php
namespace hmmmath\Tests\Fibonacci;

use hmmmath\Fibonacci\FibonacciNumber;
use InterNations\Component\Testing\AbstractTestCase;
use stdClass;

class FibonacciNumberTest extends AbstractTestCase
{
    public function testGrowLikeARabbitPopulation_Positive()
    {
        $number = new FibonacciNumber(0, 1);

        $this->assertSame(0, $number->getCurrent());
        $this->assertSame(1, $number->getNext()->getCurrent());
        $this->assertSame(1, $number->getNext()->getNext()->getCurrent());
        $this->assertSame(2, $number->getNext()->getNext()->getNext()->getCurrent());
        $this->assertSame(3, $number->getNext()->getNext()->getNext()->getNext()->getCurrent());
        $this->assertSame(5, $number->getNext()->getNext()->getNext()->getNext()->getNext()->getCurrent());
        $this->assertSame(8, $number->getNext()->getNext()->getNext()->getNext()->getNext()->getNext()->getCurrent());
        $this->assertSame(13, $number->getNext()->getNext()->getNext()->getNext()->getNext()->getNext()->getNext()->getCurrent());
    }

    public function testGrowLikeARabbitPopulation_Negative()
    {
        $number = new FibonacciNumber(0, -1);

        $this->assertSame(0, $number->getCurrent());
        $this->assertSame(-1, $number->getNext()->getCurrent());
        $this->assertSame(-1, $number->getNext()->getNext()->getCurrent());
        $this->assertSame(-2, $number->getNext()->getNext()->getNext()->getCurrent());
        $this->assertSame(-3, $number->getNext()->getNext()->getNext()->getNext()->getCurrent());
        $this->assertSame(-5, $number->getNext()->getNext()->getNext()->getNext()->getNext()->getCurrent());
        $this->assertSame(-8, $number->getNext()->getNext()->getNext()->getNext()->getNext()->getNext()->getCurrent());
        $this->assertSame(-13, $number->getNext()->getNext()->getNext()->getNext()->getNext()->getNext()->getNext()->getCurrent());
    }

    public function testDifferentInitialNumber()
    {
        $number = new FibonacciNumber(10, 20);
        $this->assertSame(10, $number->getCurrent());
        $this->assertSame(30, $number->getNext()->getCurrent());
        $this->assertSame(40, $number->getNext()->getNext()->getCurrent());
        $this->assertSame(70, $number->getNext()->getNext()->getNext()->getCurrent());
        $this->assertSame(110, $number->getNext()->getNext()->getNext()->getNext()->getCurrent());
    }

    public function testExceptionIsThrownIfCurrentNumberIsNotAnInteger()
    {
        $this->setExpectedException(
            'hmmmath\Exception\InvalidArgumentException',
            'Expected parameter #1 to be of type "integer", "string" given'
        );
        new FibonacciNumber('foo', 10);
    }

    public function testExceptionIsThrownIfNextNumberIsNotAnInteger()
    {
        $this->setExpectedException(
            'hmmmath\Exception\InvalidArgumentException',
            'Expected parameter #2 to be of type "integer", "object<stdClass>" given'
        );
        new FibonacciNumber(10, new stdClass());
    }
}
