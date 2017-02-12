package net.myacxy.retrotwitch.v5.api.streams;

import net.myacxy.retrotwitch.v5.api.common.StreamType;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TwitchStreamsService {
    /**
     * <p>Gets stream information (the stream object) for a specified user.</p>
     * <p>
     * <p><b>Authentication</b></p>
     * <p>None</p>
     *
     * @param streamType Constrains the type of streams returned. Valid values: live, playlist, all. Playlists are offline streams of VODs (Video on Demand) that appear live. Default: live.
     */
    @GET("streams/{channel_id}")
    Call<StreamResponse> getStreamByUser(@Path("channel_id") long channelId, @Query("stream_type") StreamType streamType);

    /**
     * <p>Gets a list of live streams.</p>
     * <p>
     * <p><b>Authentication</b></p>
     * <p>None</p>
     *
     * @param channel    Comma-separated list of channel IDs that constrains the channel(s) of the streams returned.
     * @param game       Constrains the game of the streams returned.
     * @param language   Constrains the language of the streams returned. Valid value: a locale ID string; for example, en, fi, es-mx. Only one language can be specified. Default: all languages.
     * @param streamType Constrains the type of streams returned. Valid values: live, playlist, all. Playlists are offline streams of VODs (Video on Demand) that appear live. Default: live.
     * @param limit      Maximum number of objects to return, sorted by number of viewers. Default: 25. Maximum: 100.
     * @param offset     Object offset for pagination of results. Default: 0.
     */
    @GET("streams")
    Call<StreamsResponse> getLiveStreams(
            @Query("channel") String channel,
            @Query("game") String game,
            @Query("language") String language,
            @Query("stream_type") StreamType streamType,
            @Query("limit") Integer limit,
            @Query("offset") Integer offset);
}
