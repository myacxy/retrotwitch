package net.myacxy.retrotwitch;

import net.myacxy.retrotwitch.models.*;

import okhttp3.logging.HttpLoggingInterceptor;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class FluentCallerTest
{

//    @Before
//    public void setUp()
//    {
//        RetroTwitch.INSTANCE.configure()
//                .setLogLevel(HttpLoggingInterceptor.Level.BASIC)
//                .apply();
//    }
//
//    @Test(timeout = 5000)
//    public void stream() throws Exception
//    {
//        RetroTwitch.INSTANCE.getCaller().stream("enns").build().enqueue(new Caller.ResponseListener<Stream>()
//        {
//            @Override
//            public void onSuccess(Stream stream)
//            {
//                lock.succeed(stream);
//            }
//
//            @Override
//            public void onError()
//            {
//                lock.fail();
//            }
//        });
//
//        lock.await();
//
//        if (lock.result != null)
//        {
//            assertThat(lock.result.channel.name, is("enns"));
//        }
//    }
//
//    @Test(timeout = 10000)
//    public void userFollows() throws Exception
//    {
//        final MultiLock multiLock = new MultiLock(2);
//
//        RetroTwitch.INSTANCE.getCaller().userFollows("myacxy").limited().withLimit(5).build().enqueue(new Caller.ResponseListener<List<UserFollow>>()
//        {
//            @Override
//            public void onSuccess(List<UserFollow> userFollows)
//            {
//                multiLock.succeed("limitedFollows", userFollows);
//            }
//
//            @Override
//            public void onError()
//            {
//                multiLock.fail();
//            }
//        })
//                .userFollows("sodapoppin").all().withLimit(100).withMaximum(100).build().enqueue(new Caller.ResponseListener<List<UserFollow>>()
//        {
//            @Override
//            public void onSuccess(List<UserFollow> userFollows)
//            {
//                multiLock.succeed("allFollows", userFollows);
//            }
//
//            @Override
//            public void onError()
//            {
//                multiLock.fail();
//            }
//        });
//
//        multiLock.await();
//
//        ArrayList<UserFollow> myacxyLimitedFollows = multiLock.getMultiResult("limitedFollows", ArrayList.class);
//        assertThat(myacxyLimitedFollows, is(notNullValue()));
//        assertThat(myacxyLimitedFollows.size() == 5, is(true));
//
//        ArrayList<UserFollow> sodapoppinAllFollows = multiLock.getMultiResult("allFollows", ArrayList.class);
//        assertThat(sodapoppinAllFollows, is(notNullValue()));
//        assertThat(sodapoppinAllFollows.size() > 350, is(true));
//    }
}
