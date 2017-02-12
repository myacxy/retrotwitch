package net.myacxy.retrotwitch.v5.api.channels;

import net.myacxy.retrotwitch.v5.api.BaseCaller;
import net.myacxy.retrotwitch.v5.api.ResponseListener;
import net.myacxy.retrotwitch.v5.api.SimpleRetroTwitchCallback;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;

public class ChannelsCaller extends BaseCaller<TwitchChannelsService> {

    public ChannelsCaller(OkHttpClient client) {
        super(client);
    }

    @Override
    public TwitchChannelsService createService(Retrofit retrofit) {
        return retrofit.create(TwitchChannelsService.class);
    }

    //<editor-fold desc="API Calls">

    /**
     * <p>Gets a channel object based on a specified OAuth token.</p>
     * <p>
     * <p><b>Authentication</b></p>
     * <p>Required scope: channel_read</p>
     * <p>
     * <p><b>Optional Query String Parameters</b></p>
     * <p>None</p>
     *
     * @return Get Channel returns more data than Get Channel by ID because Get Channel is privileged.
     */
    public Call<PrivilegedChannel> getChannel(final ResponseListener<PrivilegedChannel> listener) {
        Call<PrivilegedChannel> call = getService().getChannel();
        call.enqueue(new SimpleRetroTwitchCallback<>(listener));
        return call;
    }

    /**
     * <p>Gets a specified channel object.</p>
     * <p>
     * <p><b>Authentication</b></p>
     * <p>None.</p>
     * <p>
     * <p><b>Optional Query String Parameters</b></p>
     * <p>None</p>
     */
    public Call<SimpleChannel> getChannelById(long channelId, final ResponseListener<SimpleChannel> listener) {
        Call<SimpleChannel> call = getService().getChannelById(channelId);
        call.enqueue(new SimpleRetroTwitchCallback<>(listener));
        return call;
    }
    //</editor-fold>
}
