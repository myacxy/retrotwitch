package net.myacxy.retrotwitch.v5.api.channels;

import net.myacxy.retrotwitch.utils.StringUtil;
import net.myacxy.retrotwitch.v5.api.RxBaseCaller;
import net.myacxy.retrotwitch.v5.api.common.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;

// TODO: 14.02.2017
public class RxChannelsCaller extends RxBaseCaller<RxTwitchChannelsService> {

    public RxChannelsCaller(OkHttpClient client) {
        super(client);
    }

    private static <T> String listToSeparatedString(List<T> list, String separator, Function<T, String> function) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        List<String> result = new ArrayList<>(list.size());
        for (T t : list) {
            result.add(function.apply(t));
        }
        return StringUtil.joinStrings(result, separator);
    }

    @Override
    protected RxTwitchChannelsService createService(Retrofit retrofit) {
        return retrofit.create(RxTwitchChannelsService.class);
    }

    //<editor-fold desc="API Calls">
    /**
     * <p>Gets a channel object based on a specified OAuth token.</p>
     * <p>
     * <p><b>Authentication</b></p>
     * <p>Required scope: channel_read</p>
     * <p>
     * <p><b>Optional Query String Parameters</b></p>
     * <p>None</p>
     *
     * @return Get Channel returns more data than Get Channel by ID because Get Channel is privileged.
     */
    public Observable<Response<PrivilegedChannel>> getChannel() {
        return getService().getChannel();
    }

    /**
     * <p>Gets a specified channel object.</p>
     * <p>
     * <p><b>Authentication</b></p>
     * <p>None.</p>
     * <p>
     * <p><b>Optional Query String Parameters</b></p>
     * <p>None</p>
     */
    public Observable<Response<SimpleChannel>> getChannelById(long channelId) {
        return getService().getChannelById(channelId);
    }

    public Single<Response<GetVideosResponse>> getVideos(long channelId, List<BroadcastType> broadcastTypes, List<String> languages, Sort sort, int limit, int offset) {

        String broadcastTypesString = listToSeparatedString(broadcastTypes, ",", BroadcastType::toString);
        String languagesString = listToSeparatedString(languages, ",", String::toString);
        return getService().getVideos(
                channelId,
                broadcastTypesString,
                languagesString,
                sort.toString(),
                limit,
                offset
        );
    }

    public Single<Response<GetVideosResponse>> getVideos(long channelId, BroadcastType broadcastType, String languages, Sort sort, int limit, int offset) {
        return getService().getVideos(
                channelId,
                broadcastType.toString(),
                languages,
                sort.toString(),
                limit,
                offset
        );
    }
    //</editor-fold>
}
