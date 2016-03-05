package net.myacxy.retrotwitch;

import net.myacxy.retrotwitch.api.*;
import net.myacxy.retrotwitch.models.*;
import net.myacxy.retrotwitch.resources.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

public class FluentCallerTest extends BaseTest
{

    @Test(timeout = 15000)
    public void stream() throws Exception
    {
        final MultiLock multiLock = new MultiLock(3);

        RetroTwitch.configure().setLogLevel(HttpLoggingInterceptor.Level.BASIC).apply().getCaller()
                .stream("enns").build().enqueue(new Caller.ResponseListener<Stream>()
                    {
                        @Override
                        public void onSuccess(Stream stream)
                        {
                            multiLock.succeed("stream", stream);
                        }

                        @Override
                        public void onError()
                        {
                            multiLock.fail();
                        }
                    })
                .userFollows("myacxy").limited().withLimit(5).build().enqueue(new Caller.ResponseListener<List<UserFollow>>()
                    {
                        @Override
                        public void onSuccess(List<UserFollow> userFollows)
                        {
                            multiLock.succeed("limitedFollows", userFollows);
                        }

                        @Override
                        public void onError()
                        {
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
                        public void onError()
                        {
                            multiLock.fail();
                        }
                    });

        multiLock.await();

        Stream enns = multiLock.getSingleResult("stream", Stream.class);
        if(enns != null) {
            assertThat(enns.channel.name, is("enns"));
        }

        ArrayList<UserFollow> myacxyLimitedFollows = multiLock.getMultiResult("limitedFollows", ArrayList.class);
        assertThat(myacxyLimitedFollows, is(notNullValue()));
        assertThat(myacxyLimitedFollows.size() == 5, is(true));

        ArrayList<UserFollow> sodapoppinAllFollows = multiLock.getMultiResult("allFollows", ArrayList.class);
        assertThat(sodapoppinAllFollows, is(notNullValue()));
        assertThat(sodapoppinAllFollows.size() > 350, is(true));
    }
}
