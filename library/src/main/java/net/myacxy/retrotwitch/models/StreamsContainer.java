package net.myacxy.retrotwitch.models;

import com.google.gson.annotations.SerializedName;
import net.myacxy.retrotwitch.Caller;
import net.myacxy.retrotwitch.api.StreamType;
import net.myacxy.retrotwitch.api.TwitchV3Service;
import net.myacxy.retrotwitch.utils.StringUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StreamsContainer extends BaseModel<StreamsContainer.Links>
{
    @SerializedName("_total")
    public Integer total;
    @SerializedName("streams")
    public List<Stream> streams = new ArrayList<>();
    @SerializedName("_links")
    public Links links;

    public transient String game = null;
    public transient List<Channel> channels = null;
    public transient int limit = TwitchV3Service.DEFAULT_LIMIT;
    public transient int offset = 0;
    public transient String clientId = null;
    public transient StreamType streamType = StreamType.DEFAULT;

    private StreamsContainer()
    {
        // use builder
    }

    @Override
    public Links getLinks()
    {
        return links;
    }

    public void getPrevious(Caller.ResponseListener<StreamsContainer> listener)
    {
        if(hasPrevious())
        {
            new CallBuilder()
                    .withGame(game)
                    .withChannels(channels)
                    .withLimit(limit)
                    .withOffset(offset - limit)
                    .withClientId(clientId)
                    .withStreamType(streamType)
                    .buildAndEnqueue(listener);

            Caller.getInstance().getStreams(game, channels, limit, offset - limit, clientId, streamType, listener);
        }
    }

    public void getNext(Caller.ResponseListener<StreamsContainer> listener)
    {
        if(hasNext())
        {
            Caller.getInstance().getStreams(game, channels, limit, offset + limit, clientId, streamType, listener);
        }
    }

    public boolean hasPrevious()
    {
        return total != null && offset > 0 && offset < total;
    }

    public boolean hasNext()
    {
        return total != null && total < offset + limit;
    }

    public class Links extends BaseModel.Links
    {
        @SerializedName("summary")
        public String summary;
        @SerializedName("followed")
        public String followed;
        @SerializedName("prev")
        public String prev;
        @SerializedName("next")
        public String next;
        @SerializedName("featured")
        public String featured;
    }

    public static class CallBuilder extends Caller.CallBuilder<StreamsContainer>
    {
        String game = null;
        List<Channel> channels = null;
        int limit = TwitchV3Service.DEFAULT_LIMIT;
        int offset = 0;
        String clientId;
        StreamType streamType = StreamType.DEFAULT;

        public CallBuilder withGame(String game) {
            this.game = game;
            return this;
        }

        public CallBuilder withChannels(List<Channel> channels) {
            this.channels = channels;
            return this;
        }

        public CallBuilder appendChannel(Channel channel) {
            if(channels == null)
            {
                channels = new ArrayList<>();
            }
            channels.add(channel);
            return this;
        }

        public CallBuilder withLimit(int limit) {
            this.limit = limit;
            return this;
        }

        public CallBuilder withOffset(int offset) {
            this.offset = offset;
            return this;
        }

        public CallBuilder withClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public CallBuilder withStreamType(StreamType streamType) {
            this.streamType = streamType;
            return this;
        }

        @Override
        public Call<StreamsContainer> build()
        {
            List<String> channelNames = null;
            if(channels != null)
            {
                channelNames = new ArrayList<>(channels.size());
                for (Channel channel : channels)
                {
                    channelNames.add(channel.name);
                }
            }

            return Caller.getInstance().getService().getStreams(
                    game,
                    channelNames != null ? StringUtil.joinStrings(channelNames, ",") : null,
                    limit,
                    offset,
                    clientId,
                    streamType);
        }

        @Override
        public StreamsContainer buildAndExecute() throws IOException
        {
            StreamsContainer streamsContainer = build().execute().body();
            assignCallParameters(streamsContainer);
            return streamsContainer;
        }

        @Override
        public void buildAndEnqueue(final Caller.ResponseListener<StreamsContainer> listener)
        {
            build().enqueue(new Callback<StreamsContainer>()
            {
                @Override
                public void onResponse(Call<StreamsContainer> call, Response<StreamsContainer> response)
                {
                    StreamsContainer streamsContainer = response.body();
                    assignCallParameters(streamsContainer);
                    listener.onSuccess(streamsContainer);
                }

                @Override
                public void onFailure(Call<StreamsContainer> call, Throwable t)
                {
                    listener.onError();
                }
            });
        }

        private void assignCallParameters(StreamsContainer streamsContainer)
        {
            streamsContainer.game = game;
            streamsContainer.channels = channels;
            streamsContainer.limit = limit;
            streamsContainer.offset = offset;
            streamsContainer.clientId = clientId;
            streamsContainer.streamType = streamType;
        }
    }
}
