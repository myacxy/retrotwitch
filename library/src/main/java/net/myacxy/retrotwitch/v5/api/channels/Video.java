package net.myacxy.retrotwitch.v5.api.channels;

import com.google.gson.annotations.SerializedName;

public class Video {

    @SerializedName("broadcast_id")
    protected long broadcastId;
    @SerializedName("title")
    protected String title;
    @SerializedName("url")
    protected String url;
    @SerializedName("length")
    protected long length;

    public String getTitle() {
        return title;
    }

    public long getBroadcastId() {
        return broadcastId;
    }

    public String getUrl() {
        return url;
    }

    public long getLength() {
        return length;
    }
}
