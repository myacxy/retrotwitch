package net.myacxy.retrotwitch.sample.android.rxjava.viewmodels;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import net.myacxy.retrotwitch.utils.StringUtil;
import net.myacxy.retrotwitch.v3.models.SortBy;
import net.myacxy.retrotwitch.v3.models.UserFollow;
import net.myacxy.retrotwitch.v3.models.UserFollowsContainer;
import net.myacxy.retrotwitch.v5.RxCaller;
import net.myacxy.retrotwitch.v5.helpers.RxErrorFactory;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserFollowsViewModel
{
    public final ObservableInt totalCount = new ObservableInt();
    public final ObservableInt currentlyLoadedCount = new ObservableInt();
    public final ObservableBoolean loading = new ObservableBoolean();
    public final ObservableField<String> userName = new ObservableField<>();
    public final ObservableField<String> errorMessage = new ObservableField<>();
    public final ObservableArrayList<UserFollow> userFollows = new ObservableArrayList<>();
    private Disposable mSubscription;
    private UserFollowsContainer mUserFollowsContainer;
    private final Observer<UserFollowsContainer> mObserver = new Observer<UserFollowsContainer>()
    {
        @Override
        public void onComplete()
        {
            loading.set(false);
        }

        @Override
        public void onError(Throwable t)
        {
            loading.set(false);
            setInformation(0, 0);
            Error error = RxErrorFactory.fromThrowable(t);
            errorMessage.set(error.message);
        }

        @Override
        public void onSubscribe(Disposable d)
        {
            mSubscription = d;
        }

        @Override
        public void onNext(UserFollowsContainer userFollowsContainer)
        {
            userFollows.addAll(userFollowsContainer.userFollows);
            setInformation(userFollowsContainer.total, userFollows.size());
            UserFollowsViewModel.this.mUserFollowsContainer = userFollowsContainer;
        }
    };

    public void search()
    {
        String user = userName.get() != null ? userName.get().trim() : null;
        if (!StringUtil.isEmpty(user))
        {
            userFollows.clear();
            loading.set(true);
            setInformation(0, 0);
            if (mSubscription != null)
            {
                mSubscription.dispose();
            }
            RxCaller.getInstance()
                    .getUserFollows(user, 100, null, null, SortBy.LAST_BROADCAST)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mObserver);
        } else
        {
            errorMessage.set("user must must not be empty");
        }
    }

    private void setInformation(Integer total, int currentlyLoaded)
    {
        totalCount.set(total != null ? total : 0);
        currentlyLoadedCount.set(currentlyLoaded);
    }

    public void reset()
    {
        if (mSubscription != null)
        {
            mSubscription.dispose();
        }
    }

    public void loadNext()
    {
        if (mUserFollowsContainer.offset + mUserFollowsContainer.limit > mUserFollowsContainer.total)
        {
            return;
        } else if (mSubscription != null)
        {
            mSubscription.dispose();
        }
        loading.set(true);
        RxCaller.getInstance()
                .getUserFollows(mUserFollowsContainer.user,
                        mUserFollowsContainer.limit,
                        mUserFollowsContainer.offset + mUserFollowsContainer.limit,
                        mUserFollowsContainer.direction,
                        mUserFollowsContainer.sortBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);
    }
}
