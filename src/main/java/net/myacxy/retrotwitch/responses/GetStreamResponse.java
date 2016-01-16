package net.myacxy.retrotwitch.responses;

import com.google.gson.annotations.SerializedName;
import net.myacxy.retrotwitch.models.BaseModel;
import net.myacxy.retrotwitch.models.Stream;

public class GetStreamResponse extends BaseModel
{
    @SerializedName("_links")
    public Links links;
    @SerializedName("stream")
    public Stream stream;

    public class Links extends BaseModel
    {
        @SerializedName("channel")
        public String channel;
        @SerializedName("self")
        public String self;
    }
}
