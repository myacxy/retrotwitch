package net.myacxy.retrotwitch.sample.android.rxjava.views;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import net.myacxy.retrotwitch.sample.android.rxjava.FragmentFactory;
import net.myacxy.retrotwitch.sample.android.rxjava.R;
import net.myacxy.retrotwitch.sample.android.rxjava.FragmentFactory.Type;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    public static final String EXTRA_FRAGMENT = "extra.fragment";

    @Bind(R.id.dl_main_drawer)
    protected DrawerLayout mDrawer;
    @Bind(R.id.tb_main)
    protected Toolbar mToolbar;
    @Bind(R.id.nv_main)
    protected NavigationView mNavigation;

    private ActionBarDrawerToggle mDrawerToggle;

    private Type mType = Type.USER_FOLLOWS;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initToolbar();
        mDrawer.setScrimColor(Color.TRANSPARENT);
        mNavigation.setNavigationItemSelectedListener(this);

        Bundle extras = getIntent().getExtras();
        if(savedInstanceState != null) {
            mType = (Type) savedInstanceState.getSerializable(EXTRA_FRAGMENT);
        } else if(extras != null) {
            mType = (Type) extras.getSerializable(EXTRA_FRAGMENT);
        } else {
            mType = Type.USER_FOLLOWS;
        }
        changeFragment(mType, true);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState)
    {
        super.onPostCreate(savedInstanceState, persistentState);
        mDrawerToggle.syncState();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putSerializable(EXTRA_FRAGMENT, mType);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void changeFragment(Type type, boolean tryReusingOldFragment)
    {
        Fragment fragment = FragmentFactory.getFragment(this, mType = type, tryReusingOldFragment);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_main_container, fragment, type.getTag())
                .commit();
    }

    private void initToolbar()
    {
        setSupportActionBar(mToolbar);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.app_name, R.string.app_name);
        mDrawer.setDrawerListener(mDrawerToggle);
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_menu_black_24dp);
        drawable.setTint(ContextCompat.getColor(this, R.color.color15_100));
        mToolbar.setNavigationIcon(drawable);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (mDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.mi_nv_user_follows:
                changeFragment(Type.USER_FOLLOWS, true);
                mDrawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.mi_nv_authentication:
                changeFragment(Type.AUTHENTICATION, true);
                mDrawer.closeDrawer(GravityCompat.START);
                return true;
        }
        return false;
    }
}
