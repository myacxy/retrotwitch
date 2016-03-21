package net.myacxy.retrotwitch;

import net.myacxy.retrotwitch.api.*;
import net.myacxy.retrotwitch.models.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.logging.HttpLoggingInterceptor;
import rx.observables.BlockingObservable;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class RxCallerTest
{
    @Before
    public void setUp() {
        RxCaller.getInstance().setLoggingLevel(HttpLoggingInterceptor.Level.BASIC);
    }

    @Test(timeout = 5000)
    public void getUserFollows() throws Exception
    {
        BlockingObservable<UserFollowsContainer> observable = RxCaller.getInstance().getUserFollows("myacxy", null, null, null, null).toBlocking();

        System.out.println(observable.first().userFollows.size());
    }

    @Test(timeout = 50000)
    public void getAllUserFollows() throws Exception
    {
        List<UserFollow> follows = new ArrayList<>(300);
        BlockingObservable<UserFollowsContainer> observable = RxCaller.getInstance().getAllUserFollows("sodapoppin", Direction.DEFAULT, SortBy.DEFAULT)
                .doOnNext(userFollowsContainer -> follows.addAll(userFollowsContainer.userFollows))
                .toBlocking();

        observable.last();
        System.out.println(follows.size());
        assertTrue(follows.size() > 300);
    }

    @Test(timeout = 5000)
    public void getStream() throws Exception
    {
        BlockingObservable<StreamContainer> observable = RxCaller.getInstance().getStream("merlinidota").toBlocking();

        Stream merlini = observable.first().stream;
        System.out.print(merlini);
        if(merlini != null) {
            assertThat(merlini.channel.displayName.equalsIgnoreCase("merlinidota"), is(true));
        }
    }

    @Test(timeout = 5000)
    public void getUserFollowsStreams() throws Exception
    {
        BlockingObservable<StreamsContainer> observable = RxCaller.getInstance().getUserFollows("myacxy", null, null, null, null)
                .flatMap(userFollowsContainer -> RxCaller.getInstance().getStreams(null,
                        userFollowsContainer.userFollows.stream().map(userFollow -> userFollow.channel).collect(Collectors.toList()),
                        null, null, null, null))
                .toBlocking();

        System.out.println(observable.last().streams.size());
    }

    @Test(timeout = 5000)
    public void getStreams() throws Exception
    {
        BlockingObservable<StreamsContainer> observable = RxCaller.getInstance().getStreams(null, null, null, null, null, null).toBlocking();

        StreamsContainer streamsContainer = observable.first();
        System.out.println(observable.first().streams);
        assertThat(streamsContainer.streams.size(), equalTo(TwitchV3Service.DEFAULT_LIMIT));
    }

    @Test
    public void get500LiveStreams() throws Exception
    {
        List<Stream> streams = new ArrayList<>(500);
        BlockingObservable<StreamsContainer> observable = RxCaller.getInstance().getAllStreams(null, null, null, StreamType.LIVE, 500)
                .doOnNext(streamsContainer -> streams.addAll(streamsContainer.streams))
                .toBlocking();

        observable.last();
        assertThat(streams.size(), equalTo(500));
        streams.forEach(stream -> assertThat(stream, notNullValue()));

    }
}
