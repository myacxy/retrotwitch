package net.myacxy.retrotwitch.sample.android.rxjava.views;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import net.myacxy.retrotwitch.sample.android.rxjava.FragmentFactory;
import net.myacxy.retrotwitch.sample.android.rxjava.FragmentFactory.FragmentType;
import net.myacxy.retrotwitch.sample.android.rxjava.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String EXTRA_FRAGMENT = "extra.fragment";

    @BindView(R.id.dl_main_drawer)
    protected DrawerLayout mDrawer;
    @BindView(R.id.tb_main)
    protected Toolbar mToolbar;
    @BindView(R.id.nv_main)
    protected NavigationView mNavigation;

    private ActionBarDrawerToggle mDrawerToggle;

    private FragmentFactory.FragmentType mFragmentType = FragmentType.USER_FOLLOWS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initToolbar();
        mDrawer.setScrimColor(Color.TRANSPARENT);
        mNavigation.setNavigationItemSelectedListener(this);

        Bundle extras = getIntent().getExtras();
        if (savedInstanceState != null) {
            mFragmentType = (FragmentType) savedInstanceState.getSerializable(EXTRA_FRAGMENT);
        } else if (extras != null) {
            mFragmentType = (FragmentFactory.FragmentType) extras.getSerializable(EXTRA_FRAGMENT);
        } else {
            mFragmentType = FragmentFactory.FragmentType.USER_FOLLOWS;
        }
        changeFragment(mFragmentType, true);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        mDrawerToggle.syncState();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(EXTRA_FRAGMENT, mFragmentType);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void changeFragment(FragmentType fragmentType, boolean tryReusingOldFragment) {
        Fragment fragment = FragmentFactory.getFragment(this, mFragmentType = fragmentType, tryReusingOldFragment);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_main_container, fragment, fragmentType.getTag())
                .commit();
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.app_name, R.string.app_name);
        mDrawer.addDrawerListener(mDrawerToggle);
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_menu_black_24dp);
        if (drawable != null) {
            int color15_100 = ContextCompat.getColor(this, R.color.color15_100);
            drawable.mutate().setTint(color15_100);
            mToolbar.setNavigationIcon(drawable);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mi_nv_user_follows:
                changeFragment(FragmentType.USER_FOLLOWS, true);
                mDrawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.mi_nv_authentication:
                changeFragment(FragmentType.AUTHENTICATION, true);
                mDrawer.closeDrawer(GravityCompat.START);
                return true;
        }
        return false;
    }
}
