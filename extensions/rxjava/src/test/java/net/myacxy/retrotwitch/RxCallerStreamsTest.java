package net.myacxy.retrotwitch;

import net.myacxy.retrotwitch.api.TwitchV3Service;
import net.myacxy.retrotwitch.helpers.RxErrorFactory;
import net.myacxy.retrotwitch.models.Stream;
import net.myacxy.retrotwitch.models.StreamContainer;
import net.myacxy.retrotwitch.models.StreamsContainer;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.logging.HttpLoggingInterceptor;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@SuppressWarnings("NewApi")
public class RxCallerStreamsTest
{
    @Before
    public void setUp()
    {
        RxCaller.getInstance().setLoggingLevel(HttpLoggingInterceptor.Level.BASIC);
        RxCaller.getInstance().setClientId("75gzbgqhk0tg6dhjbqtsphmy8sdayrr");
    }

    @Test(timeout = 5000)
    public void getStream() throws Exception
    {
        final Stream[] merlini = {null};
        RxCaller.getInstance()
                .getStream("merlinidota")
                .blockingSubscribe(new Observer<StreamContainer>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        fail(RxErrorFactory.fromThrowable(e).toString());
                    }

                    @Override
                    public void onNext(StreamContainer streamContainer)
                    {
                        merlini[0] = streamContainer.stream;
                    }

                    @Override
                    public void onComplete()
                    {
                        if (merlini[0] != null)
                        {
                            assertThat(merlini[0].channel.displayName.equalsIgnoreCase("merlinidota"), is(true));
                            System.out.println("merlinidota is online");
                        } else
                        {
                            System.err.println("merlinidota is offline");
                        }
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
                .blockingSubscribe(new Observer<StreamsContainer>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {

                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        fail(RxErrorFactory.fromThrowable(e).toString());
                    }

                    @Override
                    public void onNext(StreamsContainer streamsContainer)
                    {
                        streams.addAll(streamsContainer.streams);
                    }

                    @Override
                    public void onComplete()
                    {
                        if (streams.size() == 0)
                        {
                            System.err.println("all followed streams are offline");
                        } else
                        {
                            streams.forEach(stream -> System.out.println(stream.channel.displayName));
                        }
                    }
                });
    }

    @Test(timeout = 5000)
    public void getStreams() throws Exception
    {
        List<Stream> streams = new ArrayList<>(TwitchV3Service.DEFAULT_LIMIT);
        RxCaller.getInstance()
                .getStreams(null, null, null, null, null, null)
                .blockingSubscribe(new Observer<StreamsContainer>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {

                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        fail(RxErrorFactory.fromThrowable(e).toString());
                    }

                    @Override
                    public void onNext(StreamsContainer streamsContainer)
                    {
                        streams.addAll(streamsContainer.streams);
                    }

                    @Override
                    public void onComplete()
                    {
                        assertThat(streams.size(), is(equalTo(TwitchV3Service.DEFAULT_LIMIT)));
                    }
                });
    }
}
