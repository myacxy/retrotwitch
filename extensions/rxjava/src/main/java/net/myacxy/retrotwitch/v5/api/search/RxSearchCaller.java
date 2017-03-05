package net.myacxy.retrotwitch.v5.api.search;

import net.myacxy.retrotwitch.v5.api.BaseCaller;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RxSearchCaller extends BaseCaller<RxTwitchSearchService> {

    public RxSearchCaller(OkHttpClient client) {
        super(client);
    }

    @Override
    protected RxTwitchSearchService createService(Retrofit retrofit) {
        return retrofit.create(RxTwitchSearchService.class);
    }

    //<editor-fold desc="API Calls">

    /**
     * <p>Searches for channels based on a specified query parameter. A channel is returned if the
     * query parameter is matched entirely or partially, in the channel description or game name.</p>
     * <p>
     * <p><b>Authentication</b></p>
     * <p>None</p>
     *
     * @param limit  Maximum number of objects to return, sorted by number of viewers. Default: 25. Maximum: 100.
     * @param offset Object offset for pagination of results. Default: 0.
     */
    Single<Response<SearchChannelsResponse>> searchChannels(String query, int limit, int offset) {
        return getService().searchChannels(query, limit, offset);
    }

    /**
     * <p>Searches for games based on a specified query parameter. A game is returned if the query
     * parameter is matched entirely or partially, in the game name.</p>
     * <p>
     * <p><b>Authentication</b></p>
     * <p>None</p>
     *
     * @param live If true, returns only games that are live on at least one channel. Default: false.
     */
    Single<Response<SearchGamesResponse>> searchGames(String query, boolean live) {
        return getService().searchGames(query, live);
    }

    /**
     * <p>Searches for streams based on a specified query parameter. A stream is returned if the
     * query parameter is matched entirely or partially, in the channel description or game name.</p>
     * <p>
     * <p><b>Authentication</b></p>
     * <p>None</p>
     *
     * @param hls    If true, returns only HLS streams; if false, only RTMP streams; if not set, both
     *               HLS and RTMP streams. HLS is HTTP Live Streaming, a live-streaming communications
     *               protocol. RTMP is Real-Time Media Protocol, an industry standard for moving video
     *               around a network. Default: not set.
     * @param limit  Maximum number of objects to return, sorted by number of viewers. Default: 25. Maximum: 100.
     * @param offset Object offset for pagination of results. Default: 0.
     */
    public Single<Response<SearchStreamsResponse>> searchStreams(String query, Boolean hls,
                                                                 int limit, int offset) {
        return getService().searchStreams(query, hls, limit, offset);
    }
    //</editor-fold>
}
