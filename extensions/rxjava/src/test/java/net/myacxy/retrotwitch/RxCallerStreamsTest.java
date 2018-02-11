package net.myacxy.retrotwitch;

import net.myacxy.retrotwitch.v5.RxRetroTwitch;
import net.myacxy.retrotwitch.v5.api.common.Direction;
import net.myacxy.retrotwitch.v5.api.common.SortBy;
import net.myacxy.retrotwitch.v5.api.common.StreamType;
import net.myacxy.retrotwitch.v5.api.streams.Stream;
import net.myacxy.retrotwitch.v5.api.streams.StreamResponse;
import net.myacxy.retrotwitch.v5.api.streams.StreamsResponse;
import net.myacxy.retrotwitch.v5.api.users.UserFollow;

import org.junit.Before;
import org.junit.Test;

import java.util.stream.Collectors;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class RxCallerStreamsTest {

    private RxRetroTwitch retroTwitch;

    @Before
    public void setUp() {
        retroTwitch = new RxRetroTwitch()
                .configure(new Configuration.ConfigurationBuilder()
                        .setClientId("75gzbgqhk0tg6dhjbqtsphmy8sdayrr")
                        .setLogLevel(HttpLoggingInterceptor.Level.BASIC)
                        .build()
                );
    }

    @Test(timeout = 5000)
    public void getStream() throws Exception {
        Response<StreamResponse> streamResponse = retroTwitch.users()
                .translateUserNameToUserId("merlinidota")
                .map(response -> response.body().getUsers().get(0).getId())
                .flatMap(userId -> retroTwitch.streams().getStreamByUser(userId, StreamType.DEFAULT))
                .blockingGet();

        Stream stream = streamResponse.body().getStream();
        if (stream != null) {
            assertThat(stream.getChannel().getDisplayName().equalsIgnoreCase("merlinidota"), is(true));
        }
    }

    @Test(timeout = 5000)
    public void getUserFollowsStreams() throws Exception {
        Response<StreamsResponse> streams = retroTwitch.users()
                .translateUserNameToUserId("myacxy")
                .map(response -> response.body()
                        .getUsers()
                        .get(0)
                        .getId()
                ).flatMap(userId -> retroTwitch.users()
                        .getUserFollows(userId, 100, 0, Direction.DEFAULT, SortBy.DEFAULT)
                ).map(response -> response.body()
                        .getUserFollows()
                        .stream()
                        .map(UserFollow::getChannel)
                        .collect(Collectors.toList())
                ).flatMap(channels -> retroTwitch.streams()
                        .getStreams(channels, null, null, StreamType.ALL, 100, 0)
                ).blockingGet();
        System.out.println(streams.body().getStreams());
    }

    @Test(timeout = 5000)
    public void getStreams() throws Exception {
        Response<StreamsResponse> streams = retroTwitch.streams()
                .getStreams(null, null, null, StreamType.DEFAULT, 100, 0)
                .blockingGet();
        System.out.println(streams.body().getStreams());
    }
}
