package net.myacxy.retrotwitch.api;

import net.myacxy.retrotwitch.models.StreamContainer;
import net.myacxy.retrotwitch.models.StreamsContainer;
import net.myacxy.retrotwitch.models.User;
import net.myacxy.retrotwitch.models.UserFollowsContainer;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RxTwitchV3Service
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
    Observable<UserFollowsContainer> getUserFollows(
            @Path("user") String user,
            @Query("limit") Integer limit,
            @Query("offset") Integer offset,
            @Query("direction") Direction direction,
            @Query("sortby") SortBy sortBy);

    /**
     * used to move between paginated results
     *
     * @param url {@link UserFollowsContainer.Links#prev UserFollowsContainer.Links.prev}
     *            or {@link UserFollowsContainer.Links#next UserFollowsContainer.Links.next}
     */
    @GET
    Observable<UserFollowsContainer> getUserFollows(@Url String url);

    @GET("streams/{channel}")
    Observable<StreamContainer> getStream(@Path("channel") String channel);

    @GET("streams")
    Observable<StreamsContainer> getStreams(
            @Query("game") String game,
            @Query("channel") String channelOrCommaSeparatedChannels,
            @Query("limit") Integer limit,
            @Query("offset") Integer offset,
            @Query("client_id") String clientId,
            @Query("stream_type") StreamType streamType);

    @GET("users/{user}")
    Observable<User> getUser(@Path("user") String user);

    /**
     * requires Scope.USER_READ
     * <p>
     * Note: If the user's Twitch registered Email Address is not verified, null will be returned.
     *
     * @return
     */
    @GET("user")
    Observable<User> getUser();

    /**
     * requires Scope.USER_SUBSCRIPTIONS
     *
     * @param user
     * @return a list of emoticons that the user is authorized to use
     */
    @GET("users/{user}/emotes")
    Observable<User> getUserEmotes(@Path("user") String user);
}
