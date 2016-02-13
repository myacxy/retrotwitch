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
        final Lock<FollowsContainer> lock = new Lock<>();

        Caller.getInstance().getUserFollows("myacxy", null, null, null, null, new Caller.ResponseListener<FollowsContainer>()
        {
            @Override
            public void onSuccess(FollowsContainer followsContainer)
            {
                lock.succeed(followsContainer);
            }

            @Override
            public void onError()
            {
                lock.fail();
            }
        });

        lock.await();
        System.out.println(lock.result);
    }

    @Test(timeout = 10000)
    public void getAllUserFollows() throws Exception
    {
        final Lock<List<Follow>> lock = new Lock<>();

        Caller.getInstance().getAllUserFollows("sodapoppin", Direction.DEFAULT, SortBy.DEFAULT, new Caller.ResponseListener<List<Follow>>()
        {
            @Override
            public void onSuccess(List<Follow> follows)
            {
                lock.succeed(follows);
            }

            @Override
            public void onError()
            {
                lock.fail();
            }
        });

        lock.await();
        System.out.println(lock.result.size());
        assertTrue(lock.result.size() > 300);
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
            public void onError()
            {
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
                        lock.succeed(streamsContainer);
                    }

                    @Override
                    public void onError()
                    {
                        lock.fail();
                    }
                });
            }

            @Override
            public void onError()
            {
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
            public void onError()
            {
                lock.fail();
            }
        });

        lock.await();
        System.out.println(lock.result);
    }

    @Test
    public void getAllStreams() throws Exception
    {
        final Lock<List<Stream>> lock = new Lock<>();

        Caller.getInstance().getAllStreams(null, null, null, StreamType.LIVE, 500, new Caller.ResponseListener<List<Stream>>()
        {
            @Override
            public void onSuccess(List<Stream> streams)
            {
                lock.succeed(streams);
            }

            @Override
            public void onError()
            {
                lock.fail();
            }
        });

        lock.await();
        System.out.println(lock.result.size());
        assertTrue(lock.result.size() == 500);
    }
}
