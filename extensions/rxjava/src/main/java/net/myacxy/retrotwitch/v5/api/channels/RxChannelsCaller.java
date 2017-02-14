package net.myacxy.retrotwitch.v5.api.channels;

import net.myacxy.retrotwitch.v5.api.BaseCaller;
import net.myacxy.retrotwitch.v5.api.ResponseListener;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

// TODO: 14.02.2017
public class RxChannelsCaller extends BaseCaller<RxTwitchChannelsService> {

    public RxChannelsCaller(OkHttpClient client) {
        super(client);
    }

    @Override
    protected RxTwitchChannelsService createService(Retrofit retrofit) {
        return retrofit.create(RxTwitchChannelsService.class);
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
    public Observable<PrivilegedChannel> getChannel(final ResponseListener<PrivilegedChannel> listener) {
        Observable<PrivilegedChannel> observable = getService().getChannel();
//        observable.enqueue(new SimpleRetroTwitchCallback<>(listener));
        return observable;
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
    public Observable<SimpleChannel> getChannelById(long channelId, final ResponseListener<SimpleChannel> listener) {
        Observable<SimpleChannel> observable = getService().getChannelById(channelId);
//        observable.enqueue(new SimpleRetroTwitchCallback<>(listener));
        return observable;
    }
    //</editor-fold>
}
