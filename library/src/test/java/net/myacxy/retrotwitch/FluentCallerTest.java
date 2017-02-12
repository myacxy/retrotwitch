package net.myacxy.retrotwitch;

import net.myacxy.retrotwitch.v3.Caller;
import net.myacxy.retrotwitch.v3.models.Error;
import net.myacxy.retrotwitch.v3.models.Stream;
import net.myacxy.retrotwitch.v3.models.UserFollow;
import net.myacxy.retrotwitch.v5.helpers.Lock;
import net.myacxy.retrotwitch.v5.helpers.MultiLock;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import okhttp3.logging.HttpLoggingInterceptor;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

public class FluentCallerTest
{

    @Before
    public void setUp()
    {
        RetroTwitch.getInstance().configure()
                .setClientId("75gzbgqhk0tg6dhjbqtsphmy8sdayrr")
                .setLogLevel(HttpLoggingInterceptor.Level.BASIC)
                .apply();
    }

    @Test(timeout = 5000)
    public void stream() throws Exception
    {
        final Lock<Stream> lock = new Lock<>();

        RetroTwitch.getInstance().getFluent().stream("enns").build().enqueue(new Caller.ResponseListener<Stream>()
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

        if (lock.result != null)
        {
            assertThat(lock.result.channel.name, is("enns"));
        }
    }

    @Test(timeout = 10000)
    public void userFollows() throws Exception
    {
        final MultiLock multiLock = new MultiLock(2);

        RetroTwitch.getInstance().getFluent()
                .userFollows("myacxy").limited().withLimit(5).build().enqueue(new Caller.ResponseListener<List<UserFollow>>()
                    {
                        @Override
                        public void onSuccess(List<UserFollow> userFollows)
                        {
                            multiLock.succeed("limitedFollows", userFollows);
                        }

                        @Override
                        public void onError(Error error)
                        {
                            System.err.println(error);
                            multiLock.fail();
                        }
                    })
                .userFollows("sodapoppin").all().withLimit(100).withMaximum(100).build().enqueue(new Caller.ResponseListener<List<UserFollow>>()
                    {
                        @Override
                        public void onSuccess(List<UserFollow> userFollows)
                        {
                            multiLock.succeed("allFollows", userFollows);
                        }

                        @Override
                        public void onError(Error error)
                        {
                            System.err.println(error);
                            multiLock.fail();
                        }
                    });

        multiLock.await();

        ArrayList<UserFollow> myacxyLimitedFollows = multiLock.getMultiResult("limitedFollows", ArrayList.class);
        assertThat(myacxyLimitedFollows, is(notNullValue()));
        assertThat(myacxyLimitedFollows, hasSize(5));

        ArrayList<UserFollow> sodapoppinAllFollows = multiLock.getMultiResult("allFollows", ArrayList.class);
        assertThat(sodapoppinAllFollows, is(notNullValue()));
        assertThat(sodapoppinAllFollows.size(), greaterThan(350));
    }
}
