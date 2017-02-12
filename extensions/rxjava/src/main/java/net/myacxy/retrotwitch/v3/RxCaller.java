package net.myacxy.retrotwitch.v3;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import net.myacxy.retrotwitch.utils.StringUtil;
import net.myacxy.retrotwitch.v3.api.RxTwitchV3Service;
import net.myacxy.retrotwitch.v3.models.Direction;
import net.myacxy.retrotwitch.v3.models.SortBy;
import net.myacxy.retrotwitch.v3.models.StreamType;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxCaller extends BaseCaller<RxTwitchV3Service>
{
    private static RxCaller INSTANCE = new RxCaller(HttpLoggingInterceptor.Level.NONE);

    //<editor-fold desc="Constructor">
    RxCaller(HttpLoggingInterceptor.Level level)
    {
        super(level);
    }
    //</editor-fold>

    public static RxCaller getInstance()
    {
        return INSTANCE;
    }

    @Override
    Retrofit createRetrofit()
    {
        return new Retrofit.Builder()
                .baseUrl(net.myacxy.retrotwitch.v3.api.TwitchV3Service.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    @Override
    public RxTwitchV3Service createService(Retrofit retrofit)
    {
        return retrofit.create(RxTwitchV3Service.class);
    }

    //<editor-fold desc="User Follows">
    public Observable<net.myacxy.retrotwitch.v3.models.UserFollowsContainer> getUserFollows(
            final String user,
            final Integer limit,
            final Integer offset,
            final Direction direction,
            final net.myacxy.retrotwitch.v3.models.SortBy sortBy)
    {
        return getService().getUserFollows(user, limit, offset, direction, sortBy)
                .doOnError(Throwable::printStackTrace)
                .doOnNext(userFollowsContainer -> {
                    if (userFollowsContainer == null) return;
                    userFollowsContainer.user = user;
                    userFollowsContainer.limit = limit == null ? net.myacxy.retrotwitch.v3.api.TwitchV3Service.DEFAULT_LIMIT : limit;
                    userFollowsContainer.offset = offset == null ? 0 : offset;
                    userFollowsContainer.direction = direction == null ? Direction.DEFAULT : direction;
                    userFollowsContainer.sortBy = sortBy == null ? SortBy.DEFAULT : sortBy;
                });
    }
    //</editor-fold>

    //<editor-fold desc="Stream(s)">
    public Observable<net.myacxy.retrotwitch.v3.models.StreamContainer> getStream(String channel)
    {
        return service.getStream(channel);
    }

    public Observable<net.myacxy.retrotwitch.v3.models.StreamsContainer> getStreams(
            final String game,
            final List<net.myacxy.retrotwitch.v3.models.Channel> channels,
            final Integer limit,
            final Integer offset,
            final String clientId,
            final StreamType streamType)
    {
        List<String> channelNames = null;
        if (channels != null)
        {
            channelNames = new ArrayList<>(channels.size());
            for (net.myacxy.retrotwitch.v3.models.Channel channel : channels)
            {
                channelNames.add(channel.name);
            }
        }

        return service.getStreams(
                game,
                channelNames != null ? StringUtil.joinStrings(channelNames, ",") : null,
                limit,
                offset,
                clientId,
                streamType)
                .doOnNext(streamsContainer -> {
                    if (streamsContainer == null) return;
                    streamsContainer.game = game;
                    streamsContainer.channels = channels;
                    streamsContainer.limit = limit == null ? net.myacxy.retrotwitch.v3.api.TwitchV3Service.DEFAULT_LIMIT : limit;
                    streamsContainer.offset = offset == null ? 0 : offset;
                    streamsContainer.clientId = clientId;
                    streamsContainer.streamType = streamType == null ? StreamType.DEFAULT : streamType;
                });
    }

    public Observable<net.myacxy.retrotwitch.v3.models.User> getUser(String user)
    {
        return getService().getUser(user);
    }
    //</editor-fold>
}
