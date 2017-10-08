package net.myacxy.retrotwitch.v5.api.users;

import net.myacxy.retrotwitch.v5.api.ResponseListener;
import net.myacxy.retrotwitch.v5.api.RxBaseCaller;
import net.myacxy.retrotwitch.v5.api.common.Direction;
import net.myacxy.retrotwitch.v5.api.common.SortBy;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;

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
        return getService().translateUserNameToUserId(userName);
    }

    public Single<Response<UserFollowsResponse>> getUserFollows(
            final long userId,
            final Integer limit,
            final Integer offset,
            final Direction direction,
            final SortBy sortBy) {
        return getService().getUserFollows(userId, limit, offset, direction, sortBy);
    }
    //</editor-fold>
}
