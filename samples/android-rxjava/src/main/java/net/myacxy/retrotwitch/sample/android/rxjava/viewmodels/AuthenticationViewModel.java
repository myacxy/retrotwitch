package net.myacxy.retrotwitch.sample.android.rxjava.viewmodels;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.orhanobut.logger.Logger;

import net.myacxy.retrotwitch.RetroTwitch;
import net.myacxy.retrotwitch.api.Scope;

public class AuthenticationViewModel
{
    private Context mContext;

    public AuthenticationViewModel(Context context)
    {
        mContext = context;
    }

    public void onAuthenticateClicked(View view)
    {
        String url = RetroTwitch.getInstance()
                .authenticate()
                .setClientId("75gzbgqhk0tg6dhjbqtsphmy8sdayrr")
                .setRedirectUri("http://localhost/retrotwitchtest")
                .addScopes(Scope.USER_READ)
                .addScopes()
                .buildUrl();

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        try
        {
            mContext.startActivity(intent);
        } catch (ActivityNotFoundException e)
        {
            Logger.d(e.toString());
        }
    }
}
