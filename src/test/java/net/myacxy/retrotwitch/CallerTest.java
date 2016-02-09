package net.myacxy.retrotwitch;

import net.myacxy.retrotwitch.api.*;
import net.myacxy.retrotwitch.models.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CallerTest extends BaseTest
{
    @Test(timeout = 5000)
    public void getUserFollows() throws Exception
    {
        final Lock lock = new Lock();

        Caller.getInstance().getUserFollows("myacxy", null, null, null, null, new Caller.ResponseListener<FollowsContainer>()
        {
            @Override
            public void onSuccess(FollowsContainer followsContainer)
            {
                System.out.println(followsContainer);
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

    @Test(timeout = 10000)
    public void getAllUserFollows() throws Exception
    {
        final Lock lock = new Lock();

        Caller.getInstance().getAllUserFollows("sodapoppin", Direction.DEFAULT, SortBy.DEFAULT, new Caller.ResponseListener<List<Follow>>()
        {
            @Override
            public void onSuccess(List<Follow> follows)
            {
                System.out.println(follows.size());
                assertTrue(follows.size() > 300);
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
    public void getUserFollowsStreams() throws Exception
    {
        final Lock lock = new Lock();

        Caller.getInstance().getUserFollows("myacxy", null, null, null, null, new Caller.ResponseListener<FollowsContainer>()
        {
            @Override
            public void onSuccess(FollowsContainer followsContainer)
            {
                List<Channel> channels = new ArrayList<>(followsContainer.total);
                for (Follow follow : followsContainer.follows)
                {
                    channels.add(follow.channel);
                }
                Caller.getInstance().getStreams(null, channels, null, null, null, null, new Caller.ResponseListener<StreamsContainer>()
                {
                    @Override
                    public void onSuccess(StreamsContainer streamsContainer)
                    {
                        System.out.println(streamsContainer);
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

        Caller.getInstance().getStreams(null, null, null, null, null, null, new Caller.ResponseListener<StreamsContainer>()
        {
            @Override
            public void onSuccess(StreamsContainer streamsContainer)
            {
                System.out.println(streamsContainer);
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
