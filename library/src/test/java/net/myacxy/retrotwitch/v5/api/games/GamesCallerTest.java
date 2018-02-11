package net.myacxy.retrotwitch.v5.api.games;

import net.myacxy.retrotwitch.Configuration;
import net.myacxy.retrotwitch.helpers.Lock;
import net.myacxy.retrotwitch.helpers.TestConstants;
import net.myacxy.retrotwitch.v5.RetroTwitch;
import net.myacxy.retrotwitch.v5.api.ResponseListener;
import net.myacxy.retrotwitch.v5.api.common.Error;

import org.junit.Before;
import org.junit.Test;

import okhttp3.logging.HttpLoggingInterceptor;

public class GamesCallerTest {

    private RetroTwitch retroTwitch;

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
    public void getTopGames() throws Exception {
        final Lock<TopGamesResponse> lock = new Lock<>();
        retroTwitch.games()
                .getTopGames(100, 0, new ResponseListener<TopGamesResponse>() {
                    @Override
                    public void onSuccess(TopGamesResponse topGamesResponse) {
                        System.out.println(topGamesResponse.toString());
                        lock.succeed(topGamesResponse);
                    }

                    @Override
                    public void onError(Error error) {
                        System.out.println(error.toString());
                        lock.fail();
                    }
                });
        lock.await();
    }
}