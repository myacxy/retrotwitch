package net.myacxy.retrotwitch.v5.api.streams;

import net.myacxy.retrotwitch.utils.StringUtil;
import net.myacxy.retrotwitch.v5.api.RxBaseCaller;
import net.myacxy.retrotwitch.v5.api.channels.SimpleChannel;
import net.myacxy.retrotwitch.v5.api.common.StreamType;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;

// TODO: 14.02.2017
public class RxStreamsCaller extends RxBaseCaller<RxTwitchStreamsService> {

    public RxStreamsCaller(OkHttpClient client) {
        super(client);
    }

    @Override
    protected RxTwitchStreamsService createService(Retrofit retrofit) {
        return retrofit.create(RxTwitchStreamsService.class);
    }

    //<editor-fold desc="API Calls">
    public Observable<Response<StreamResponse>> getStreamByUser(long channelId, StreamType streamType) {
//        call.enqueue(new RetroTwitchCallback<StreamResponse, Stream>(listener) {
//            @Override
//            public Stream beforeOnSuccess(StreamResponse streamResponse) {
//                return streamResponse.stream;
//            }
//        });
        return getService().getStreamByUser(channelId, streamType);
    }

    public Observable<Response<StreamsResponse>> getStreams(
            final List<SimpleChannel> channels,
            final String game,
            final String language,
            final StreamType streamType,
            final Integer limit,
            final Integer offset) {

        List<String> channelNames = null;
        if (channels != null) {
            channelNames = new ArrayList<>(channels.size());
            for (SimpleChannel channel : channels) {
                channelNames.add(channel.getName());
            }
        }

//        call.enqueue(new RetroTwitchCallback<StreamsResponse, StreamsResponse>(listener) {
//
//            @Override
//            public StreamsResponse beforeOnSuccess(StreamsResponse streamsResponse) {
//                streamsResponse.channels = channels;
//                streamsResponse.game = game;
//                streamsResponse.language = language;
//                streamsResponse.limit = limit == null ? TwitchConstants.DEFAULT_LIMIT : limit;
//                streamsResponse.offset = offset == null ? TwitchConstants.DEFAULT_OFFSET : offset;
//                streamsResponse.streamType = streamType == null ? StreamType.DEFAULT : streamType;
//                return streamsResponse;
//            }
//        });

        return getService().getLiveStreams(
                channelNames != null ? StringUtil.joinStrings(channelNames, ",") : null,
                game, language, streamType, limit, offset
        );
    }
    //</editor-fold>
}
