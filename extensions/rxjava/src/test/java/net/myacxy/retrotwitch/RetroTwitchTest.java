package net.myacxy.retrotwitch;

import net.myacxy.retrotwitch.v3.models.Scope;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class RetroTwitchTest
{
    private String mAuthenticationUrl = "https://api.twitch.tv/kraken/oauth2/authorize"
            + "?response_type=token"
            + "&redirect_uri=http://localhost/retrotwitchtest"
            + "&client_id=75gzbgqhk0tg6dhjbqtsphmy8sdayrr"
            + "&scope=user_read";

    @Before
    public void setUp() throws Exception
    {

    }

    @Test
    public void authenticateWithCallback() throws Exception
    {
        RetroTwitch.getInstance().authenticate()
                .setClientId("75gzbgqhk0tg6dhjbqtsphmy8sdayrr")
                .setRedirectUri("http://localhost/retrotwitchtest")
                .setScopes(Scope.USER_READ)
                .build(url -> assertThat(url, equalTo(mAuthenticationUrl)));
    }

    @Test
    public void authenticate() throws Exception
    {

        String url = RetroTwitch.getInstance().authenticate()
                .setClientId("75gzbgqhk0tg6dhjbqtsphmy8sdayrr")
                .setRedirectUri("http://localhost/retrotwitchtest")
                .setScopes(Scope.USER_READ)
                .buildUrl();

        System.out.println(url);

        assertThat(url, equalTo(mAuthenticationUrl));
    }
}
