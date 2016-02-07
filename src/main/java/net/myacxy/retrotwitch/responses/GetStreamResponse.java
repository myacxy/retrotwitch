package net.myacxy.retrotwitch.responses;

import com.google.gson.annotations.SerializedName;
import net.myacxy.retrotwitch.models.BaseModel;
import net.myacxy.retrotwitch.models.Stream;

public class GetStreamResponse extends BaseModel<GetStreamResponse.Links>
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
