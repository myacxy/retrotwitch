package net.myacxy.retrotwitch;

import net.myacxy.retrotwitch.api.*;
import net.myacxy.retrotwitch.models.*;
import net.myacxy.retrotwitch.models.Stream;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;

import okhttp3.logging.HttpLoggingInterceptor;
import rx.Observer;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class RxCallerStreamsTest
{
    @Before
    public void setUp() {
        RxCaller.getInstance().setLoggingLevel(HttpLoggingInterceptor.Level.BASIC);
    }

    @Test(timeout = 5000)
    public void getStream() throws Exception
    {
        final Stream[] merlini = {null};
        RxCaller.getInstance()
                .getStream("merlinidota")
                .toBlocking()
                .subscribe(new Observer<StreamContainer>()
                {
                    @Override
                    public void onCompleted()
                    {
                        if(merlini[0] != null) {
                            assertThat(merlini[0].channel.displayName.equalsIgnoreCase("merlinidota"), is(true));
                            System.out.println("merlinidota is online");
                        } else {
                            System.err.println("merlinidota is offline");
                        }
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        fail(ErrorFactory.fromThrowable(e).toString());
                    }

                    @Override
                    public void onNext(StreamContainer streamContainer)
                    {
                        merlini[0] = streamContainer.stream;
                    }
                });
    }

    @Test(timeout = 5000)
    public void getUserFollowsStreams() throws Exception
    {
        List<Stream> streams = new ArrayList<>(10);
        RxCaller.getInstance().getUserFollows("myacxy", null, null, null, null)
                .flatMap(userFollowsContainer -> RxCaller.getInstance().getStreams(
                        null,
                        userFollowsContainer.userFollows.stream()
                                .map(userFollow -> userFollow.channel)
                                .collect(Collectors.toList()),
                        null,
                        null,
                        null,
                        null))
                .toBlocking()
                .subscribe(new Observer<StreamsContainer>()
                {
                    @Override
                    public void onCompleted()
                    {
                        if(streams.size() == 0) {
                            System.err.println("all followed streams are offline");
                        } else {
                            streams.forEach(stream -> System.out.println(stream.channel.displayName));
                        }
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        fail(ErrorFactory.fromThrowable(e).toString());
                    }

                    @Override
                    public void onNext(StreamsContainer streamsContainer)
                    {
                        streams.addAll(streamsContainer.streams);
                    }
                });
    }

    @Test(timeout = 5000)
    public void getStreams() throws Exception
    {
        List<Stream> streams = new ArrayList<>(TwitchV3Service.DEFAULT_LIMIT);
        RxCaller.getInstance()
                .getStreams(null, null, null, null, null, null)
                .toBlocking()
                .subscribe(new Observer<StreamsContainer>()
                {
                    @Override
                    public void onCompleted()
                    {
                        assertThat(streams.size(), is(equalTo(TwitchV3Service.DEFAULT_LIMIT)));
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        fail(ErrorFactory.fromThrowable(e).toString());
                    }

                    @Override
                    public void onNext(StreamsContainer streamsContainer)
                    {
                        streams.addAll(streamsContainer.streams);
                    }
                });
    }


    @Test
    public void getLiveStreams() throws Exception
    {
        getLiveStreams(1);
        getLiveStreams(99);
        getLiveStreams(100);
        getLiveStreams(101);
        getLiveStreams(123);
        getLiveStreams(321);
    }

    private void getLiveStreams(int count) throws Exception
    {
        List<Stream> streams = new ArrayList<>(count);
        RxCaller.getInstance().getAllStreams(null, null, null, StreamType.LIVE, count)
                .toBlocking()
                .subscribe(new Observer<StreamsContainer>()
                {
                    @Override
                    public void onCompleted()
                    {
                        // streams may go offline during the request and thus results may not be exact
                        assertThat(streams.size(), greaterThanOrEqualTo(count - (int) (count * 0.05f)));
                        assertThat(streams.size(), lessThanOrEqualTo(count));
                        if(streams.size() != count) {
                            System.err.println("requested=" + count + ", actual=" + streams.size());
                        }
                        streams.forEach(stream -> assertThat(stream, notNullValue()));
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        fail(ErrorFactory.fromThrowable(e).toString());
                    }

                    @Override
                    public void onNext(StreamsContainer streamsContainer)
                    {
                        streams.addAll(streamsContainer.streams);
                    }
                });
    }
}
