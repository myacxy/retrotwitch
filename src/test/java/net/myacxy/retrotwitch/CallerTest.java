package net.myacxy.retrotwitch;

import net.myacxy.retrotwitch.models.Follow;
import net.myacxy.retrotwitch.models.Stream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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

        Caller.getInstance().getUserFollows("myacxy", new Caller.ResponseListener<List<Follow>>()
        {
            @Override
            public void onSuccess(List<Follow> follows)
            {
                System.out.println(follows);
                lock.release();
            }

            @Override
            public void onError()
            {
                lock.release();
                fail();
            }
        });

        lock.await();
    }

    @Test(timeout = 5000)
    public void getStream() throws Exception
    {
        final Lock lock = new Lock();

        Caller.getInstance().getStream("forsenlol", new Caller.ResponseListener<Stream>()
        {
            @Override
            public void onSuccess(Stream stream)
            {
                assertNotNull(stream);
                lock.release();
            }

            @Override
            public void onError()
            {
                lock.release();
                fail();
            }
        });

        lock.await();
    }

    @Test(timeout = 5000)
    public void getStreams() throws Exception
    {
        final Lock lock = new Lock();

        Caller.getInstance().getUserFollows("myacxy", new Caller.ResponseListener<List<Follow>>()
        {
            @Override
            public void onSuccess(List<Follow> follows)
            {
                List<String> channelNames = new ArrayList<>(follows.size());
                for (Follow follow : follows) {
                    channelNames.add(follow.channel.name);
                }

                Caller.getInstance().getStreams(null, channelNames, null, null, null, null, new Caller.ResponseListener<List<Stream>>()
                {
                    @Override
                    public void onSuccess(List<Stream> streams)
                    {
                        System.out.println(streams);
                        lock.release();
                    }

                    @Override
                    public void onError()
                    {
                        lock.release();
                        fail();
                    }
                });
            }

            @Override
            public void onError()
            {
                lock.release();
                fail();
            }
        });

        lock.await();
    }
}
