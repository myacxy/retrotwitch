package net.myacxy.retrotwitch.sample.android.rxjava;

import net.myacxy.retrotwitch.sample.android.rxjava.viewmodels.AuthenticationViewModel;
import net.myacxy.retrotwitch.sample.android.rxjava.viewmodels.UserFollowsViewModel;

public class SimpleViewModelLocator
{
    private final static SimpleViewModelLocator INSTANCE = new SimpleViewModelLocator();

    private UserFollowsViewModel mUserFollowsViewModel;
    private AuthenticationViewModel mAuthenticationViewModel;

    public static SimpleViewModelLocator getInstance() {
        return INSTANCE;
    }

    public UserFollowsViewModel getUserFollows() {
        return mUserFollowsViewModel == null ? mUserFollowsViewModel = new UserFollowsViewModel() : mUserFollowsViewModel;
    }

    public AuthenticationViewModel getAuthentication() {
        return mAuthenticationViewModel == null ? mAuthenticationViewModel = new AuthenticationViewModel() : mAuthenticationViewModel;
    }
}
