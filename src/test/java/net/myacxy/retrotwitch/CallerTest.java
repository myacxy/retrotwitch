package net.myacxy.retrotwitch;

import net.myacxy.retrotwitch.models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;
import static org.junit.Assert.*;

public class CallerTest extends BaseTest
{

    @Before
    public void setUp() throws Exception
    {

    }

    @After
    public void tearDown() throws Exception
    {

    }

    @Test(timeout = 5000)
    public void getFollows() throws Exception
    {
        final Lock lock = new Lock();

        Caller.getInstance().getUserFollows("myacxy", null, null, null, null, new Caller.ResponseListener<Follows>()
        {
            @Override
            public void onSuccess(Follows follows)
            {
                System.out.println(follows);
                lock.release();
            }

            @Override
            public void onError()
            {
                fail();
            }
        });

        lock.await();
    }

    @Test(timeout = 5000)
    public void getStream() throws Exception
    {
        final Lock lock = new Lock();

        Caller.getInstance().getStream("merlinidota", new Caller.ResponseListener<Stream>()
        {
            @Override
            public void onSuccess(Stream stream)
            {
                System.out.print(stream);
                lock.release();
            }

            @Override
            public void onError()
            {
                fail();
            }
        });

        lock.await();
    }

    @Test(timeout = 5000)
    public void getFollowsStreams() throws Exception
    {
        final Lock lock = new Lock();

        Caller.getInstance().getUserFollows("myacxy", null, null, null, null, new Caller.ResponseListener<Follows>()
        {
            @Override
            public void onSuccess(Follows follows)
            {
                List<Channel> channels = new ArrayList<>(follows.total);
                for (Follow follow : follows.follows)
                {
                    channels.add(follow.channel);
                }
                Caller.getInstance().getStreams(null, channels, null, null, null, null, new Caller.ResponseListener<Streams>()
                {
                    @Override
                    public void onSuccess(Streams streams)
                    {
                        System.out.println(streams);
                        lock.release();
                    }

                    @Override
                    public void onError()
                    {
                        fail();
                    }
                });
            }

            @Override
            public void onError()
            {
                fail();
            }
        });

        lock.await();
    }

    @Test(timeout = 5000)
    public void getStreams() throws Exception
    {
        final Lock lock = new Lock();

        Caller.getInstance().getStreams(null, null, null, null, null, null, new Caller.ResponseListener<Streams>()
        {
            @Override
            public void onSuccess(Streams streams)
            {
                System.out.println(streams);
                lock.release();
            }

            @Override
            public void onError()
            {
                fail();
            }
        });

        lock.await();
    }
}
