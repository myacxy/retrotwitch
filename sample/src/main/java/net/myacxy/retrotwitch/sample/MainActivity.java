package net.myacxy.retrotwitch.sample;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
{

    @Bind(R.id.tb_main)
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        changeFragment();
        setSupportActionBar(mToolbar);
    }

    private void changeFragment() {

        String tag = UserFollowsFragment.class.getSimpleName();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if(fragment == null) {
            fragment = new UserFollowsFragment();
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_main_container, fragment, tag)
                .commit();
    }
}
