package net.myacxy.retrotwitch;

import net.myacxy.retrotwitch.api.Scope;
import org.junit.Before;
import org.junit.Test;

public class RetroTwitchTest
{

    @Before
    public void setUp() throws Exception
    {

    }

    @Test
    public void authenticateWithCallback() throws Exception
    {
        RetroTwitch.INSTANCE.authenticate()
                .setRedirectUri("http://localhost/retrotwitchtest")
                .addScopes(Scope.USER_READ)
                .build(new RetroTwitch.AuthenticationBuilder.Callback()
                {
                    @Override
                    public void authenticate(String url)
                    {
                        System.out.println(url);
                    }
                });
    }

    @Test
    public void authenticate() throws Exception
    {

        String url = RetroTwitch.INSTANCE.authenticate()
                .setRedirectUri("http://localhost/retrotwitchtest")
                .addScopes(Scope.USER_READ)
                .buildUrl();

        System.out.println(url);
    }
}