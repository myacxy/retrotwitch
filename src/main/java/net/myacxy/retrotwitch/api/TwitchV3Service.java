package net.myacxy.retrotwitch.api;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import net.myacxy.retrotwitch.models.Follows;
import net.myacxy.retrotwitch.models.Streams;
import net.myacxy.retrotwitch.responses.GetStreamResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface TwitchV3Service
{
    String BASE_URL = "https://api.twitch.tv/kraken/";

    /**
     * @param limit default 25, max 100
     * @param offset default 0
     * @param direction default desc
     * @param sortBy default created_at
     */
    @GET("users/{user}/follows/channels")
    Call<Follows> getUserFollows(
            @NotNull @Path("user") String user,
            @Nullable @Query("limit") Integer limit,
            @Nullable @Query("offset") Integer offset,
            @Nullable @Query("direction") Direction direction,
            @Nullable @Query("sortby") SortBy sortBy);

    /**
     * used to move between paginated results
     *
     * @param url   {@link net.myacxy.retrotwitch.models.Follows.Links#prev Follows.Links.prev}
     *              or {@link net.myacxy.retrotwitch.models.Follows.Links#next Follows.Links.next}
     */
    @GET
    Call<Follows> getUserFollows(@NotNull @Url String url);

    @GET("streams/{channel}")
    Call<GetStreamResponse> getStream(@NotNull @Path("channel") String channel);

    @GET("streams")
    Call<Streams> getStreams(
            @Nullable @Query("game") String game,
            @Nullable @Query("channel") String channelOrCommaSeparatedChannels,
            @Nullable @Query("limit") Integer limit,
            @Nullable @Query("offset") Integer offset,
            @Nullable @Query("client_id") String clientId,
            @Nullable @Query("stream_type") StreamType streamType);
}
