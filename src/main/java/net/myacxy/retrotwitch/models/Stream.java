package net.myacxy.retrotwitch.models;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

public class Stream extends BaseModel
{
    @SerializedName("game")
    public String game;
    @SerializedName("viewers")
    public Integer viewers;
    @SerializedName("average_fps")
    public Double averageFps;
    @SerializedName("delay")
    public Integer delay;
    @SerializedName("video_height")
    public Integer videoHeight;
    @SerializedName("is_playlist")
    public Boolean isPlaylist;
    @SerializedName("created_at")
    public String createdAt;
    @SerializedName("_id")
    public Long id;
    @SerializedName("channel")
    public Channel channel;
    @SerializedName("preview")
    public Preview preview;
    @SerializedName("_links")
    public Links links;

    public class Preview extends BaseModel
    {
        @SerializedName("small")
        public String small;
        @SerializedName("medium")
        public String medium;
        @SerializedName("large")
        public String large;
        @SerializedName("template")
        public String template;
    }

    public class Links extends BaseModel
    {
        @SerializedName("self")
        public String self;
    }
}
