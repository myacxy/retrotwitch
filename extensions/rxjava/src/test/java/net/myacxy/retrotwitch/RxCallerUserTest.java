package net.myacxy.retrotwitch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.myacxy.retrotwitch.models.User;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class RxCallerUserTest
{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Test(timeout = 5000)
    public void getUser() throws Exception
    {
        User user = RxCaller.getInstance().getUser("myacxy").toBlocking().first();
        System.out.println(GSON.toJson(user));
        assertThat(user.bio, is(equalTo(null)));
        assertThat(user.logo, is(equalTo(null)));
        assertThat(user.displayName, is(equalTo("myacxy")));
        assertThat(user.name, is(equalTo("myacxy")));
    }
}
