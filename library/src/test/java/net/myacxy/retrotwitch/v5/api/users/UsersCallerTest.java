package net.myacxy.retrotwitch.v5.api.users;

import net.myacxy.retrotwitch.helpers.Lock;
import net.myacxy.retrotwitch.helpers.TestConstants;
import net.myacxy.retrotwitch.v5.RetroTwitch;
import net.myacxy.retrotwitch.v5.api.ResponseListener;
import net.myacxy.retrotwitch.v5.api.common.Error;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import okhttp3.logging.HttpLoggingInterceptor;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class UsersCallerTest {

    @Before
    public void setUp() throws Exception {
        RetroTwitch.getInstance()
                .configure()
                .setClientId(TestConstants.TEST_CLIENT_ID)
                .setLogLevel(HttpLoggingInterceptor.Level.BODY)
                .apply();
    }

    @Test
    public void getUser() throws Exception {

    }

    @Test
    public void getUserById() throws Exception {

    }

    @Test
    public void translateUserNameToUserId() throws Exception {
        final Lock<List<SimpleUser>> lock = new Lock<>();
        RetroTwitch.getInstance().users().translateUserNameToUserId(TestConstants.TEST_USER_NAME, new ResponseListener<List<SimpleUser>>() {
            @Override
            public void onSuccess(List<SimpleUser> simpleUsers) {
                SimpleUser user = simpleUsers.get(0);
                Assert.assertThat(user.name, is(equalTo(TestConstants.TEST_USER_NAME)));
                Assert.assertThat(user.displayName, is(equalTo(TestConstants.TEST_USER_NAME)));
                Assert.assertThat(user.id, is(equalTo(TestConstants.TEST_USER_ID)));
                Assert.assertThat(user.type, is(equalTo("user")));
                Assert.assertThat(user.bio, is(equalTo(null)));
                Assert.assertThat(user.logo, is(equalTo(null)));
                lock.succeed(simpleUsers);
            }

            @Override
            public void onError(Error error) {
                lock.fail();
                Assert.fail(error.toString());
            }
        });
        lock.await();
    }

    @Test
    public void getUserFollows() throws Exception {

    }

}