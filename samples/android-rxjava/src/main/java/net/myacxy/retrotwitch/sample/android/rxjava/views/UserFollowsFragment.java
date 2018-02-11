package net.myacxy.retrotwitch.sample.android.rxjava.views;

import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.myacxy.retrotwitch.sample.android.rxjava.AppApplication;
import net.myacxy.retrotwitch.sample.android.rxjava.R;
import net.myacxy.retrotwitch.sample.android.rxjava.databinding.FragmentUserFollowsBinding;
import net.myacxy.retrotwitch.sample.android.rxjava.viewmodels.UserFollowsViewModel;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class UserFollowsFragment extends Fragment {

    protected RecyclerView mSearchResults;

    private UserFollowsAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private FragmentUserFollowsBinding mBinding;
    private UserFollowsViewModel mViewModel;
    private Unbinder unbinder;

    private Observable.OnPropertyChangedCallback mErrorCallback = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable observable, int i) {
            String errorMessage = mViewModel.errorMessage.get();
            if (!TextUtils.isEmpty(errorMessage)) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewModel = AppApplication.getViewModelLocator().getUserFollows();
        mBinding = FragmentUserFollowsBinding.inflate(inflater, container, false);
        mBinding.setViewModel(mViewModel);
        mSearchResults = mBinding.rvUfResults;
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        mSearchResults.setLayoutManager(mLayoutManager = new LinearLayoutManager(getContext()));
        mSearchResults.setAdapter(mAdapter = new UserFollowsAdapter(mViewModel));

        mSearchResults.addOnScrollListener(new ScrollListener());

        mViewModel.errorMessage.addOnPropertyChangedCallback(mErrorCallback);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mViewModel.errorMessage.removeOnPropertyChangedCallback(mErrorCallback);
        mViewModel.reset();
    }

    @OnClick(R.id.btn_uf_search)
    protected void onSearchClicked() {
        mViewModel.search();
    }

    //<editor-fold desc="Inner Classes">
    private class ScrollListener extends OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (dy > 0) {
                int visibleItems = mLayoutManager.getChildCount();
                int totalItems = mAdapter.getRealItemCount();
                int pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();

                if (!mViewModel.loading.get()) {
                    if (visibleItems + pastVisibleItems >= totalItems - visibleItems / 3) {
                        mViewModel.loadNext();
                    }
                }
            }
        }
    }
    //</editor-fold>
}
