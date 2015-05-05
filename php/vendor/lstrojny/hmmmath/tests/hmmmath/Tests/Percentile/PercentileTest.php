<?php
namespace hmmmath\Tests\Percentile;

use hmmmath\Percentile\Percentile;
use InterNations\Component\Testing\AbstractTestCase;

class PercentileTest extends AbstractTestCase
{
    public static function getNearestRankData()
    {
        return [
            [15.0, 0.0, [50, 15, 20, 35, 40]],
            [15.0, 0.01, [50, 15, 20, 35, 40]],
            [15.0, 0.1, [50, 15, 20, 35, 40]],
            [20.0, 0.2, [50, 15, 20, 35, 40]],
            [20.0, 0.3, [50, 15, 20, 35, 40]],
            [35.0, 0.4, [50, 15, 20, 35, 40]],
            [35.0, 0.5, [50, 15, 20, 35, 40]],
            [40.0, 0.6, [50, 15, 20, 35, 40]],
            [40.0, 0.7, [50, 15, 20, 35, 40]],
            [50.0, 0.8, [50, 15, 20, 35, 40]],
            [50.0, 0.9, [50, 15, 20, 35, 40]],
            [50.0, 0.99, [50, 15, 20, 35, 40]],
            [50.0, 1.0, [50, 15, 20, 35, 40]],
            [20.0, 0.5, [-1, 10, 50, 15, 20, 35, 40]],
        ];
    }

    /** @dataProvider getNearestRankData */
    public function testNearestRankDefaultMode($expectation, $percentile, $data)
    {
        $result= Percentile::percentile($data, $percentile);
        $this->assertInternalType('float', $result);
        $this->assertSame($expectation, $result);
    }

    /** @dataProvider getNearestRankData */
    public function testNearestRangeExplicit($expectation, $percentile, $data)
    {
        $result= Percentile::percentile($data, $percentile, Percentile::NEAREST_RANK);
        $this->assertInternalType('float', $result);
        $this->assertSame($expectation, $result);
    }

    public static function getGoogleDocsSample()
    {
        return [
            [15.0, 0.0, [50, 15, 20, 35, 40]],
            [15.2, 0.01, [50, 15, 20, 35, 40]],
            [17.0, 0.1, [50, 15, 20, 35, 40]],
            [19.0, 0.2, [50, 15, 20, 35, 40]],
            [23.0, 0.3, [50, 15, 20, 35, 40]],
            [29.0, 0.4, [50, 15, 20, 35, 40]],
            [35.0, 0.5, [50, 15, 20, 35, 40]],
            [37.0, 0.6, [50, 15, 20, 35, 40]],
            [39.0, 0.7, [50, 15, 20, 35, 40]],
            [42.0, 0.8, [50, 15, 20, 35, 40]],
            [46.0, 0.9, [50, 15, 20, 35, 40]],
            [49.6, 0.99, [50, 15, 20, 35, 40]],
            [50.0, 1, [50, 15, 20, 35, 40]],
        ];
    }

    public static function getLinearInterpolationData()
    {
        return [
            [15.0, 0.0, [50, 15, 20, 35, 40]],
            [15.0, 0.01, [50, 15, 20, 35, 40]],
            [15.0, 0.1, [50, 15, 20, 35, 40]],
            [17.5, 0.2, [50, 15, 20, 35, 40]],
            [20.0, 0.3, [50, 15, 20, 35, 40]],
            [27.5, 0.4, [50, 15, 20, 35, 40]],
            [35.0, 0.5, [50, 15, 20, 35, 40]],
            [37.5, 0.6, [50, 15, 20, 35, 40]],
            [40.0, 0.7, [50, 15, 20, 35, 40]],
            [45.0, 0.8, [50, 15, 20, 35, 40]],
            [50.0, 0.9, [50, 15, 20, 35, 40]],
            [50.0, 0.99, [50, 15, 20, 35, 40]],
            [50.0, 1.0, [50, 15, 20, 35, 40]],
        ];
    }

    /** @dataProvider getLinearInterpolationData */
    public function testLinearInterpolation($expectation, $percentile, $data)
    {
        $result = Percentile::percentile($data, $percentile, Percentile::LINEAR_INTERPOLATION);
        $this->assertSame($expectation, $result);
        $this->assertInternalType('float', $result);
    }

    public static function getErrorDataSet()
    {
        return [
            [-1],
            [-1.0],
            [-0.01],
            [1.01],
            [100],
            [100.0],
        ];
    }

    /** @dataProvider getErrorDataSet */
    public function testPercentileError($percentile)
    {
        $this->setExpectedException(
            'hmmmath\Exception\InvalidArgumentException',
            'Expected parameter #2 to be of type "double<0..1>"'
        );

        Percentile::percentile([], $percentile);
    }
}
