package net.myacxy.retrotwitch.v5.api.users;

import net.myacxy.retrotwitch.v5.api.BaseCaller;
import net.myacxy.retrotwitch.v5.api.ResponseListener;
import net.myacxy.retrotwitch.v5.api.RetroTwitchCallback;
import net.myacxy.retrotwitch.v5.api.SimpleRetroTwitchCallback;
import net.myacxy.retrotwitch.v5.api.common.Direction;
import net.myacxy.retrotwitch.v5.api.common.SortBy;
import net.myacxy.retrotwitch.v5.api.common.TwitchConstants;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;

// TODO: 14.02.2017
public class RxUsersCaller extends BaseCaller<TwitchUsersService> {

    //<editor-fold desc="Constructor">
    public RxUsersCaller(OkHttpClient client) {
        super(client);
    }
    //</editor-fold>

    @Override
    protected TwitchUsersService createService(Retrofit retrofit) {
        return retrofit.create(TwitchUsersService.class);
    }

    //<editor-fold desc="API Calls">
    public Call<PrivilegedUser> getUser(final ResponseListener<PrivilegedUser> listener) {
        Call<PrivilegedUser> call = getService().getUser();
        call.enqueue(new SimpleRetroTwitchCallback<>(listener));
        return call;
    }

    public Call<SimpleUser> getUserById(long userId, final ResponseListener<SimpleUser> listener) {
        Call<SimpleUser> call = getService().getUserById(userId);
        call.enqueue(new SimpleRetroTwitchCallback<>(listener));
        return call;
    }

    public Call<SimpleUsersResponse> translateUserNameToUserId(String userName, final ResponseListener<List<SimpleUser>> listener) {
        Call<SimpleUsersResponse> call = getService().translateUserNameToUserId(userName);
        call.enqueue(new RetroTwitchCallback<SimpleUsersResponse, List<SimpleUser>>(listener) {
            @Override
            public List<SimpleUser> beforeOnSuccess(SimpleUsersResponse simpleUsersResponse) {
                return simpleUsersResponse.getUsers();
            }
        });
        return call;
    }

    public Call<UserFollowsResponse> getUserFollows(
            final long userId,
            final Integer limit,
            final Integer offset,
            final Direction direction,
            final SortBy sortBy,
            final ResponseListener<UserFollowsResponse> listener) {
        Call<UserFollowsResponse> call = getService().getUserFollows(userId, limit, offset, direction, sortBy);
        call.enqueue(new RetroTwitchCallback<UserFollowsResponse, UserFollowsResponse>(listener) {

            @Override
            public UserFollowsResponse beforeOnSuccess(UserFollowsResponse userFollowsResponse) {
                userFollowsResponse.userId = userId;
                userFollowsResponse.limit = limit == null ? TwitchConstants.DEFAULT_LIMIT : limit;
                userFollowsResponse.offset = offset == null ? TwitchConstants.DEFAULT_OFFSET : offset;
                userFollowsResponse.direction = direction == null ? TwitchConstants.DEFAULT_DIRECTION : direction;
                userFollowsResponse.sortBy = sortBy == null ? TwitchConstants.DEFAULT_SORT_BY : sortBy;
                return userFollowsResponse;
            }
        });
        return call;
    }
    //</editor-fold>
}
