package net.myacxy.retrotwitch.sample.android.rxjava.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.orhanobut.logger.Logger;

public class WebStartActivity extends AppCompatActivity
{
    public static final String EXTRA_ACCESS_TOKEN = "extra.access.token";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        String dataString = getIntent().getDataString();
        dataString = dataString.replace('#', '?');
        Uri uri = Uri.parse(dataString);
        String accessToken = uri.getQueryParameter("access_token");
        Logger.d(accessToken);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_ACCESS_TOKEN, accessToken);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
