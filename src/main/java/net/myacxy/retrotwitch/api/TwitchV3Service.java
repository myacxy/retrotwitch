package net.myacxy.retrotwitch.api;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import net.myacxy.retrotwitch.models.*;
import retrofit2.Call;
import retrofit2.http.*;

public interface TwitchV3Service
{
    String BASE_URL = "https://api.twitch.tv/kraken/";
    int DEFAULT_LIMIT = 25;
    int MAX_LIMIT = 100;

    /**
     * @param limit default 25, max 100
     * @param offset default 0
     * @param direction default desc
     * @param sortBy default created_at
     */
    @GET("users/{user}/follows/channels")
    Call<FollowsContainer> getUserFollows(
            @NotNull @Path("user") String user,
            @Nullable @Query("limit") Integer limit,
            @Nullable @Query("offset") Integer offset,
            @Nullable @Query("direction") Direction direction,
            @Nullable @Query("sortby") SortBy sortBy);

    /**
     * used to move between paginated results
     *
     * @param url   {@link FollowsContainer.Links#prev FollowsContainer.Links.prev}
     *              or {@link FollowsContainer.Links#next FollowsContainer.Links.next}
     */
    @GET
    Call<FollowsContainer> getUserFollows(@NotNull @Url String url);

    @GET("streams/{channel}")
    Call<StreamContainer> getStream(@NotNull @Path("channel") String channel);

    @GET("streams")
    Call<StreamsContainer> getStreams(
            @Nullable @Query("game") String game,
            @Nullable @Query("channel") String channelOrCommaSeparatedChannels,
            @Nullable @Query("limit") Integer limit,
            @Nullable @Query("offset") Integer offset,
            @Nullable @Query("client_id") String clientId,
            @Nullable @Query("stream_type") StreamType streamType);
}
