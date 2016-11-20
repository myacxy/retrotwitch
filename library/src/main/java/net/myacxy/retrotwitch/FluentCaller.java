package net.myacxy.retrotwitch;

import net.myacxy.retrotwitch.resources.StreamResource;
import net.myacxy.retrotwitch.resources.UserFollowsResource;

public class FluentCaller
{
    private static FluentCaller INSTANCE = new FluentCaller();

    FluentCaller()
    {
    }

    public static FluentCaller getInstance()
    {
        return INSTANCE;
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
