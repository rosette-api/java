<?php
namespace hmmmath\Percentile;

use hmmmath\Exception\InvalidArgumentException;

final class Percentile
{
    const NEAREST_RANK = 'nearestRank';

    const LINEAR_INTERPOLATION = 'linearInterpolation';

    /**
     * Calculate nth percentile of a list of numbers
     *
     * @param number[] $numbers
     * @param float $percentile
     * @param string $mode
     * @param boolean $sort
     * @return float
     */
    public static function percentile(array $numbers, $percentile, $mode = self::NEAREST_RANK, $sort = true)
    {
        InvalidArgumentException::assertParameterType('2', 'double', $percentile, '0..1');

        if ($sort) {
            sort($numbers);
        }

        switch ($mode) {
            case static::LINEAR_INTERPOLATION:
                $result = static::linearInterpolation($numbers, $percentile);
                break;

            case static::NEAREST_RANK:
            default:
                $result = static::nearestRank($numbers, $percentile);
                break;
        }

        return $result;
    }

    /**
     * @param number[] $numbers
     * @param float $percentile
     * @return float
     */
    private static function nearestRank(array $numbers, $percentile)
    {
        $nearestRank = static::getNearestRank($numbers, $percentile);

        return static::getValue($numbers, $nearestRank, 'end');
    }

    /**
     * @param number[] $numbers
     * @param float $percentile
     * @return float
     */
    private static function linearInterpolation(array $numbers, $percentile)
    {
        $nearestRank = static::getNearestRank($numbers, $percentile);

        $count = count($numbers);

        $leftRank = (int) floor($nearestRank);

        $p = 100 / $count * ($leftRank - 0.5);

        $left = static::getValue($numbers, $leftRank, 'reset');
        $right = static::getValue($numbers, $leftRank + 1, 'end');

        return $left + $count * ($percentile * 100 - $p) / 100 * ($right - $left);
    }

    /**
     * @param number[] $numbers
     * @param float $key
     * @param callable $fallback
     * @return float
     */
    private static function getValue(array $numbers, $key, callable $fallback)
    {
        $key = (int) round($key) -  1;
        $value = isset($numbers[$key]) ? $numbers[$key] : $fallback($numbers);

        return (float) $value;
    }

    /**
     * @param number[] $numbers
     * @param float $percentile
     * @return float
     */
    private static function getNearestRank(array $numbers, $percentile)
    {
        return $percentile * count($numbers) + 1 / 2;
    }
}
