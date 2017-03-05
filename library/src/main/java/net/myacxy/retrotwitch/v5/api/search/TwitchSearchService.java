package net.myacxy.retrotwitch.v5.api.search;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TwitchSearchService {

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
    @GET("search/channels")
    Call<SearchChannelsResponse> searchChannels(
            @Query("query") String query,
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    /**
     * <p>Searches for games based on a specified query parameter. A game is returned if the query
     * parameter is matched entirely or partially, in the game name.</p>
     * <p>
     * <p><b>Authentication</b></p>
     * <p>None</p>
     *
     * @param live If true, returns only games that are live on at least one channel. Default: false.
     */
    @GET("search/games")
    Call<SearchGamesResponse> searchGames(
            @Query("query") String query,
            @Query("live") boolean live
    );

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
    @GET("search/streams")
    Call<SearchStreamsResponse> searchStreams(
            @Query("query") String query,
            @Query("hls") Boolean hls,
            @Query("limit") int limit,
            @Query("offset") int offset
    );
}
