<?php
namespace hmmmath\GreatestCommonDivisor;

final class GreatestCommonDivisor
{
    const AUTODETECT = 'auto';

    const EUCLIDIAN = 'euclidian';

    const APPROXIMATION = 'approximation';

    /**
     * Calculate the greatest common divisor of a list of numbers
     *
     * Euclidian’s algorithm forces integers, even if you feed floats. Use approximation for floats. Default mode is
     * auto detection and selects the appropriate algorithm for the number pair at hand.
     *
     * @param array $numbers
     * @param string $mode
     * @return integer|float
     */
    public static function greatestCommonDivisor(array $numbers, $mode = self::AUTODETECT)
    {
        $left = array_shift($numbers);

        if (is_infinite($left)) {
            return INF;
        }

        if ($mode === static::AUTODETECT && is_float($left)) {
            $mode = static::APPROXIMATION;
        }

        do {
            $right = array_shift($numbers);

            if (is_infinite($right)) {
                return INF;
            }

            if ($mode === static::APPROXIMATION || ($mode === static::AUTODETECT && is_float($right))) {
                $left = static::approximateGreatestCommonDivisor($left, $right);
            } else {
                $left = static::euclidianGreatestCommonDivisor($left, $right);
            }


        } while ($numbers);

        return $left;
    }

    private static function euclidianGreatestCommonDivisor($left, $right)
    {
        return $right ? static::euclidianGreatestCommonDivisor($right, $left % $right) : $left;
    }

    private static function approximateGreatestCommonDivisor($left, $right)
    {
        return $right ? static::approximateGreatestCommonDivisor($right, fmod($left, $right)) : $left;
    }
}
