package net.myacxy.retrotwitch.api;

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
