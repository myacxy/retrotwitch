package net.myacxy.retrotwitch;

import net.myacxy.retrotwitch.v5.RxRetroTwitch;
import net.myacxy.retrotwitch.v5.api.users.SimpleUser;

import org.junit.Before;
import org.junit.Test;

import okhttp3.logging.HttpLoggingInterceptor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class RxCallerUserTest {

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
    public void getUser() throws Exception {
        SimpleUser user = retroTwitch.users()
                .translateUserNameToUserId("myacxy")
                .map(response -> response.body().getUsers().get(0))
                .blockingGet();

        assertThat(user.getBio(), is(equalTo(null)));
        assertThat(user.getDisplayName(), is(equalTo("myacxy")));
        assertThat(user.getName(), is(equalTo("myacxy")));
    }
}
