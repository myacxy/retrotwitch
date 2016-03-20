package net.myacxy.retrotwitch.models;

import com.google.gson.annotations.SerializedName;
import net.myacxy.retrotwitch.Caller;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

public class StreamContainer extends BaseModel<StreamContainer.Links>
{
    @SerializedName("_links")
    public Links links;
    @SerializedName("stream")
    public Stream stream;

    private StreamContainer()
    {

    }

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
