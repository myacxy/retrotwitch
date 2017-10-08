package net.myacxy.retrotwitch.v5.api.channels;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RxTwitchChannelsService {

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
    @GET("channels")
    Observable<Response<PrivilegedChannel>> getChannel();

    /**
     * <p>Gets a specified channel object.</p>
     * <p>
     * <p><b>Authentication</b></p>
     * <p>None.</p>
     * <p>
     * <p><b>Optional Query String Parameters</b></p>
     * <p>None</p>
     */
    @GET("channels/{channel_id}")
    Observable<Response<SimpleChannel>> getChannelById(@Path("channel_id") long channelId);

    /**
     * <p>Gets a list of VODs (Video on Demand) from a specified channel.</p>
     * <p>
     * <p><b>Authentication</b></p>
     * <p>None.</p>
     *
     * @param broadcastType Constrains the type of videos returned. Valid values: (any combination of) archive, highlight, upload. Default: all types (no filter).
     * @param language      Constrains the language of the streams returned. Valid value: a locale ID string; for example, en, fi, es-mx. Only one language can be specified. Default: all languages.
     * @param sort          Sorting order of the returned objects. Valid values: views, time. Default: time (most recent first).
     * @param limit         Maximum number of objects to return, sorted by number of viewers. Default: 25. Maximum: 100.
     * @param offset        Object offset for pagination of results. Default: 0.
     */
    @GET("channels/{channel_id}/videos")
    Single<Response<GetVideosResponse>> getVideos(
            @Path("channel_id") long channelId,
            @Query("broadcast_type") String broadcastType,
            @Query("language") String language,
            @Query("sort") String sort,
            @Query("limit") int limit,
            @Query("offset") int offset
    );
}
