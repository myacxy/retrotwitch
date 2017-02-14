package net.myacxy.retrotwitch.sample.android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import net.myacxy.retrotwitch.utils.StringUtil;
import net.myacxy.retrotwitch.v5.RetroTwitch;
import net.myacxy.retrotwitch.v5.api.ResponseListener;
import net.myacxy.retrotwitch.v5.api.common.Error;
import net.myacxy.retrotwitch.v5.api.common.SortBy;
import net.myacxy.retrotwitch.v5.api.users.SimpleUser;
import net.myacxy.retrotwitch.v5.api.users.UserFollow;
import net.myacxy.retrotwitch.v5.api.users.UserFollowsResource;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class UserFollowsFragment extends Fragment {
    @BindView(R.id.et_uf_user_name)
    protected EditText mUserName;
    @BindView(R.id.btn_uf_search)
    protected Button mSearch;

    @BindView(R.id.tv_uf_total)
    protected TextView mTotal;
    @BindView(R.id.tv_uf_currently_loaded)
    protected TextView mCurrentlyLoaded;

    @BindView(R.id.tv_iuf_empty)
    protected TextView mEmpty;
    @BindView(R.id.rv_uf_results)
    protected RecyclerView mSearchResults;

    private UserFollowAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private UserFollowsResource mUserFollowsResource;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_follows, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        mAdapter = new UserFollowAdapter();
        mLayoutManager = new LinearLayoutManager(getContext());
        mSearchResults.setLayoutManager(mLayoutManager);
        mSearchResults.setAdapter(mAdapter);

        mSearchResults.addOnScrollListener(new ScrollListener());

        if (savedInstanceState != null) {
            ArrayList<UserFollow> userFollows = (ArrayList<UserFollow>) savedInstanceState.getSerializable("test");
            mAdapter.setUserFollows(userFollows);
            mUserFollowsResource = (UserFollowsResource) savedInstanceState.getSerializable("test2");
            setInformation(mUserFollowsResource.getTotal(), userFollows.size());
        } else {
            setInformation(0, 0);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("test", mAdapter.getUserFollows());
        outState.putSerializable("test2", mUserFollowsResource);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_uf_search)
    public void onSearchClicked() {
        String userName = mUserName.getText().toString().trim();
        if (!StringUtil.isBlank(userName)) {
            showProgress(true);
            mAdapter.getUserFollows().clear();
            setInformation(0, 0);
            RetroTwitch.getInstance().users().translateUserNameToUserId(userName, new ResponseListener<List<SimpleUser>>() {
                @Override
                public void onSuccess(List<SimpleUser> simpleUsers) {
                    mUserFollowsResource = RetroTwitch.getInstance()
                            .userFollows(simpleUsers.get(0).getId())
                            .limited()
                            .withSortBy(SortBy.LAST_BROADCAST)
                            .build();

                    mUserFollowsResource.enqueue(RetroTwitch.getInstance(), new ResponseListener<List<UserFollow>>() {
                        @Override
                        public void onSuccess(List<UserFollow> userFollows) {
                            showProgress(false);
                            mAdapter.setUserFollows(userFollows);
                            setInformation(mUserFollowsResource.getTotal(), userFollows.size());
                        }

                        @Override
                        public void onError(Error error) {
                            showProgress(false);
                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onError(Error error) {
                    showProgress(false);
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "user name must not be empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void setInformation(Integer total, int currentlyLoaded) {
        mTotal.setText(String.format(getString(R.string.total), total != null ? total : 0));
        mCurrentlyLoaded.setText(String.format(getString(R.string.currently_loaded), currentlyLoaded));
    }

    private void showProgress(boolean show) {
        mSearch.setEnabled(!show);
        mAdapter.showProgress(show);
        mEmpty.setVisibility(!show && mAdapter.getRealItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    //<editor-fold desc="Inner Classes">
    protected class UserFollowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final static int VIEW_TYPE_USER_FOLLOW = 0;
        private final static int VIEW_TYPE_REFRESH = 1;

        private ArrayList<UserFollow> mUserFollows = new ArrayList<>();
        private boolean mShowProgress;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case VIEW_TYPE_USER_FOLLOW:
                    View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_user_follow, parent, false);
                    return new UserFollowViewHolder(itemView);
                case VIEW_TYPE_REFRESH:
                    itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_refresh, parent, false);
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
                    ((RefreshViewHolder) holder).showProgress(mShowProgress);
                    break;
            }
        }

        @Override
        public int getItemViewType(int position) {
            return position == mUserFollows.size() ? VIEW_TYPE_REFRESH : VIEW_TYPE_USER_FOLLOW;
        }

        @Override
        public int getItemCount() {
            return mUserFollows.size() + (mShowProgress ? 1 : 0);
        }

        public int getRealItemCount() {
            return mUserFollows.size();
        }

        public ArrayList<UserFollow> getUserFollows() {
            return mUserFollows;
        }

        public void setUserFollows(List<UserFollow> userFollows) {
            mUserFollows.clear();
            addUserFollows(userFollows);
        }

        public void addUserFollows(List<UserFollow> userFollows) {
            mUserFollows.addAll(userFollows);
            mCurrentlyLoaded.setText(String.format(getString(R.string.currently_loaded), mUserFollows.size()));
            mEmpty.setVisibility(mUserFollows.size() == 0 ? View.VISIBLE : View.GONE);
        }

        public void showProgress(boolean show) {
            mShowProgress = show;
            notifyDataSetChanged();
        }

        private UserFollow getItem(int position) {
            return mUserFollows.get(position);
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
            @BindView(R.id.pb_progress)
            protected ProgressBar mProgress;

            public RefreshViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            public void showProgress(boolean show) {
                ((ViewGroup) mProgress.getParent()).setVisibility(show ? View.VISIBLE : View.GONE);
            }
        }
    }

    private class ScrollListener extends OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (dy > 0) {
                int visibleItems = mLayoutManager.getChildCount();
                int totalItems = mAdapter.getRealItemCount();
                int pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();

                if (mSearch.isEnabled()) {
                    if (mUserFollowsResource.hasNext() && visibleItems + pastVisibleItems >= totalItems - visibleItems / 3) {
                        showProgress(true);
                        mUserFollowsResource.getNext(RetroTwitch.getInstance(), new ResponseListener<List<UserFollow>>() {
                            @Override
                            public void onSuccess(List<UserFollow> userFollows) {
                                mAdapter.addUserFollows(userFollows);
                                showProgress(false);
                                setInformation(mUserFollowsResource.getTotal(), mAdapter.getRealItemCount());
                            }

                            @Override
                            public void onError(Error error) {
                                showProgress(false);
                                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        }
    }
    //</editor-fold>
}
