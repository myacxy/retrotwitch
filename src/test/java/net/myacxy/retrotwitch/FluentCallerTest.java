package net.myacxy.retrotwitch;

import net.myacxy.retrotwitch.api.*;
import net.myacxy.retrotwitch.models.*;
import net.myacxy.retrotwitch.resources.*;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

public class FluentCallerTest extends BaseTest
{

    @Test
    public void stream() throws Exception
    {
        final MultiLock multiLock = new MultiLock(3);

        FluentCaller.getInstance()
                .stream("ladyydeathstrike").build().enqueue(new Caller.ResponseListener<Stream>()
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

        System.out.println(multiLock.getSingleResult("stream", Stream.class).channel.name);
        System.out.println(multiLock.getMultiResult("limitedFollows", ArrayList.class).size());
        System.out.println(multiLock.getMultiResult("allFollows", ArrayList.class).size());
    }
}
