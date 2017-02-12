package net.myacxy.retrotwitch.v3.models;

public enum StreamType
{
    DEFAULT("all"),
    ALL("all"),
    PLAYLIST("playlist"),
    LIVE("live");

    private String mStreamType;

    StreamType(String streamType)
    {
        mStreamType = streamType;
    }

    @Override
    public String toString()
    {
        return mStreamType;
    }
}
