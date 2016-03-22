package net.myacxy.retrotwitch;

import net.myacxy.retrotwitch.api.Direction;
import net.myacxy.retrotwitch.api.RxTwitchV3Service;
import net.myacxy.retrotwitch.api.TwitchV3Service;
import net.myacxy.retrotwitch.api.SortBy;
import net.myacxy.retrotwitch.api.StreamType;
import net.myacxy.retrotwitch.models.Channel;
import net.myacxy.retrotwitch.models.StreamContainer;
import net.myacxy.retrotwitch.models.StreamsContainer;
import net.myacxy.retrotwitch.models.UserFollowsContainer;
import net.myacxy.retrotwitch.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class RxCaller extends BaseCaller<RxTwitchV3Service>
{
    private static RxCaller sInstance = new RxCaller(HttpLoggingInterceptor.Level.NONE);

    //<editor-fold desc="Constructor">
    RxCaller(HttpLoggingInterceptor.Level level)
    {
        super(level);
    }
    //</editor-fold>

    public static RxCaller getInstance()
    {
        return sInstance;
    }

    @Override
    Retrofit createRetrofit()
    {
        return new Retrofit.Builder()
                .baseUrl(net.myacxy.retrotwitch.api.TwitchV3Service.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(mClient)
                .build();
    }

    @Override
    public RxTwitchV3Service createService(Retrofit retrofit)
    {
        return retrofit.create(RxTwitchV3Service.class);
    }

    //<editor-fold desc="User Follows">
    public Observable<UserFollowsContainer> getUserFollows(
            final String user,
            final Integer limit,
            final Integer offset,
            final Direction direction,
            final SortBy sortBy)
    {
        return getService().getUserFollows(user, limit, offset, direction, sortBy)
                .doOnNext(userFollowsContainer -> {
                    if (userFollowsContainer == null) return;
                    userFollowsContainer.user = user;
                    userFollowsContainer.limit = limit == null ? net.myacxy.retrotwitch.api.TwitchV3Service.DEFAULT_LIMIT : limit;
                    userFollowsContainer.offset = offset == null ? 0 : offset;
                    userFollowsContainer.direction = direction == null ? Direction.DEFAULT : direction;
                    userFollowsContainer.sortBy = sortBy == null ? SortBy.DEFAULT : sortBy;
                });
    }

    public Observable<UserFollowsContainer> getAllUserFollows(
            String user,
            Direction direction,
            SortBy sortBy)
    {
        return getUserFollows(user, TwitchV3Service.MAX_LIMIT, 0, direction, sortBy)
                .concatMap(userFollowsContainer -> {
                    if (userFollowsContainer == null) return Observable.empty();
                    int pages = (int) (userFollowsContainer.total / (float) userFollowsContainer.limit);
                    if (userFollowsContainer.total > userFollowsContainer.offset + userFollowsContainer.limit)
                    {
                        return Observable.just(userFollowsContainer)
                                .concatWith(Observable.range(1, pages)
                                        .concatMap(page -> getUserFollows(user, userFollowsContainer.limit, page * userFollowsContainer.limit, direction, sortBy)));
                    }
                    return Observable.just(userFollowsContainer);
                });
    }
    //</editor-fold>

    //<editor-fold desc="Stream(s)">
    public Observable<StreamContainer> getStream(String channel)
    {
        return mService.getStream(channel);
    }

    public Observable<StreamsContainer> getStreams(
            final String game,
            final List<Channel> channels,
            final Integer limit,
            final Integer offset,
            final String clientId,
            final StreamType streamType)
    {
        List<String> channelNames = null;
        if (channels != null)
        {
            channelNames = new ArrayList<>(channels.size());
            for (Channel channel : channels)
            {
                channelNames.add(channel.name);
            }
        }

        return mService.getStreams(
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
                    streamsContainer.limit = limit == null ? net.myacxy.retrotwitch.api.TwitchV3Service.DEFAULT_LIMIT : limit;
                    streamsContainer.offset = offset == null ? 0 : offset;
                    streamsContainer.clientId = clientId;
                    streamsContainer.streamType = streamType == null ? StreamType.DEFAULT : streamType;
                });
    }

    public Observable<StreamsContainer> getAllStreams(
            String game,
            List<Channel> channels,
            String clientId,
            StreamType streamType,
            final int maximum)
    {

        return getStreams(
                game,
                channels,
                maximum > TwitchV3Service.MAX_LIMIT ? TwitchV3Service.MAX_LIMIT : maximum,
                0,
                clientId,
                streamType)
                .concatMap(initialContainer ->
                {
                    // first request
                    if (initialContainer == null) return Observable.empty();
                    else if (initialContainer.total > initialContainer.streams.size())
                    {
                        int additionalPages = Math.round(maximum / TwitchV3Service.MAX_LIMIT + 0.5f) - 1;

                        return Observable.just(initialContainer)
                                .concatWith(Observable.range(1, additionalPages)
                                        .concatMap(page ->
                                        {
                                            int limit = initialContainer.limit;
                                            if(page == additionalPages) {
                                                limit = maximum - page * initialContainer.limit;
                                            }
                                            return getStreams(game, channels, limit, page * initialContainer.limit, clientId, streamType);
                                        }));
                    }
                    return Observable.just(initialContainer);
                });
    }
    //</editor-fold>
}
