package net.myacxy.retrotwitch;

import net.myacxy.retrotwitch.api.*;
import net.myacxy.retrotwitch.models.*;
import net.myacxy.retrotwitch.models.Error;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.logging.HttpLoggingInterceptor;
import rx.Observer;
import rx.observables.BlockingObservable;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class RxCallerUserFollowsTest
{
    @Before
    public void setUp() {
        RxCaller.getInstance().setLoggingLevel(HttpLoggingInterceptor.Level.BASIC);
    }

    //<editor-fold desc="[ Get User Follows ]">
    @Test(timeout = 5000)
    public void getUserFollows() throws Exception
    {
        RxCaller.getInstance()
                .getUserFollows("myacxy", null, null, null, null)
                .toBlocking()
                .subscribe(new Observer<UserFollowsContainer>()
                {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e) {
                        fail(ErrorFactory.fromThrowable(e).toString());
                    }

                    @Override
                    public void onNext(UserFollowsContainer userFollowsContainer)
                    {
                        System.out.println(userFollowsContainer.userFollows.size());
                    }
                });
    }

    @Test
    public void notFoundErrorGetUserFollows() throws Exception
    {
        RxCaller.getInstance()
                .getUserFollows("userThatDoesNotExist", null, null, null, null)
                .toBlocking()
                .subscribe(new Observer<UserFollowsContainer>()
                {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e)
                    {
                        Error error = ErrorFactory.fromThrowable(e);
                        assertThat(error.getType(), is(equalTo(Error.Type.NOT_FOUND)));
                    }

                    @Override
                    public void onNext(UserFollowsContainer userFollowsContainer) { }
                });

    }

    @Test
    public void unprocessableEntityErrorGetUserFollows() throws Exception
    {
        RxCaller.getInstance()
                .getUserFollows("test", null, null, null, null)
                .toBlocking()
                .subscribe(new Observer<UserFollowsContainer>()
                {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable e)
                    {
                        Error error = ErrorFactory.fromThrowable(e);
                        assertThat(error.getType(), is(equalTo(Error.Type.UNPROCESSABLE_ENTITY)));
                    }

                    @Override
                    public void onNext(UserFollowsContainer userFollowsContainer) { }
                });

    }
    //</editor-fold>

    //<editor-fold desc="[ Get All User Follows ]">
    @Test(timeout = 5000)
    public void getAllUserFollows() throws Exception
    {
        List<UserFollow> follows = new ArrayList<>(300);
        RxCaller.getInstance().getAllUserFollows("sodapoppin", Direction.DEFAULT, SortBy.DEFAULT)
                .toBlocking()
                .subscribe(new Observer<UserFollowsContainer>()
                {
                    @Override
                    public void onCompleted()
                    {
                        System.out.println(follows.size());
                        assertThat(follows.size(), is(greaterThan(300)));
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        fail(ErrorFactory.fromThrowable(e).toString());
                    }

                    @Override
                    public void onNext(UserFollowsContainer userFollowsContainer)
                    {
                        follows.addAll(userFollowsContainer.userFollows);
                    }
                });
    }
    //</editor-fold>
}
