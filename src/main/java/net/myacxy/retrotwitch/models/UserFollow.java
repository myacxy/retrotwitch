package net.myacxy.retrotwitch.models;

import com.google.gson.annotations.SerializedName;

public class UserFollow extends BaseModel<BaseModel.Links>
{
    @SerializedName("created_at")
    public String createdAt;
    @SerializedName("_links")
    public Links links;
    @SerializedName("notifications")
    public Boolean notifications;
    @SerializedName("channel")
    public Channel channel;

    private UserFollow()
    {

    }

    @Override
    public Links getLinks()
    {
        return links;
    }
}
