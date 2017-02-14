package net.myacxy.retrotwitch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.myacxy.retrotwitch.v3.models.User;
import net.myacxy.retrotwitch.v5.RxCaller;

import org.junit.Before;
import org.junit.Test;

import okhttp3.logging.HttpLoggingInterceptor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class RxCallerUserTest
{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Before
    public void setUp()
    {
        RxCaller.getInstance().setLoggingLevel(HttpLoggingInterceptor.Level.BASIC);
        RxCaller.getInstance().setClientId("75gzbgqhk0tg6dhjbqtsphmy8sdayrr");
    }

    @Test(timeout = 5000)
    public void getUser() throws Exception
    {
        User user = RxCaller.getInstance().getUser("myacxy").blockingFirst();
        System.out.println(GSON.toJson(user));
        assertThat(user.bio, is(equalTo(null)));
        assertThat(user.logo, is(equalTo(null)));
        assertThat(user.displayName, is(equalTo("myacxy")));
        assertThat(user.name, is(equalTo("myacxy")));
    }
}
