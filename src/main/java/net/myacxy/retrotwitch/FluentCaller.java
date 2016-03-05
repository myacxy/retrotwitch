package net.myacxy.retrotwitch;

import net.myacxy.retrotwitch.resources.StreamResource;
import net.myacxy.retrotwitch.resources.UserFollowsResource;
import okhttp3.logging.HttpLoggingInterceptor;

public enum FluentCaller
{
    INSTANCE;

    public UserFollowsResource.Builder userFollows(String user)
    {
        return new UserFollowsResource.Builder(user);
    }

    public StreamResource.Builder stream(String channel)
    {
        return new StreamResource.Builder(channel);
    }
}
