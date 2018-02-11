package net.myacxy.retrotwitch.sample.android.rxjava.viewmodels;

import android.databinding.ObservableField;

import net.myacxy.retrotwitch.v5.RxRetroTwitch;
import net.myacxy.retrotwitch.v5.api.common.Scope;


public class AuthenticationViewModel {

    private final RxRetroTwitch retroTwitch;

    public ObservableField<String> accessToken = new ObservableField<>("unknown");

    public AuthenticationViewModel(RxRetroTwitch retroTwitch) {
        this.retroTwitch = retroTwitch;
    }

    public String createAuthenticationUrl() {
        return retroTwitch.authenticate()
                .setClientId("75gzbgqhk0tg6dhjbqtsphmy8sdayrr")
                .setRedirectUri("http://localhost/retrotwitchtest")
                .setScopes(Scope.USER_READ)
                .setScopes()
                .buildUrl();
    }
}
