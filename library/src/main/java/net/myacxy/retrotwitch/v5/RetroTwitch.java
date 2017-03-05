package net.myacxy.retrotwitch.v5;

import net.myacxy.retrotwitch.BaseRetroTwitch;
import net.myacxy.retrotwitch.v5.api.channels.ChannelsCaller;
import net.myacxy.retrotwitch.v5.api.games.GamesCaller;
import net.myacxy.retrotwitch.v5.api.search.SearchCaller;
import net.myacxy.retrotwitch.v5.api.streams.StreamsCaller;
import net.myacxy.retrotwitch.v5.api.users.UserFollowsResource;
import net.myacxy.retrotwitch.v5.api.users.UsersCaller;

public class RetroTwitch extends BaseRetroTwitch<RetroTwitch> {

    private static RetroTwitch INSTANCE = new RetroTwitch();

    //<editor-fold desc="Member">
    private ChannelsCaller channels;
    private GamesCaller games;
    private SearchCaller search;
    private StreamsCaller streams;
    private UsersCaller users;
    //</editor-fold>

    protected RetroTwitch() {
        super();

        channels = new ChannelsCaller(client);
        games = new GamesCaller(client);
        search = new SearchCaller(client);
        streams = new StreamsCaller(client);
        users = new UsersCaller(client);
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
    public GamesCaller games() {
        return games;
    }

    @Override
    public SearchCaller search() {
        return search;
    }

    @Override
    public StreamsCaller streams() {
        return streams;
    }

    @Override
    public UsersCaller users() {
        return users;
    }

    public UserFollowsResource.Builder userFollows(long userId) {
        return new UserFollowsResource.Builder(userId);
    }
    //</editor-fold>
}
