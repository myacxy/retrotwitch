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

    public static class CallBuilder extends Caller.CallBuilder<StreamContainer>
    {
        final String channel;

        public CallBuilder(String channel)
        {
            this.channel = channel;
        }

        @Override
        public Call<StreamContainer> build()
        {
               return Caller.getInstance().getService().getStream(channel);
        }

        @Override
        public StreamContainer buildAndExecute() throws IOException
        {
            return build().execute().body();
        }

        @Override
        public void buildAndEnqueue(final Caller.ResponseListener<StreamContainer> listener)
        {
            build().enqueue(new Callback<StreamContainer>()
            {
                @Override
                public void onResponse(Call<StreamContainer> call, Response<StreamContainer> response)
                {
                    listener.onSuccess(response.body());
                }

                @Override
                public void onFailure(Call<StreamContainer> call, Throwable t)
                {
                    listener.onError();
                }
            });
        }
    }
}
