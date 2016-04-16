package net.myacxy.retrotwitch;

import net.myacxy.retrotwitch.api.Scope;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

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
        RetroTwitch.INSTANCE.authenticate()
                .setClientId("75gzbgqhk0tg6dhjbqtsphmy8sdayrr")
                .setRedirectUri("http://localhost/retrotwitchtest")
                .addScopes(Scope.USER_READ)
                .build(new RetroTwitch.AuthenticationBuilder.Callback()
                {
                    @Override
                    public void authenticate(String url)
                    {
                        assertThat(url, equalTo(mAuthenticationUrl));
                    }
                });
    }

    @Test
    public void authenticate() throws Exception
    {

        String url = RetroTwitch.INSTANCE.authenticate()
                .setClientId("75gzbgqhk0tg6dhjbqtsphmy8sdayrr")
                .setRedirectUri("http://localhost/retrotwitchtest")
                .addScopes(Scope.USER_READ)
                .buildUrl();

        assertThat(url, equalTo(mAuthenticationUrl));
    }
}
