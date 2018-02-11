package net.myacxy.retrotwitch.v5.api.users;

import net.myacxy.retrotwitch.v5.api.ResponseListener;
import net.myacxy.retrotwitch.v5.api.RxBaseCaller;
import net.myacxy.retrotwitch.v5.api.common.Direction;
import net.myacxy.retrotwitch.v5.api.common.SortBy;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;

// TODO: 14.02.2017
public class RxUsersCaller extends RxBaseCaller<RxTwitchUsersService> {

    //<editor-fold desc="Constructor">
    public RxUsersCaller(OkHttpClient client) {
        super(client);
    }
    //</editor-fold>

    @Override
    protected RxTwitchUsersService createService(Retrofit retrofit) {
        return retrofit.create(RxTwitchUsersService.class);
    }

    //<editor-fold desc="API Calls">
    public Single<Response<PrivilegedUser>> getUser(final ResponseListener<PrivilegedUser> listener) {
        return getService().getUser();
    }

    public Single<Response<SimpleUser>> getUserById(long userId) {
        return getService().getUserById(userId);
    }

    public Single<Response<SimpleUsersResponse>> translateUserNameToUserId(String userName) {
//        call.enqueue(new RetroTwitchCallback<SimpleUsersResponse, List<SimpleUser>>(listener) {
//            @Override
//            public List<SimpleUser> beforeOnSuccess(SimpleUsersResponse simpleUsersResponse) {
//                return simpleUsersResponse.getUsers();
//            }
//        });
        return getService().translateUserNameToUserId(userName);
    }

    public Single<Response<UserFollowsResponse>> getUserFollows(
            final long userId,
            final Integer limit,
            final Integer offset,
            final Direction direction,
            final SortBy sortBy) {

//        call.enqueue(new RetroTwitchCallback<UserFollowsResponse, UserFollowsResponse>(listener) {
//
//            @Override
//            public UserFollowsResponse beforeOnSuccess(UserFollowsResponse userFollowsResponse) {
//                userFollowsResponse.userId = userId;
//                userFollowsResponse.limit = limit == null ? TwitchConstants.DEFAULT_LIMIT : limit;
//                userFollowsResponse.offset = offset == null ? TwitchConstants.DEFAULT_OFFSET : offset;
//                userFollowsResponse.direction = direction == null ? TwitchConstants.DEFAULT_DIRECTION : direction;
//                userFollowsResponse.sortBy = sortBy == null ? TwitchConstants.DEFAULT_SORT_BY : sortBy;
//                return userFollowsResponse;
//            }
//        });

        return getService().getUserFollows(userId, limit, offset, direction, sortBy);
    }
    //</editor-fold>
}
