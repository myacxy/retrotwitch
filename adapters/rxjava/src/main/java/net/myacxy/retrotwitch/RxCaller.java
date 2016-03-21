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
    public RxTwitchV3Service createService(Retrofit retrofit) {
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
        Observable<UserFollowsContainer> observable = getService().getUserFollows(user, limit, offset, direction, sortBy)
                .doOnNext(userFollowsContainer -> {
                    userFollowsContainer.user = user;
                    userFollowsContainer.limit = limit == null ? net.myacxy.retrotwitch.api.TwitchV3Service.DEFAULT_LIMIT : limit;
                    userFollowsContainer.offset = offset == null ? 0 : offset;
                    userFollowsContainer.direction = direction == null ? Direction.DEFAULT : direction;
                    userFollowsContainer.sortBy = sortBy == null ? SortBy.DEFAULT : sortBy;
            });
        return observable;
    }

    public Observable<UserFollowsContainer> getAllUserFollows(
            String user,
            Direction direction,
            SortBy sortBy)
    {
        return getUserFollows(user, TwitchV3Service.MAX_LIMIT, 0, direction, sortBy)
                .concatMap(userFollowsContainer -> {
                    int pages = (int) (userFollowsContainer.total / (float) userFollowsContainer.limit);
                    if(userFollowsContainer.total > userFollowsContainer.offset + userFollowsContainer.limit) {
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

        Observable<StreamsContainer> observable = mService.getStreams(
                game,
                channelNames != null ? StringUtil.joinStrings(channelNames, ",") : null,
                limit,
                offset,
                clientId,
                streamType).doOnNext(streamsContainer -> {
                    streamsContainer.game = game;
                    streamsContainer.channels = channels;
                    streamsContainer.limit = limit == null ? net.myacxy.retrotwitch.api.TwitchV3Service.DEFAULT_LIMIT : limit;
                    streamsContainer.offset = offset == null ? 0 : offset;
                    streamsContainer.clientId = clientId;
                    streamsContainer.streamType = streamType == null ? StreamType.DEFAULT : streamType;
            });
        return observable;
    }

    public Observable<StreamsContainer> getAllStreams(
            String game,
            List<Channel> channels,
            String clientId,
            StreamType streamType,
            int maximum)
    {
        if (maximum > TwitchV3Service.MAX_LIMIT)
        {
            return getAllStreamsRecursively(game, channels, clientId, streamType, maximum, 0);
        } else
        {
            return getStreams(game, channels, maximum, 0, clientId, streamType);
        }
    }


    private Observable<StreamsContainer> getAllStreamsRecursively(String game, List<Channel> channels, String clientId, StreamType streamType, int maximum, int currentPage)
    {
        return getStreams(game, channels, TwitchV3Service.MAX_LIMIT, currentPage * TwitchV3Service.MAX_LIMIT, clientId, streamType).concatMap(streamsContainer -> {
            int pages = 0;
            if (maximum < streamsContainer.total)
            {
                pages = (int) (maximum / ((float) streamsContainer.limit) + 0.5f);
            } else
            {
                pages = (int) (streamsContainer.total / ((float) streamsContainer.limit) + 0.5f);
            }
            int nextPage = (streamsContainer.offset + streamsContainer.limit) / streamsContainer.limit;
            if (nextPage >= pages)
            {
                return Observable.just(streamsContainer);
            }

            return Observable.just(streamsContainer).concatWith(getAllStreamsRecursively(game, channels, clientId, streamType, maximum, nextPage));
        });
    }

    //</editor-fold>
}
