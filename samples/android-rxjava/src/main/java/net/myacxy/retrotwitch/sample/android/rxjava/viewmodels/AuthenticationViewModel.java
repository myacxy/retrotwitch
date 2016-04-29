package net.myacxy.retrotwitch.sample.android.rxjava.viewmodels;

import android.databinding.ObservableField;

import net.myacxy.retrotwitch.RetroTwitch;
import net.myacxy.retrotwitch.api.Scope;

public class AuthenticationViewModel
{
    public ObservableField<String> accessToken = new ObservableField<>("unknown");

    public String createAuthenticationUrl()
    {
        return RetroTwitch.getInstance()
                .authenticate()
                .setClientId("75gzbgqhk0tg6dhjbqtsphmy8sdayrr")
                .setRedirectUri("http://localhost/retrotwitchtest")
                .addScopes(Scope.USER_READ)
                .addScopes()
                .buildUrl();
    }
}
