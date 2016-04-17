package net.myacxy.retrotwitch.resources;

import net.myacxy.retrotwitch.Caller;
import net.myacxy.retrotwitch.FluentCaller;
import net.myacxy.retrotwitch.helpers.ErrorFactory;
import net.myacxy.retrotwitch.models.Error;
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

    public FluentCaller enqueue(final Caller.ResponseListener<Stream> listener)
    {
        Caller.getInstance().getService().getStream(channel).enqueue(new Callback<StreamContainer>()
        {
            @Override
            public void onResponse(Call<StreamContainer> call, Response<StreamContainer> response)
            {
                Error error = ErrorFactory.fromResponse(response);
                if (error == null)
                {
                    listener.onSuccess(response.body().stream);
                } else
                {
                    listener.onError(error);
                }
            }

            @Override
            public void onFailure(Call<StreamContainer> call, Throwable t)
            {
                listener.onError(ErrorFactory.unexpected(-1, t.getMessage()));
            }
        });

        return FluentCaller.INSTANCE;
    }

    public static class Builder extends BaseSingleResource.Builder<StreamResource>
    {
        final String channel;

        public Builder(String channel)
        {
            this.channel = channel;
        }

        public StreamResource build()
        {
            return new StreamResource(this);
        }
    }
}
