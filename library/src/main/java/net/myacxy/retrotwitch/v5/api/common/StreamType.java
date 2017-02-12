package net.myacxy.retrotwitch.v5.api.common;

public enum StreamType {
    DEFAULT("live"),
    ALL("all"),
    PLAYLIST("playlist"),
    LIVE("live");

    private String mStreamType;

    StreamType(String streamType) {
        mStreamType = streamType;
    }

    @Override
    public String toString() {
        return mStreamType;
    }
}
