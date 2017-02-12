package net.myacxy.retrotwitch.v3.models;

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
