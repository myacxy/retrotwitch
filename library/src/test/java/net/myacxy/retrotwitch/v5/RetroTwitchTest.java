package net.myacxy.retrotwitch.v5;

import net.myacxy.retrotwitch.Configuration;
import net.myacxy.retrotwitch.helpers.TestConstants;
import net.myacxy.retrotwitch.v5.api.common.Scope;

import org.junit.Before;
import org.junit.Test;

import okhttp3.logging.HttpLoggingInterceptor;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class RetroTwitchTest {

    private static final String URL_AUTHENTICATION = "https://api.twitch.tv/kraken/oauth2/authorize"
            + "?response_type=token"
            + "&redirect_uri=" + TestConstants.TEST_REDIRECT_URI
            + "&client_id=" + TestConstants.TEST_CLIENT_ID
            + "&scope=user_read";

    RetroTwitch retroTwitch;

    @Before
    public void setUp() throws Exception {
        retroTwitch = new RetroTwitch()
                .configure(new Configuration.ConfigurationBuilder()
                        .setClientId(TestConstants.TEST_CLIENT_ID)
                        .setLogLevel(HttpLoggingInterceptor.Level.BODY)
                        .build()
                );
    }

    @Test
    public void authenticate() throws Exception {
        String url = retroTwitch.authenticate()
                .setClientId(TestConstants.TEST_CLIENT_ID)
                .setRedirectUri(TestConstants.TEST_REDIRECT_URI)
                .setScopes(Scope.USER_READ)
                .buildUrl();

        assertThat(url, equalTo(URL_AUTHENTICATION));
    }
}
