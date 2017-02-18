package net.myacxy.retrotwitch.v5;

import net.myacxy.retrotwitch.BaseRetroTwitch;
import net.myacxy.retrotwitch.v5.api.channels.RxChannelsCaller;
import net.myacxy.retrotwitch.v5.api.streams.RxStreamsCaller;
import net.myacxy.retrotwitch.v5.api.users.RxUsersCaller;

public class RxRetroTwitch extends BaseRetroTwitch<RxRetroTwitch> {

    private static RxRetroTwitch INSTANCE = new RxRetroTwitch();

    //<editor-fold desc="Member">
    private RxChannelsCaller channels;
    private RxUsersCaller users;
    private RxStreamsCaller streams;
    //</editor-fold>

    //<editor-fold desc="Constructor">
    public RxRetroTwitch() {
        super();

        channels = new RxChannelsCaller(client);
        users = new RxUsersCaller(client);
        streams = new RxStreamsCaller(client);
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
    public RxUsersCaller users() {
        return users;
    }

    @Override
    public RxStreamsCaller streams() {
        return streams;
    }
    //</editor-fold>
}
