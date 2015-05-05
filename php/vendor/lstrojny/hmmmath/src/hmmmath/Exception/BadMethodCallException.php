<?php
namespace hmmmath\Exception;

use InvalidArgumentException as BaseBadMethodCallException;

class BadMethodCallException extends BaseBadMethodCallException
{
    public static function invalidMethod($className, $methodName)
    {
        return new static(sprintf('Bad method call to %s::%s()', $className, $methodName));
    }
}
