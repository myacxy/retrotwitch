package net.myacxy.retrotwitch.sample.android.rxjava.views;

import android.databinding.Observable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import net.myacxy.retrotwitch.sample.android.rxjava.R;
import net.myacxy.retrotwitch.sample.android.rxjava.viewmodels.UserFollowsViewModel;
import net.myacxy.retrotwitch.v5.api.users.UserFollow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserFollowsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int VIEW_TYPE_USER_FOLLOW = 0;
    private final static int VIEW_TYPE_REFRESH = 1;

    private final Observable.OnPropertyChangedCallback loadingCallback = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable observable, int i) {
            notifyDataSetChanged();
        }
    };

    private UserFollowsViewModel userFollowsViewModel;

    public UserFollowsAdapter(UserFollowsViewModel viewModel) {
        userFollowsViewModel = viewModel;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        userFollowsViewModel.loading.addOnPropertyChangedCallback(loadingCallback);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        userFollowsViewModel.loading.removeOnPropertyChangedCallback(loadingCallback);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case VIEW_TYPE_USER_FOLLOW:
                View itemView = layoutInflater.inflate(R.layout.item_user_follow, parent, false);
                return new UserFollowViewHolder(itemView);
            case VIEW_TYPE_REFRESH:
                itemView = layoutInflater.inflate(R.layout.item_refresh, parent, false);
                return new RefreshViewHolder(itemView);
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_USER_FOLLOW:
                ((UserFollowViewHolder) holder).bind(getItem(position));
                break;
            case VIEW_TYPE_REFRESH:
                ((RefreshViewHolder) holder).showProgress(userFollowsViewModel.loading.get());
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == userFollowsViewModel.userFollows.size() ? VIEW_TYPE_REFRESH : VIEW_TYPE_USER_FOLLOW;
    }

    @Override
    public int getItemCount() {
        return userFollowsViewModel.userFollows.size() + (userFollowsViewModel.loading.get() ? 1 : 0);
    }

    public int getRealItemCount() {
        return userFollowsViewModel.userFollows.size();
    }

    public ArrayList<UserFollow> getUserFollows() {
        return userFollowsViewModel.userFollows;
    }

    public void setUserFollows(List<UserFollow> userFollows) {
        userFollowsViewModel.userFollows.clear();
        addUserFollows(userFollows);
    }

    public void addUserFollows(List<UserFollow> userFollows) {
        userFollowsViewModel.userFollows.addAll(userFollows);
        userFollowsViewModel.currentlyLoadedCount.set(userFollowsViewModel.userFollows.size());
    }

    private UserFollow getItem(int position) {
        return userFollowsViewModel.userFollows.get(position);
    }

    protected class UserFollowViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_iuf_channel_name)
        protected TextView mChannelName;

        public UserFollowViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(UserFollow userFollow) {
            mChannelName.setText(userFollow.getChannel().getName());
        }
    }

    protected class RefreshViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fl_progress)
        protected FrameLayout progress;

        public RefreshViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void showProgress(boolean show) {
            progress.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }
}
