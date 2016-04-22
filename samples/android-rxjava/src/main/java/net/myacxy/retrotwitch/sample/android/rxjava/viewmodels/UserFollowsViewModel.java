package net.myacxy.retrotwitch.sample.android.rxjava.viewmodels;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;
import android.widget.Toast;

import net.myacxy.retrotwitch.RxCaller;
import net.myacxy.retrotwitch.api.SortBy;
import net.myacxy.retrotwitch.helpers.RxErrorFactory;
import net.myacxy.retrotwitch.models.Error;
import net.myacxy.retrotwitch.models.UserFollow;
import net.myacxy.retrotwitch.models.UserFollowsContainer;
import net.myacxy.retrotwitch.utils.StringUtil;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserFollowsViewModel
{
    public final ObservableInt totalCount = new ObservableInt();
    public final ObservableInt currentlyLoadedCount = new ObservableInt();
    public final ObservableBoolean loading = new ObservableBoolean();
    public final ObservableField<String> userName = new ObservableField<>();
    public final ObservableArrayList<UserFollow> userFollows = new ObservableArrayList<>();
    private Subscription mSubscription;
    private Context mContext;
    private UserFollowsContainer mUserFollowsContainer;
    private final Observer<UserFollowsContainer> mObserver = new Observer<UserFollowsContainer>()
    {
        @Override
        public void onCompleted()
        {
            loading.set(false);
        }

        @Override
        public void onError(Throwable t)
        {
            loading.set(false);
            setInformation(0, 0);
            Error error = RxErrorFactory.fromThrowable(t);
            Toast.makeText(mContext, error.message, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(UserFollowsContainer userFollowsContainer)
        {
            userFollows.addAll(userFollowsContainer.userFollows);
            setInformation(userFollowsContainer.total, userFollows.size());
            UserFollowsViewModel.this.mUserFollowsContainer = userFollowsContainer;
        }
    };

    public UserFollowsViewModel(Context context)
    {
        mContext = context;
    }

    public void onSearchClicked(View view)
    {
        String user = userName.get() != null ? userName.get().trim() : null;
        if (!StringUtil.isBlank(user))
        {
            userFollows.clear();
            loading.set(true);
            setInformation(0, 0);
            if (mSubscription != null)
            {
                mSubscription.unsubscribe();
            }
            mSubscription = RxCaller.getInstance()
                    .getUserFollows(user, 100, null, null, SortBy.LAST_BROADCAST)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mObserver);
        } else
        {
            Toast.makeText(mContext, "user name must not be empty", Toast.LENGTH_SHORT).show();
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
            mSubscription.unsubscribe();
        }
    }

    public void loadNext()
    {
        if (mUserFollowsContainer.offset + mUserFollowsContainer.limit > mUserFollowsContainer.total)
        {
            return;
        } else if (mSubscription != null)
        {
            mSubscription.unsubscribe();
        }
        loading.set(true);
        mSubscription = RxCaller.getInstance()
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
