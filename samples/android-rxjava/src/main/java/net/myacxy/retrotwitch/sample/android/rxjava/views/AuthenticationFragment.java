package net.myacxy.retrotwitch.sample.android.rxjava.views;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.myacxy.retrotwitch.sample.android.rxjava.R;
import net.myacxy.retrotwitch.sample.android.rxjava.databinding.FragmentAuthenticationBinding;
import net.myacxy.retrotwitch.sample.android.rxjava.viewmodels.AuthenticationViewModel;

public class AuthenticationFragment extends Fragment
{
    private FragmentAuthenticationBinding mBinding;
    private AuthenticationViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mViewModel = new AuthenticationViewModel(getContext());
        FragmentAuthenticationBinding mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_authentication, container, false);
        mBinding.setViewModel(mViewModel);
        return mBinding.getRoot();
    }
}
