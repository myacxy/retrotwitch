package net.myacxy.retrotwitch;

import net.myacxy.retrotwitch.api.*;
import net.myacxy.retrotwitch.models.*;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CallerTest extends BaseTest
{
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
        final Lock<List<UserFollow>> lock = new Lock<>();

        Caller.getInstance().getAllUserFollows("sodapoppin", Direction.DEFAULT, SortBy.DEFAULT, new Caller.ResponseListener<List<UserFollow>>()
        {
            @Override
            public void onSuccess(List<UserFollow> userFollows)
            {
                lock.succeed(userFollows);
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

    @Test
    public void getStreamsBuilder()
    {
        StreamsContainer streamsContainer = null;
        try
        {
            streamsContainer = new StreamsContainer.CallBuilder().buildAndExecute();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        if (streamsContainer != null)
        {
            assertEquals(25, streamsContainer.streams.size());
        }
    }

    @Test
    public void getUserFollowsWithBuilder() throws Exception
    {
        final Lock<List<UserFollow>> lock = new Lock<>();

        new UserFollowsContainer.CallBuilder("sodapoppin").withLimit(100).buildAndGetAll(new Caller.ResponseListener<List<UserFollow>>()
        {
            @Override
            public void onSuccess(List<UserFollow> userFollows)
            {
                lock.succeed(userFollows);
            }

            @Override
            public void onError()
            {
                lock.fail();
            }
        });

        lock.await();
    }
}
