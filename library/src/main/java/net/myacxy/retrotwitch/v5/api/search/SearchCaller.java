package net.myacxy.retrotwitch.v5.api.search;

import net.myacxy.retrotwitch.v5.api.BaseCaller;
import net.myacxy.retrotwitch.v5.api.ResponseListener;
import net.myacxy.retrotwitch.v5.api.SimpleRetroTwitchCallback;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;

public class SearchCaller extends BaseCaller<TwitchSearchService> {

    public SearchCaller(OkHttpClient client) {
        super(client);
    }

    @Override
    protected TwitchSearchService createService(Retrofit retrofit) {
        return retrofit.create(TwitchSearchService.class);
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
    public Call<SearchChannelsResponse> searchChannels(String query, int limit, int offset,
                                                       final ResponseListener<SearchChannelsResponse> listener) {
        Call<SearchChannelsResponse> call = getService().searchChannels(query, limit, offset);
        call.enqueue(new SimpleRetroTwitchCallback<>(listener));
        return call;
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
    public Call<SearchGamesResponse> searchGames(String query, boolean live,
                                                 final ResponseListener<SearchGamesResponse> listener) {
        Call<SearchGamesResponse> call = getService().searchGames(query, live);
        call.enqueue(new SimpleRetroTwitchCallback<>(listener));
        return call;
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
    public Call<SearchStreamsResponse> searchStreams(String query, Boolean hls, int limit, int offset,
                                                     final ResponseListener<SearchStreamsResponse> listener) {
        Call<SearchStreamsResponse> call = getService().searchStreams(query, hls, limit, offset);
        call.enqueue(new SimpleRetroTwitchCallback<>(listener));
        return call;
    }
    //</editor-fold>
}
