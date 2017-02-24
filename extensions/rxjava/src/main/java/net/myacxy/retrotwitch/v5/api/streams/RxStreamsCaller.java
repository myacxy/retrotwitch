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

        List<String> channelIds = null;
        if (channels != null) {
            channelIds = new ArrayList<>(channels.size());
            for (SimpleChannel channel : channels) {
                channelIds.add(String.valueOf(channel.getId()));
            }
        }

        return getService().getLiveStreams(
                channelIds != null ? StringUtil.joinStrings(channelIds, ",") : null,
                game, language, streamType, limit, offset
        );
    }
    //</editor-fold>
}
