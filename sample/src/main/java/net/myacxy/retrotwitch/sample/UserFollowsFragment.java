package net.myacxy.retrotwitch.sample;

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

import net.myacxy.retrotwitch.Caller;
import net.myacxy.retrotwitch.RetroTwitch;
import net.myacxy.retrotwitch.models.UserFollow;
import net.myacxy.retrotwitch.resources.UserFollowsResource;
import net.myacxy.retrotwitch.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserFollowsFragment extends Fragment
{
    @Bind(R.id.et_uf_user_name)
    protected EditText mUserName;
    @Bind(R.id.btn_uf_search)
    protected Button mSearch;
    @Bind(R.id.tv_uf_total)
    protected TextView mTotal;
    @Bind(R.id.tv_uf_currently_loaded)
    protected TextView mCurrentlyLoaded;

    @Bind(R.id.rv_uf_results)
    protected RecyclerView mSearchResults;
    @Bind(R.id.pb_uf_progress)
    protected ProgressBar mProgress;

    private UserFollowAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private UserFollowsResource mUserFollowsResource;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_user_follows, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mAdapter = new UserFollowAdapter();
        mLayoutManager = new LinearLayoutManager(getContext());
        mSearchResults.setLayoutManager(mLayoutManager);
        mSearchResults.setAdapter(mAdapter);

        mSearchResults.addOnScrollListener(new ScrollListener());
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn_uf_search)
    public void onSearchClicked()
    {
        String userName = mUserName.getText().toString().trim();
        if (!StringUtil.isBlank(userName))
        {
            showProgress(true);
            mUserFollowsResource = RetroTwitch.INSTANCE.getCaller()
                    .userFollows(userName)
                    .limited()
                    .build();

            mUserFollowsResource.enqueue(new Caller.ResponseListener<List<UserFollow>>()
            {
                @Override
                public void onSuccess(List<UserFollow> userFollows)
                {
                    showProgress(false);
                    mAdapter.setUserFollows(userFollows);
                }

                @Override
                public void onError()
                {
                    // has no next
                    showProgress(false);
                }
            });
        } else
        {
            Toast.makeText(getContext(), "user name must not be empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void showProgress(boolean show)
    {
        mSearch.setEnabled(!show);
        mProgress.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    //<editor-fold desc="Inner Classes">
    protected class UserFollowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    {
        private List<UserFollow> mUserFollows = new ArrayList<>();

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_user_follow, parent, false);
            return new UserFollowViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
        {
            if (holder instanceof UserFollowViewHolder)
            {
                ((UserFollowViewHolder) holder).bind(mUserFollows.get(position));
            }
        }

        @Override
        public int getItemCount()
        {
            return mUserFollows.size();
        }

        protected class UserFollowViewHolder extends RecyclerView.ViewHolder
        {
            @Bind(R.id.tv_iuf_channel_name)
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

        public void setUserFollows(List<UserFollow> userFollows)
        {
            mUserFollows.clear();
            addUserFollows(userFollows);
        }

        public void addUserFollows(List<UserFollow> userFollows)
        {
            mUserFollows.addAll(userFollows);
            mCurrentlyLoaded.setText("Currently loaded: " + mUserFollows.size());
        }
    }

    private class ScrollListener extends OnScrollListener
    {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy)
        {
            if (dy > 0)
            {
                int visibleItems = mLayoutManager.getChildCount();
                int totalItems = mLayoutManager.getItemCount();
                int pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();

                if (mProgress.getVisibility() == View.GONE)
                {
                    if (visibleItems + pastVisibleItems >= totalItems)
                    {
                        showProgress(true);
                        mUserFollowsResource.getNext(new Caller.ResponseListener<List<UserFollow>>()
                        {
                            @Override
                            public void onSuccess(List<UserFollow> userFollows)
                            {
                                mAdapter.addUserFollows(userFollows);
                                showProgress(false);
                            }

                            @Override
                            public void onError()
                            {
                                showProgress(false);
                                Toast.makeText(getContext(), "request error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        }
    }
    //</editor-fold>
}
