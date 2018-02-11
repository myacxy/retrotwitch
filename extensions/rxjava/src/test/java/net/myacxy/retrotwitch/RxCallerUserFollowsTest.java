package net.myacxy.retrotwitch;

import net.myacxy.retrotwitch.v5.RxRetroTwitch;
import net.myacxy.retrotwitch.v5.api.common.Direction;
import net.myacxy.retrotwitch.v5.api.common.SortBy;
import net.myacxy.retrotwitch.v5.api.users.SimpleUsersResponse;
import net.myacxy.retrotwitch.v5.api.users.UserFollow;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class RxCallerUserFollowsTest {

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

    //<editor-fold desc="[ Get User Follows ]">
    @Test(timeout = 5000)
    public void getUserFollows() throws Exception {
        List<UserFollow> userFollows = retroTwitch.users().translateUserNameToUserId("myacxy")
                .map(response -> response.body().getUsers().get(0).getId())
                .flatMap(userId -> retroTwitch.users().getUserFollows(userId, 100, 0, Direction.DEFAULT, SortBy.DEFAULT))
                .blockingGet()
                .body()
                .getUserFollows();

        System.out.println(userFollows);
        assertThat(userFollows.size(), Matchers.greaterThan(0));
    }

    @Test
    public void notFoundErrorGetUserFollows() throws Exception {
        Response<SimpleUsersResponse> response = retroTwitch.users()
                .translateUserNameToUserId("userThatDoesNotExist")
                .blockingGet();

        assertThat(response.isSuccessful(), is(true));
        assertThat(response.body().getUsers(), hasSize(0));
    }
    //</editor-fold>
}
