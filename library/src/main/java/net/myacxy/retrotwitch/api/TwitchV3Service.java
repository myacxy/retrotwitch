package net.myacxy.retrotwitch.api;

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
    Call<UserFollowsContainer> getUserFollows(
            @Path("user") String user,
            @Query("limit") Integer limit,
            @Query("offset") Integer offset,
            @Query("direction") Direction direction,
            @Query("sortby") SortBy sortBy);

    /**
     * used to move between paginated results
     *
     * @param url   {@link UserFollowsContainer.Links#prev UserFollowsContainer.Links.prev}
     *              or {@link UserFollowsContainer.Links#next UserFollowsContainer.Links.next}
     */
    @GET
    Call<UserFollowsContainer> getUserFollows(@Url String url);

    @GET("streams/{channel}")
    Call<StreamContainer> getStream( @Path("channel") String channel);

    @GET("streams")
    Call<StreamsContainer> getStreams(
            @Query("game") String game,
            @Query("channel") String channelOrCommaSeparatedChannels,
            @Query("limit") Integer limit,
            @Query("offset") Integer offset,
            @Query("client_id") String clientId,
            @Query("stream_type") StreamType streamType);
}
