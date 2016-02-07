package net.myacxy.retrotwitch.api;

public enum Direction
{
    DEFAULT("desc"),
    ASCENDING("asc"),
    DESCENDING("desc");

    private String mDirection;

    Direction(String direction)
    {
        mDirection = direction;
    }

    @Override
    public String toString()
    {
        return mDirection;
    }
}
