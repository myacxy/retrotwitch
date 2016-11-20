package net.myacxy.retrotwitch.sample.android.rxjava.views;

import android.databinding.Observable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.myacxy.retrotwitch.models.UserFollow;
import net.myacxy.retrotwitch.sample.android.rxjava.R;
import net.myacxy.retrotwitch.sample.android.rxjava.viewmodels.UserFollowsViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserFollowsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private final static int VIEW_TYPE_USER_FOLLOW = 0;
    private final static int VIEW_TYPE_REFRESH = 1;

    private UserFollowsViewModel mViewModel;

    public UserFollowsAdapter(UserFollowsViewModel viewModel)
    {
        mViewModel = viewModel;
        mViewModel.loading.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback()
        {
            @Override
            public void onPropertyChanged(Observable observable, int i)
            {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        switch (viewType)
        {
            case VIEW_TYPE_USER_FOLLOW:
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_follow, parent, false);
                return new UserFollowViewHolder(itemView);
            case VIEW_TYPE_REFRESH:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_refresh, parent, false);
                return new RefreshViewHolder(itemView);
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        switch (holder.getItemViewType())
        {
            case VIEW_TYPE_USER_FOLLOW:
                ((UserFollowViewHolder) holder).bind(getItem(position));
                break;
            case VIEW_TYPE_REFRESH:
                ((RefreshViewHolder) holder).showProgress(mViewModel.loading.get());
                break;
        }
    }

    @Override
    public int getItemViewType(int position)
    {
        return position == mViewModel.userFollows.size() ? VIEW_TYPE_REFRESH : VIEW_TYPE_USER_FOLLOW;
    }

    @Override
    public int getItemCount()
    {
        return mViewModel.userFollows.size() + (mViewModel.loading.get() ? 1 : 0);
    }

    public int getRealItemCount()
    {
        return mViewModel.userFollows.size();
    }

    public ArrayList<UserFollow> getUserFollows()
    {
        return mViewModel.userFollows;
    }

    public void setUserFollows(List<UserFollow> userFollows)
    {
        mViewModel.userFollows.clear();
        addUserFollows(userFollows);
    }

    public void addUserFollows(List<UserFollow> userFollows)
    {
        mViewModel.userFollows.addAll(userFollows);
        mViewModel.currentlyLoadedCount.set(mViewModel.userFollows.size());
    }

    private UserFollow getItem(int position)
    {
        return mViewModel.userFollows.get(position);
    }

    protected class UserFollowViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.tv_iuf_channel_name)
        protected TextView mChannelName;

        public UserFollowViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(UserFollow userFollow)
        {
            mChannelName.setText(userFollow.channel.name);
        }
    }

    protected class RefreshViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.pb_progress)
        protected ProgressBar mProgress;

        public RefreshViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void showProgress(boolean show)
        {
            ((ViewGroup) mProgress.getParent()).setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }
}
