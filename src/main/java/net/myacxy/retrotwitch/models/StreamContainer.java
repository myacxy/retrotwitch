package net.myacxy.retrotwitch.models;

import com.google.gson.annotations.SerializedName;

public class StreamContainer extends BaseModel<StreamContainer.Links>
{
    @SerializedName("_links")
    public Links links;
    @SerializedName("stream")
    public Stream stream;

    @Override
    public Links getLinks()
    {
        return links;
    }

    public class Links extends BaseModel.Links
    {
        @SerializedName("channel")
        public String channel;
    }
}
