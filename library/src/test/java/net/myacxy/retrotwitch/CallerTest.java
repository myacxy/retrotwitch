package net.myacxy.retrotwitch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.myacxy.retrotwitch.helpers.Lock;
import net.myacxy.retrotwitch.models.Channel;
import net.myacxy.retrotwitch.models.Error;
import net.myacxy.retrotwitch.models.Stream;
import net.myacxy.retrotwitch.models.StreamsContainer;
import net.myacxy.retrotwitch.models.User;
import net.myacxy.retrotwitch.models.UserFollow;
import net.myacxy.retrotwitch.models.UserFollowsContainer;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import okhttp3.logging.HttpLoggingInterceptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CallerTest
{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Before
    public void setUp() throws Exception
    {
        RetroTwitch.getInstance().configure()
                .setClientId("75gzbgqhk0tg6dhjbqtsphmy8sdayrr")
                .setLogLevel(HttpLoggingInterceptor.Level.BASIC)
                .apply();
    }

    @Test(timeout = 5000)
    public void getUserFollows() throws Exception
    {
        final Lock<UserFollowsContainer> lock = new Lock<>();

        Caller.getInstance().getUserFollows("myacxy", null, null, null, null, new Caller.ResponseListener<UserFollowsContainer>()
        {
            @Override
            public void onSuccess(UserFollowsContainer userFollowsContainer)
            {
                lock.succeed(userFollowsContainer);
            }

            @Override
            public void onError(Error error)
            {
                System.err.println(error);
                lock.fail();
            }
        });

        lock.await();
        System.out.println(lock.result);
    }

    @Test(timeout = 5000)
    public void getStream() throws Exception
    {
        final Lock<Stream> lock = new Lock<>();

        Caller.getInstance().getStream("merlinidota", new Caller.ResponseListener<Stream>()
        {
            @Override
            public void onSuccess(Stream stream)
            {
                lock.succeed(stream);
            }

            @Override
            public void onError(Error error)
            {
                System.err.println(error);
                lock.fail();
            }
        });

        lock.await();
        System.out.print(lock.result);
    }

    @Test(timeout = 5000)
    public void getUserFollowsStreams() throws Exception
    {
        final Lock<StreamsContainer> lock = new Lock<>();

        Caller.getInstance().getUserFollows("myacxy", null, null, null, null, new Caller.ResponseListener<UserFollowsContainer>()
        {
            @Override
            public void onSuccess(UserFollowsContainer userFollowsContainer)
            {
                List<Channel> channels = new ArrayList<>(userFollowsContainer.total);
                for (UserFollow userFollow : userFollowsContainer.userFollows)
                {
                    channels.add(userFollow.channel);
                }
                Caller.getInstance().getStreams(null, channels, null, null, null, null, new Caller.ResponseListener<StreamsContainer>()
                {
                    @Override
                    public void onSuccess(StreamsContainer streamsContainer)
                    {
                        lock.succeed(streamsContainer);
                    }

                    @Override
                    public void onError(Error error)
                    {
                        System.err.println(error);
                        lock.fail();
                    }
                });
            }

            @Override
            public void onError(Error error)
            {
                System.err.println(error);
                lock.fail();
            }
        });

        lock.await();
        System.out.println(lock.result);
    }

    @Test(timeout = 5000)
    public void getStreams() throws Exception
    {
        final Lock<StreamsContainer> lock = new Lock<>();

        Caller.getInstance().getStreams(null, null, null, null, null, null, new Caller.ResponseListener<StreamsContainer>()
        {
            @Override
            public void onSuccess(StreamsContainer streamsContainer)
            {
                lock.succeed(streamsContainer);
            }

            @Override
            public void onError(Error error)
            {
                System.err.println(error);
                lock.fail();
            }
        });

        lock.await();
        System.out.println(lock.result);
    }

    @Test(timeout = 5000)
    public void getUser() throws Exception
    {
        final Lock<User> lock = new Lock<>();

        Caller.getInstance().getUser("myacxy", new Caller.ResponseListener<User>()
        {
            @Override
            public void onSuccess(User user)
            {
                lock.succeed(user);
            }

            @Override
            public void onError(Error error)
            {
                System.err.println(error);
                lock.fail();
            }
        });

        lock.await();
        System.out.println(GSON.toJson(lock.result));
        assertNull(lock.result.bio);
        assertNull(lock.result.logo);
        assertEquals(lock.result.displayName, "myacxy");
    }
}
