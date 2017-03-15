package net.myacxy.retrotwitch.v5.api.search;

import net.myacxy.retrotwitch.Configuration;
import net.myacxy.retrotwitch.helpers.Lock;
import net.myacxy.retrotwitch.helpers.TestConstants;
import net.myacxy.retrotwitch.v5.RetroTwitch;
import net.myacxy.retrotwitch.v5.api.ResponseListener;
import net.myacxy.retrotwitch.v5.api.common.RetroTwitchError;

import org.junit.Before;
import org.junit.Test;

import okhttp3.logging.HttpLoggingInterceptor;

public class SearchCallerTest {

    @Before
    public void setUp() throws Exception {
        RetroTwitch.getInstance()
                .configure(new Configuration.ConfigurationBuilder()
                        .setClientId(TestConstants.TEST_CLIENT_ID)
                        .setLogLevel(HttpLoggingInterceptor.Level.BODY)
                        .build()
                );
    }

    @Test
    public void searchChannels() throws Exception {
        final Lock<SearchChannelsResponse> lock = new Lock<>();
        RetroTwitch.getInstance()
                .search()
                .searchChannels("Zelda", 100, 0, new ResponseListener<SearchChannelsResponse>() {
                    @Override
                    public void onSuccess(SearchChannelsResponse response) {
                        System.out.println(response.toString());
                        lock.succeed(response);
                    }

                    @Override
                    public void onError(RetroTwitchError error) {
                        System.out.println(error.toString());
                        lock.fail();
                    }
                });
        lock.await();
    }

    @Test
    public void searchGames() throws Exception {
        final Lock<SearchGamesResponse> lock = new Lock<>();
        RetroTwitch.getInstance()
                .search()
                .searchGames("Zelda", true, new ResponseListener<SearchGamesResponse>() {
                    @Override
                    public void onSuccess(SearchGamesResponse response) {
                        System.out.println(response.toString());
                        lock.succeed(response);
                    }

                    @Override
                    public void onError(RetroTwitchError error) {
                        System.out.println(error.toString());
                        lock.fail();
                    }
                });
        lock.await();
    }

    @Test
    public void searchStreams() throws Exception {
        final Lock<SearchStreamsResponse> lock = new Lock<>();
        RetroTwitch.getInstance()
                .search()
                .searchStreams("Zelda", null, 100, 0, new ResponseListener<SearchStreamsResponse>() {
                    @Override
                    public void onSuccess(SearchStreamsResponse response) {
                        System.out.println(response.toString());
                        lock.succeed(response);
                    }

                    @Override
                    public void onError(RetroTwitchError error) {
                        System.out.println(error.toString());
                        lock.fail();
                    }
                });
        lock.await();
    }
}