package net.myacxy.retrotwitch.v3;

import net.myacxy.retrotwitch.v3.resources.StreamResource;
import net.myacxy.retrotwitch.v3.resources.UserFollowsResource;

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
