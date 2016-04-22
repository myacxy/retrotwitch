package net.myacxy.retrotwitch.sample.android.rxjava.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.myacxy.retrotwitch.RxCaller;
import net.myacxy.retrotwitch.models.UserFollowsContainer;
import net.myacxy.retrotwitch.sample.android.rxjava.viewmodels.UserFollowsViewModel;
import net.myacxy.retrotwitch.sample.android.rxjava.databinding.FragmentUserFollowsBinding;

public class UserFollowsFragment extends Fragment
{
    protected RecyclerView mSearchResults;

    private UserFollowsAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private FragmentUserFollowsBinding mBinding;
    private UserFollowsViewModel mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mViewModel = new UserFollowsViewModel(getContext());
        mBinding = FragmentUserFollowsBinding.inflate(inflater, container, false);
        mBinding.setViewModel(mViewModel);
        mSearchResults = mBinding.rvUfResults;
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        mSearchResults.setLayoutManager(mLayoutManager = new LinearLayoutManager(getContext()));
        mSearchResults.setAdapter(mAdapter = new UserFollowsAdapter(mViewModel));

        mSearchResults.addOnScrollListener(new ScrollListener());

        if (savedInstanceState != null)
        {
//            ArrayList<UserFollow> userFollows = (ArrayList<UserFollow>) savedInstanceState.getSerializable("test");
//            mAdapter.setUserFollows(userFollows);
//            mUserFollowsResource = (UserFollowsResource) savedInstanceState.getSerializable("test2");
//            setInformation(mUserFollowsResource.getTotal(), userFollows.size());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
//        outState.putSerializable("test", mAdapter.getUserFollows());
//        outState.putSerializable("test2", mUserFollowsResource);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mViewModel.reset();
    }

    //<editor-fold desc="Inner Classes">


    private class ScrollListener extends OnScrollListener
    {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy)
        {
            if (dy > 0)
            {
                int visibleItems = mLayoutManager.getChildCount();
                int totalItems = mAdapter.getRealItemCount();
                int pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();

                if (!mViewModel.loading.get())
                {
                    if (visibleItems + pastVisibleItems >= totalItems - visibleItems / 3)
                    {
                        mViewModel.loadNext();
                    }
                }
            }
        }
    }
    //</editor-fold>
}
