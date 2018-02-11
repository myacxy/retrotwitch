package net.myacxy.retrotwitch.sample.android.rxjava;

import net.myacxy.retrotwitch.sample.android.rxjava.viewmodels.AuthenticationViewModel;
import net.myacxy.retrotwitch.sample.android.rxjava.viewmodels.UserFollowsViewModel;
import net.myacxy.retrotwitch.v5.RxRetroTwitch;

public class SimpleViewModelLocator {

    private final RxRetroTwitch retroTwitch;
    private UserFollowsViewModel userFollowsViewModel;
    private AuthenticationViewModel authenticationViewModel;

    public SimpleViewModelLocator(RxRetroTwitch retroTwitch) {
        this.retroTwitch = retroTwitch;
    }

    public UserFollowsViewModel getUserFollows() {
        return userFollowsViewModel == null ? userFollowsViewModel = new UserFollowsViewModel(retroTwitch) : userFollowsViewModel;
    }

    public AuthenticationViewModel getAuthentication() {
        return authenticationViewModel == null ? authenticationViewModel = new AuthenticationViewModel(retroTwitch) : authenticationViewModel;
    }
}
