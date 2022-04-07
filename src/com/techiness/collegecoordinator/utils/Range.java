package com.techiness.collegecoordinator.utils;

public final class Range
{
    private boolean upperBoundInclusive;
    private double lowerBound;
    private double upperBound;

    public Range(double lowerBound, double upperBound, boolean upperBoundInclusive)
    {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.upperBoundInclusive = upperBoundInclusive;
    }

    public boolean isUpperBoundInclusive()
    {
        return upperBoundInclusive;
    }

    public Range resetUpperBoundInclusive(boolean upperBoundInclusive)
    {
        this.upperBoundInclusive = upperBoundInclusive;
        return this;
    }

    public Range resetUpperBound(double upperBound)
    {
        this.upperBound = upperBound;
        return this;
    }

    public Range resetLowerBound(double lowerBound)
    {
        this.lowerBound = lowerBound;
        return this;
    }

    public double getLowerBound()
    {
        return lowerBound;
    }

    public double getUpperBound()
    {
        return upperBound;
    }

    public Range resetBounds(double lowerBound, double upperBound)
    {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        return this;
    }

    public boolean isNumberInRange(double numberToBeChecked)
    {
        return upperBoundInclusive ? numberToBeChecked >= lowerBound && numberToBeChecked <= upperBound
                : numberToBeChecked >= lowerBound && numberToBeChecked < upperBound;
    }

    public int count()
    {
        return upperBoundInclusive ? (int)(upperBound-lowerBound+1) : (int)(upperBound-lowerBound);
    }
}
