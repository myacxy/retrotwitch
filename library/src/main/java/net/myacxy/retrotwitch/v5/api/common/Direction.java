package net.myacxy.retrotwitch.v5.api.common;

public enum Direction {
    DEFAULT("desc"),
    ASCENDING("asc"),
    DESCENDING("desc");

    private String mDirection;

    Direction(String direction) {
        mDirection = direction;
    }

    @Override
    public String toString() {
        return mDirection;
    }
}
