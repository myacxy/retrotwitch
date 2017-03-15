package net.myacxy.retrotwitch.sample.android;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import net.myacxy.retrotwitch.v5.RetroTwitch;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.logging.HttpLoggingInterceptor;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.dl_main_drawer)
    protected DrawerLayout mDrawer;
    @BindView(R.id.tb_main)
    protected Toolbar mToolbar;

    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RetroTwitch.getInstance()
                .configure(new net.myacxy.retrotwitch.Configuration.ConfigurationBuilder()
                        .setClientId("75gzbgqhk0tg6dhjbqtsphmy8sdayrr")
                        .setLogLevel(HttpLoggingInterceptor.Level.BASIC)
                        .build()
                );

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mDrawer.setScrimColor(Color.TRANSPARENT);
        initToolbar();
        changeFragment();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void changeFragment() {

        String tag = UserFollowsFragment.class.getSimpleName();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            fragment = new UserFollowsFragment();
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_main_container, fragment, tag)
                .commit();
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.app_name, R.string.app_name);
        mDrawer.addDrawerListener(mDrawerToggle);
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_menu_black_24dp);
        drawable.setTint(ContextCompat.getColor(this, R.color.color15));
//        getSupportActionBar().setHomeButtonEnabled(true);
        mToolbar.setNavigationIcon(drawable);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
