package net.myacxy.retrotwitch.sample.android.rxjava.viewmodels;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.text.TextUtils;

import net.myacxy.retrotwitch.v5.RxRetroTwitch;
import net.myacxy.retrotwitch.v5.api.common.Direction;
import net.myacxy.retrotwitch.v5.api.common.RetroTwitchError;
import net.myacxy.retrotwitch.v5.api.common.SortBy;
import net.myacxy.retrotwitch.v5.api.common.TwitchConstants;
import net.myacxy.retrotwitch.v5.api.users.SimpleUser;
import net.myacxy.retrotwitch.v5.api.users.UserFollow;
import net.myacxy.retrotwitch.v5.api.users.UserFollowsResponse;
import net.myacxy.retrotwitch.v5.helpers.RxRetroTwitchErrorFactory;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class UserFollowsViewModel {

    public final ObservableInt totalCount = new ObservableInt();
    public final ObservableInt currentlyLoadedCount = new ObservableInt();
    public final ObservableBoolean loading = new ObservableBoolean();
    public final ObservableField<String> twitchUserName = new ObservableField<>();
    public final ObservableField<String> errorMessage = new ObservableField<>();
    public final ObservableArrayList<UserFollow> userFollows = new ObservableArrayList<>();

    private final RxRetroTwitch retroTwitch;

    private Disposable disposable;
    private UserFollowsMeta userFollowsMeta;

    public UserFollowsViewModel(RxRetroTwitch retroTwitch) {
        this.retroTwitch = retroTwitch;
    }

    public void search() {
        String userName = twitchUserName.get() != null ? twitchUserName.get().trim() : null;
        if (!TextUtils.isEmpty(userName)) {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }

            disposable = retroTwitch.users().translateUserNameToUserId(userName)
                    .map(response -> {
                        if (response.isSuccessful()) {
                            List<SimpleUser> users = response.body().getUsers();
                            if (users != null && users.size() > 0) {
                                SimpleUser user = users.get(0);
                                userFollowsMeta = new UserFollowsMeta(user.getId());
                                return user.getId();
                            }
                        }
                        throw new IllegalArgumentException("Invalid user name");
                    })
                    .flatMap(userId -> retroTwitch.users().getUserFollows(userId, 100, userFollowsMeta.currentOffset, Direction.DEFAULT, SortBy.DEFAULT))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(disposable -> {
                        userFollowsMeta = null;
                        userFollows.clear();
                        loading.set(true);
                        setInformation(0, 0);
                    })
                    .doFinally(() -> loading.set(false))
                    .subscribeWith(new DisposableSingleObserver<Response<UserFollowsResponse>>() {
                        @Override
                        public void onSuccess(Response<UserFollowsResponse> response) {
                            UserFollowsResponse body = response.body();
                            userFollows.addAll(body.getUserFollows());
                            userFollowsMeta.total = body.getTotal();
                            setInformation(userFollowsMeta.total, userFollows.size());
                        }

                        @Override
                        public void onError(Throwable e) {
                            userFollowsMeta = null;
                            setInformation(0, 0);
                            RetroTwitchError error = RxRetroTwitchErrorFactory.fromThrowable(e);
                            errorMessage.set(error.getMessage());
                        }
                    });
        } else {
            errorMessage.set("User name must not be empty");
        }
    }

    private void setInformation(Integer total, int currentlyLoaded) {
        totalCount.set(total != null ? total : 0);
        currentlyLoadedCount.set(currentlyLoaded);
    }

    public void reset() {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public void loadNext() {
        int nextOffset = userFollowsMeta.currentOffset + userFollowsMeta.limit;
        if (nextOffset > userFollowsMeta.total) {
            return;
        } else if (disposable != null && disposable.isDisposed()) {
            disposable.dispose();
        }
        disposable = retroTwitch.users()
                .getUserFollows(userFollowsMeta.userId,
                        userFollowsMeta.limit,
                        nextOffset,
                        Direction.DEFAULT,
                        SortBy.DEFAULT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    loading.set(true);
                })
                .doFinally(() -> loading.set(false))
                .subscribeWith(new DisposableSingleObserver<Response<UserFollowsResponse>>() {
                    @Override
                    public void onSuccess(Response<UserFollowsResponse> response) {
                        UserFollowsResponse body = response.body();
                        userFollows.addAll(body.getUserFollows());
                        userFollowsMeta.currentOffset = nextOffset;
                        setInformation(userFollowsMeta.total, userFollows.size());
                    }

                    @Override
                    public void onError(Throwable e) {
                        RetroTwitchError error = RxRetroTwitchErrorFactory.fromThrowable(e);
                        errorMessage.set(error.getMessage());
                    }
                });
    }

    private static class UserFollowsMeta {

        private final long userId;
        private final int limit = TwitchConstants.MAX_LIMIT;
        private int currentOffset = 0;
        private int total = 0;

        private UserFollowsMeta(long userId) {
            this.userId = userId;
        }
    }
}
