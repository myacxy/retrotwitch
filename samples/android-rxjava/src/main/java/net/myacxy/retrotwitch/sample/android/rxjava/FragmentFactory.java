package net.myacxy.retrotwitch.sample.android.rxjava;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.orhanobut.logger.Logger;

import net.myacxy.retrotwitch.sample.android.rxjava.views.AuthenticationFragment;
import net.myacxy.retrotwitch.sample.android.rxjava.views.UserFollowsFragment;

public class FragmentFactory
{
    private FragmentFactory()
    {
        throw new IllegalAccessError();
    }

    public static Fragment getFragment(AppCompatActivity activity, FragmentType fragmentType, boolean tryReusingOldFragment)
    {
        if (tryReusingOldFragment)
        {
            Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag(fragmentType.getTag());
            if (fragment != null)
            {
                Logger.t(0).i("reusing fragment. (%s)", fragmentType.getTag());
                return fragment;
            }
            Logger.t(0).i("could not reuse fragment. (%s)", fragmentType.getTag());
        }

        return fragmentType.newInstance();
    }

    public enum FragmentType
    {
        USER_FOLLOWS(UserFollowsFragment.class.getSimpleName())
                {
                    @Override
                    public Fragment newInstance()
                    {
                        return new UserFollowsFragment();
                    }
                },
        AUTHENTICATION(AuthenticationFragment.class.getSimpleName())
                {
                    @Override
                    public Fragment newInstance()
                    {
                        return new AuthenticationFragment();
                    }
                };

        private String tag;

        FragmentType(String tag)
        {
            this.tag = tag;
        }

        public abstract Fragment newInstance();

        public String getTag()
        {
            return tag;
        }
    }
}
