<?php
namespace hmmmath\Tests\GreatestCommonDivisor;

use hmmmath\GreatestCommonDivisor\GreatestCommonDivisor;
use InterNations\Component\Testing\AbstractTestCase;

class GreatestCommonDivisorTest extends AbstractTestCase
{
    public function testGreatestCommonDivisorOfIntegersWithAutoDetection()
    {
        $this->assertSame(1, GreatestCommonDivisor::greatestCommonDivisor([1]));

        $this->assertSame(5, GreatestCommonDivisor::greatestCommonDivisor([10, 20, 5]));
        $this->assertSame(10, GreatestCommonDivisor::greatestCommonDivisor([10, 20]));
        $this->assertSame(1, GreatestCommonDivisor::greatestCommonDivisor([0, 1]));
        $this->assertSame(-1, GreatestCommonDivisor::greatestCommonDivisor([0, -1]));
        $this->assertSame(-1, GreatestCommonDivisor::greatestCommonDivisor([-2, -3]));
        $this->assertSame(-1, GreatestCommonDivisor::greatestCommonDivisor([-2, -3]));
    }

    public function testGreatestCommonDivisorFloatsWithAutoDetection()
    {
        $this->assertSame(10.5, GreatestCommonDivisor::greatestCommonDivisor([10.5, 21.0]));
        $this->assertSame(0.5, GreatestCommonDivisor::greatestCommonDivisor([10.5, 21, 0.5]));
        $this->assertSame(-0.5, GreatestCommonDivisor::greatestCommonDivisor([-2, -3, -2.5]));
    }

    public function testGreatestCommonDivisorFloatWithForcedMode()
    {
        $this->assertSame(1, GreatestCommonDivisor::greatestCommonDivisor([10.1, 21.0], GreatestCommonDivisor::EUCLIDIAN));
        $this->assertSame(3, GreatestCommonDivisor::greatestCommonDivisor([3, 21.5, 6.1], GreatestCommonDivisor::EUCLIDIAN));
    }

    public function testGreatestCommonDivisorIntegerWithForcedMode()
    {
        $this->assertSame(2.0, GreatestCommonDivisor::greatestCommonDivisor([10, 22], GreatestCommonDivisor::APPROXIMATION));
        $this->assertEquals(1.0, GreatestCommonDivisor::greatestCommonDivisor([10, 21, 20], GreatestCommonDivisor::APPROXIMATION));
    }

    public function testINF()
    {
        $this->assertTrue(is_infinite(GreatestCommonDivisor::greatestCommonDivisor([10, INF])));
        $this->assertTrue(is_infinite(GreatestCommonDivisor::greatestCommonDivisor([INF, INF])));
        $this->assertTrue(is_infinite(GreatestCommonDivisor::greatestCommonDivisor([INF, 10])));
        $this->assertTrue(is_infinite(GreatestCommonDivisor::greatestCommonDivisor([10, INF, 10])));
    }
}
