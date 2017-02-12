package net.myacxy.retrotwitch.v5;

import net.myacxy.retrotwitch.v5.api.common.Scope;

import org.junit.Before;
import org.junit.Test;

import okhttp3.logging.HttpLoggingInterceptor;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class RetroTwitchTest {
    private static final String CLIENT_ID = "75gzbgqhk0tg6dhjbqtsphmy8sdayrr";

    private String mAuthenticationUrl = "https://api.twitch.tv/kraken/oauth2/authorize"
            + "?response_type=token"
            + "&redirect_uri=http://localhost/retrotwitchtest"
            + "&client_id=75gzbgqhk0tg6dhjbqtsphmy8sdayrr"
            + "&scope=user_read";

    @Before
    public void setUp() throws Exception {
        RetroTwitch.getInstance()
                .configure()
                .setClientId(CLIENT_ID)
                .setLogLevel(HttpLoggingInterceptor.Level.BASIC)
                .apply();
    }

    @Test
    public void authenticateWithCallback() throws Exception {
        RetroTwitch.getInstance().authenticate()
                .setClientId("75gzbgqhk0tg6dhjbqtsphmy8sdayrr")
                .setRedirectUri("http://localhost/retrotwitchtest")
                .setScopes(Scope.USER_READ)
                .build(new RetroTwitch.AuthenticationBuilder.Callback() {
                    @Override
                    public void authenticate(String url) {
                        assertThat(url, equalTo(mAuthenticationUrl));
                    }
                });
    }

    @Test
    public void authenticate() throws Exception {
        String url = RetroTwitch.getInstance().authenticate()
                .setClientId("75gzbgqhk0tg6dhjbqtsphmy8sdayrr")
                .setRedirectUri("http://localhost/retrotwitchtest")
                .setScopes(Scope.USER_READ)
                .buildUrl();

        assertThat(url, equalTo(mAuthenticationUrl));
    }
}
