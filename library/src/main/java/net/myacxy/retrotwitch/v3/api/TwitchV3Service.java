package net.myacxy.retrotwitch.v3.api;

import net.myacxy.retrotwitch.v3.models.Direction;
import net.myacxy.retrotwitch.v3.models.SortBy;
import net.myacxy.retrotwitch.v3.models.StreamContainer;
import net.myacxy.retrotwitch.v3.models.StreamType;
import net.myacxy.retrotwitch.v3.models.StreamsContainer;
import net.myacxy.retrotwitch.v3.models.User;
import net.myacxy.retrotwitch.v3.models.UserFollowsContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface TwitchV3Service
{
    String BASE_URL = "https://api.twitch.tv/kraken/";
    int DEFAULT_LIMIT = 25;
    int MAX_LIMIT = 100;

    /**
     * @param limit     default 25, max 100
     * @param offset    default 0
     * @param direction default desc
     * @param sortBy    default created_at
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
     * @param url {@link UserFollowsContainer.Links#prev UserFollowsResponse.Links.prev}
     *            or {@link UserFollowsContainer.Links#next UserFollowsResponse.Links.next}
     */
    @GET
    Call<UserFollowsContainer> getUserFollows(@Url String url);

    @GET("streams/{channel}")
    Call<StreamContainer> getStream(@Path("channel") String channel);

    @GET("streams")
    Call<StreamsContainer> getStreams(
            @Query("game") String game,
            @Query("channel") String channelOrCommaSeparatedChannels,
            @Query("limit") Integer limit,
            @Query("offset") Integer offset,
            @Query("client_id") String clientId,
            @Query("stream_type") StreamType streamType);

    @GET("users/{user}")
    Call<User> getUser(@Path("user") String user);

    /**
     * requires Scope.USER_READ
     * <p>
     * Note: If the user's Twitch registered Email Address is not verified, null will be returned.
     *
     * @return
     */
    @GET("user")
    Call<User> getUser();
}
