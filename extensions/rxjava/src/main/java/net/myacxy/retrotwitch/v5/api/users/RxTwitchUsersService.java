package net.myacxy.retrotwitch.v5.api.users;

import net.myacxy.retrotwitch.v5.api.common.Direction;
import net.myacxy.retrotwitch.v5.api.common.SortBy;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RxTwitchUsersService {
    /**
     * <p>Gets a user object based on the OAuth token provided.</p>
     * <p>
     * <p>If the user’s Twitch-registered email address is not verified, null is returned.</p>
     * <p>
     * <p><b>Authentication</b></p>
     * <p>Required scope: user_read</p>
     *
     * @return Get User returns more data than Get User by ID, because Get User is privileged.
     */
    @GET("user")
    Single<Response<PrivilegedUser>> getUser();

    /**
     * <p>Gets a specified user object.</p>
     * <p>
     * <p><b>Authentication</b></p>
     * <p>None</p>
     */
    @GET("users/{user_id}")
    Single<Response<SimpleUser>> getUserById(@Path("user_id") long userId);

    @GET("users")
    Single<Response<SimpleUsersResponse>> translateUserNameToUserId(@Query("login") String userName);

    /**
     * <p>Gets a list of all channels followed by a specified user, sorted by the date when they
     * started following each channel.</p>
     * <p>
     * <p><b>Authentication</b></p>
     * <p>None</p>
     *
     * @param limit     Maximum number of most-recent objects to return. Default: 25. Maximum: 100.
     * @param offset    Object offset for pagination of results. Default: 0.
     * @param direction Sorting direction. Valid values: asc, desc. Default: desc (newest first).
     * @param sortBy    Sorting key. Valid values: created_at, last_broadcast, login. Default: created_at.
     */
    @GET("users/{user_id}/follows/channels")
    Single<Response<UserFollowsResponse>> getUserFollows(
            @Path("user_id") long userId,
            @Query("limit") Integer limit,
            @Query("offset") Integer offset,
            @Query("direction") Direction direction,
            @Query("sortby") SortBy sortBy);
}
