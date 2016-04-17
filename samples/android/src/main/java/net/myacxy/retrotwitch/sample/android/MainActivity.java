package net.myacxy.retrotwitch.sample.android;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import net.myacxy.retrotwitch.RetroTwitch;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.logging.HttpLoggingInterceptor;

public class MainActivity extends AppCompatActivity
{
    @Bind(R.id.dl_main_drawer)
    protected DrawerLayout mDrawer;
    @Bind(R.id.tb_main)
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        RetroTwitch.INSTANCE.configure().setLogLevel(HttpLoggingInterceptor.Level.BASIC).apply();

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initToolbar();
        changeFragment();
    }

    private void changeFragment()
    {

        String tag = UserFollowsFragment.class.getSimpleName();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null)
        {
            fragment = new UserFollowsFragment();
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_main_container, fragment, tag)
                .commit();
    }

    private void initToolbar()
    {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_menu_black_24dp);
        drawable.setTint(ContextCompat.getColor(this, R.color.color15));
        getSupportActionBar().setLogo(drawable);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == android.R.id.home) {
            mDrawer.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
