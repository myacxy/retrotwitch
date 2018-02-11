package net.myacxy.retrotwitch.v5.api.streams;

import net.myacxy.retrotwitch.utils.StringUtil;
import net.myacxy.retrotwitch.v5.api.BaseCaller;
import net.myacxy.retrotwitch.v5.api.ResponseListener;
import net.myacxy.retrotwitch.v5.api.RetroTwitchCallback;
import net.myacxy.retrotwitch.v5.api.channels.SimpleChannel;
import net.myacxy.retrotwitch.v5.api.common.StreamType;
import net.myacxy.retrotwitch.v5.api.common.TwitchConstants;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;

public class StreamsCaller extends BaseCaller<TwitchStreamsService> {

    public StreamsCaller(OkHttpClient client) {
        super(client);
    }

    @Override
    protected TwitchStreamsService createService(Retrofit retrofit) {
        return retrofit.create(TwitchStreamsService.class);
    }

    //<editor-fold desc="API Calls">
    public Call<StreamResponse> getStreamByUser(long channelId, StreamType streamType, final ResponseListener<Stream> listener) {
        Call<StreamResponse> call = getService().getStreamByUser(channelId, streamType);
        call.enqueue(new RetroTwitchCallback<StreamResponse, Stream>(listener) {
            @Override
            public Stream beforeOnSuccess(StreamResponse streamResponse) {
                return streamResponse.getStream();
            }
        });
        return call;
    }

    public Call<StreamsResponse> getStreams(
            final List<SimpleChannel> channels,
            final String game,
            final String language,
            final StreamType streamType,
            final Integer limit,
            final Integer offset,
            final ResponseListener<StreamsResponse> listener) {

        List<String> channelNames = null;
        if (channels != null) {
            channelNames = new ArrayList<>(channels.size());
            for (SimpleChannel channel : channels) {
                channelNames.add(channel.getName());
            }
        }

        Call<StreamsResponse> call = getService().getLiveStreams(
                channelNames != null ? StringUtil.joinStrings(channelNames, ",") : null,
                game, language, streamType, limit, offset);

        call.enqueue(new RetroTwitchCallback<StreamsResponse, StreamsResponse>(listener) {

            @Override
            public StreamsResponse beforeOnSuccess(StreamsResponse streamsResponse) {
                streamsResponse.channels = channels;
                streamsResponse.game = game;
                streamsResponse.language = language;
                streamsResponse.limit = limit == null ? TwitchConstants.DEFAULT_LIMIT : limit;
                streamsResponse.offset = offset == null ? TwitchConstants.DEFAULT_OFFSET : offset;
                streamsResponse.streamType = streamType == null ? StreamType.DEFAULT : streamType;
                return streamsResponse;
            }
        });
        return call;
    }
    //</editor-fold>
}
