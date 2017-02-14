package net.myacxy.retrotwitch.v5.api.common;

public class TwitchConstants {

    public static final Direction DEFAULT_DIRECTION = Direction.DEFAULT;
    public static final int DEFAULT_LIMIT = 25;
    public static final int DEFAULT_OFFSET = 0;
    public static final SortBy DEFAULT_SORT_BY = SortBy.DEFAULT;
    public static final StreamType DEFAULT_STREAM_TYPE = StreamType.DEFAULT;
    public static final int MAX_LIMIT = 100;

    private TwitchConstants() {
        throw new IllegalAccessError();
    }
}
