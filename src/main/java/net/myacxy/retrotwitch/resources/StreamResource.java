package net.myacxy.retrotwitch.resources;

import com.sun.istack.internal.NotNull;
import net.myacxy.retrotwitch.Caller;
import net.myacxy.retrotwitch.FluentCaller;
import net.myacxy.retrotwitch.models.Stream;
import net.myacxy.retrotwitch.models.StreamContainer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StreamResource extends BaseSingleResource<StreamResource, Stream>
{
    private final String channel;

    private StreamResource(Builder builder)
    {
        channel = builder.channel;
    }

    public static class Builder extends BaseSingleResource.Builder<StreamResource>
    {
        final String channel;

        public Builder(@NotNull String channel)
        {
            this.channel = channel;
        }

        public StreamResource build()
        {
            return new StreamResource(this);
        }
    }

    public FluentCaller enqueue(final Caller.ResponseListener<Stream> listener)
    {
        Caller.getInstance().getService().getStream(channel).enqueue(new Callback<StreamContainer>()
        {
            @Override
            public void onResponse(Call<StreamContainer> call, Response<StreamContainer> response)
            {
                listener.onSuccess(response.body().stream);
            }

            @Override
            public void onFailure(Call<StreamContainer> call, Throwable t)
            {
                listener.onError();
            }
        });

        return FluentCaller.getInstance();
    }
}
