package net.myacxy.retrotwitch.v5;

import net.myacxy.retrotwitch.BaseRetroTwitch;
import net.myacxy.retrotwitch.v5.api.channels.RxChannelsCaller;
import net.myacxy.retrotwitch.v5.api.games.games.RxGamesCaller;
import net.myacxy.retrotwitch.v5.api.streams.RxStreamsCaller;
import net.myacxy.retrotwitch.v5.api.users.RxUsersCaller;

public class RxRetroTwitch extends BaseRetroTwitch<RxRetroTwitch> {

    private static RxRetroTwitch INSTANCE = new RxRetroTwitch();

    //<editor-fold desc="Member">
    private RxChannelsCaller channels;
    private RxGamesCaller games;
    private RxStreamsCaller streams;
    private RxUsersCaller users;
    //</editor-fold>

    //<editor-fold desc="Constructor">
    public RxRetroTwitch() {
        super();

        channels = new RxChannelsCaller(client);
        games = new RxGamesCaller(client);
        streams = new RxStreamsCaller(client);
        users = new RxUsersCaller(client);
    }
    //</editor-fold>

    public static RxRetroTwitch getInstance() {
        return INSTANCE;
    }

    //<editor-fold desc="Public Methods">
    @Override
    public RxChannelsCaller channels() {
        return channels;
    }

    @Override
    public RxGamesCaller games() {
        return games;
    }

    @Override
    public RxStreamsCaller streams() {
        return streams;
    }

    @Override
    public RxUsersCaller users() {
        return users;
    }
    //</editor-fold>
}
