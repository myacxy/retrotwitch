package net.myacxy.retrotwitch.v5;

import net.myacxy.retrotwitch.BaseRetroTwitch;
import net.myacxy.retrotwitch.v5.api.channels.ChannelsCaller;
import net.myacxy.retrotwitch.v5.api.streams.StreamsCaller;
import net.myacxy.retrotwitch.v5.api.users.UserFollowsResource;
import net.myacxy.retrotwitch.v5.api.users.UsersCaller;

public class RetroTwitch extends BaseRetroTwitch<RetroTwitch> {

    private static RetroTwitch INSTANCE = new RetroTwitch();

    //<editor-fold desc="Member">
    private ChannelsCaller channels;
    private UsersCaller users;
    private StreamsCaller streams;
    //</editor-fold>

    protected RetroTwitch() {
        super();

        channels = new ChannelsCaller(client);
        users = new UsersCaller(client);
        streams = new StreamsCaller(client);
    }

    public static RetroTwitch getInstance() {
        return INSTANCE;
    }

    //<editor-fold desc="Public Methods">
    @Override
    public ChannelsCaller channels() {
        return channels;
    }

    @Override
    public UsersCaller users() {
        return users;
    }

    @Override
    public StreamsCaller streams() {
        return streams;
    }

    public UserFollowsResource.Builder userFollows(long userId) {
        return new UserFollowsResource.Builder(userId);
    }
    //</editor-fold>
}
