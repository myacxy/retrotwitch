package net.myacxy.retrotwitch;

import net.myacxy.retrotwitch.resources.StreamResource;
import net.myacxy.retrotwitch.resources.UserFollowsResource;

public class FluentCaller
{
    private static FluentCaller sInstance = new FluentCaller();

    private FluentCaller()
    {

    }

    public static FluentCaller getInstance()
    {
        return sInstance;
    }

    public UserFollowsResource.Builder userFollows(String user)
    {
        return new UserFollowsResource.Builder(user);
    }

    public StreamResource.Builder stream(String channel)
    {
        return new StreamResource.Builder(channel);
    }
}
