package net.myacxy.retrotwitch.models;

import com.google.gson.annotations.SerializedName;

public class Follow extends BaseModel
{
    @SerializedName("created_at")
    public String createdAt;
    @SerializedName("_links")
    public Links links;
    @SerializedName("notifications")
    public Boolean notifications;
    @SerializedName("channel")
    public Channel channel;

    public class Links extends BaseModel
    {
        @SerializedName("self")
        public String self;
    }
}
