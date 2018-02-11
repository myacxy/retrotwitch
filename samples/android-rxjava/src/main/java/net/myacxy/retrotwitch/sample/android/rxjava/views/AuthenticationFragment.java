package net.myacxy.retrotwitch.sample.android.rxjava.views;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import net.myacxy.retrotwitch.sample.android.rxjava.AppApplication;
import net.myacxy.retrotwitch.sample.android.rxjava.R;
import net.myacxy.retrotwitch.sample.android.rxjava.databinding.FragmentAuthenticationBinding;
import net.myacxy.retrotwitch.sample.android.rxjava.viewmodels.AuthenticationViewModel;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AuthenticationFragment extends Fragment {

    private FragmentAuthenticationBinding binding;
    private AuthenticationViewModel authenticationViewModel;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAuthenticationBinding.inflate(inflater, container, false);
        authenticationViewModel = AppApplication.getViewModelLocator().getAuthentication();
        binding.setViewModel(authenticationViewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_a_authenticate)
    protected void onAuthenticateClicked() {
        String url = authenticationViewModel.createAuthenticationUrl();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Logger.d(e.toString());
        }
    }
}
