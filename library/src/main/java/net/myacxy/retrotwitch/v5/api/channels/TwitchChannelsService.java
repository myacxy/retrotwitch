package net.myacxy.retrotwitch.v5.api.channels;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TwitchChannelsService {
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
    @GET("channels")
    Call<PrivilegedChannel> getChannel();

    /**
     * <p>Gets a specified channel object.</p>
     * <p>
     * <p><b>Authentication</b></p>
     * <p>None.</p>
     * <p>
     * <p><b>Optional Query String Parameters</b></p>
     * <p>None</p>
     */
    @GET("channels/{channel_id}")
    Call<SimpleChannel> getChannelById(@Path("channel_id") long channelId);
}
