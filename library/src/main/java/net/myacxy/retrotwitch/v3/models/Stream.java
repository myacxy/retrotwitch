package net.myacxy.retrotwitch.v3.models;

import com.google.gson.annotations.SerializedName;

public class Stream extends BaseModel<BaseModel.Links>
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

    private Stream()
    {

    }

    @Override
    public Links getLinks()
    {
        return links;
    }

    public class Preview
    {
        @SerializedName("small")
        public String small;
        @SerializedName("medium")
        public String medium;
        @SerializedName("large")
        public String large;
        @SerializedName("template")
        public String template;

        @Override
        public String toString()
        {
            return "{\n"
                    + " \"small\": \"" + small + "\"\n"
                    + " \"medium\": \"" + medium + "\"\n"
                    + " \"large\": \"" + large + "\"\n"
                    + " \"template\": \"" + template + "\"\n"
                    + "}\n";
        }
    }
}
