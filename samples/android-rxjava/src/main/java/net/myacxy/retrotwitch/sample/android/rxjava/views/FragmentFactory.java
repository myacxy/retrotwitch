package net.myacxy.retrotwitch.sample.android.rxjava.views;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public class FragmentFactory
{
    private FragmentFactory()
    {
        throw new IllegalAccessError();
    }

    public static Fragment getFragment(AppCompatActivity activity, Type type)
    {
        Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag(type.getTag());
        if (fragment != null)
        {
            return fragment;
        }

        switch (type)
        {
            case USER_FOLLOWS:
                return new UserFollowsFragment();
            case AUTHENTICATION:
                return new AuthenticationFragment();
            default:
                throw new IllegalArgumentException();
        }
    }

    enum Type
    {
        USER_FOLLOWS(UserFollowsFragment.class.getSimpleName()),
        AUTHENTICATION(AuthenticationFragment.class.getSimpleName());

        private String tag;

        Type(String tag)
        {
            this.tag = tag;
        }

        public String getTag()
        {
            return tag;
        }
    }
}
