package net.myacxy.retrotwitch;

import net.myacxy.retrotwitch.v3.models.UserFollowsContainer;
import net.myacxy.retrotwitch.v5.RxCaller;
import net.myacxy.retrotwitch.v5.helpers.RxErrorFactory;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.logging.HttpLoggingInterceptor;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class RxCallerUserFollowsTest
{
    @Before
    public void setUp()
    {
        RxCaller.getInstance().setLoggingLevel(HttpLoggingInterceptor.Level.BASIC);
        RxCaller.getInstance().setClientId("75gzbgqhk0tg6dhjbqtsphmy8sdayrr");
    }

    //<editor-fold desc="[ Get User Follows ]">
    @Test(timeout = 5000)
    public void getUserFollows() throws Exception
    {
        RxCaller.getInstance()
                .getUserFollows("myacxy", null, null, null, null)
                .blockingSubscribe(new Observer<UserFollowsContainer>()
                {
                    @Override
                    public void onComplete()
                    {
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        fail(RxErrorFactory.fromThrowable(e).toString());
                    }

                    @Override
                    public void onNext(UserFollowsContainer userFollowsContainer)
                    {
                        System.out.println(userFollowsContainer.userFollows.size());
                    }

                    @Override
                    public void onSubscribe(Disposable d)
                    {
                    }
                });
    }

    @Test
    public void notFoundErrorGetUserFollows() throws Exception
    {
        RxCaller.getInstance()
                .getUserFollows("userThatDoesNotExist", null, null, null, null)
                .blockingSubscribe(new Observer<UserFollowsContainer>()
                {
                    @Override
                    public void onComplete()
                    {
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        Error error = RxErrorFactory.fromThrowable(e);
                        assertThat(error.getType(), is(equalTo(Error.Type.NOT_FOUND)));
                    }

                    @Override
                    public void onNext(UserFollowsContainer userFollowsContainer)
                    {
                    }

                    @Override
                    public void onSubscribe(Disposable d)
                    {

                    }
                });

    }

    @Test
    public void unprocessableEntityErrorGetUserFollows() throws Exception
    {
        RxCaller.getInstance()
                .getUserFollows("test", null, null, null, null)
                .blockingSubscribe(new Observer<UserFollowsContainer>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        Error error = RxErrorFactory.fromThrowable(e);
                        assertThat(error.getType(), is(equalTo(Error.Type.UNPROCESSABLE_ENTITY)));
                    }

                    @Override
                    public void onNext(UserFollowsContainer userFollowsContainer)
                    {
                    }

                    @Override
                    public void onComplete()
                    {
                    }
                });

    }
    //</editor-fold>
}
