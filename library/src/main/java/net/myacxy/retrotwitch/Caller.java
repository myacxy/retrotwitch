package net.myacxy.retrotwitch;

import net.myacxy.retrotwitch.api.*;
import net.myacxy.retrotwitch.helpers.ErrorFactory;
import net.myacxy.retrotwitch.models.*;
import net.myacxy.retrotwitch.models.Error;
import net.myacxy.retrotwitch.utils.StringUtil;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Caller extends BaseCaller<TwitchV3Service>
{
    private static Caller sInstance = new Caller(HttpLoggingInterceptor.Level.NONE);

    //<editor-fold desc="Constructor">
    Caller(HttpLoggingInterceptor.Level level)
    {
        super(level);
    }
    //</editor-fold>

    public static Caller getInstance()
    {
        return sInstance;
    }

    @Override
    public TwitchV3Service createService(Retrofit retrofit)
    {
        return retrofit.create(TwitchV3Service.class);
    }

    //<editor-fold desc="User Follows">
    public Call<UserFollowsContainer> getUserFollows(
            final String user,
            final Integer limit,
            final Integer offset,
            final Direction direction,
            final SortBy sortBy,
            final ResponseListener<UserFollowsContainer> listener)
    {
        Call<UserFollowsContainer> call = getService().getUserFollows(user, limit, offset, direction, sortBy);

        call.enqueue(new Callback<UserFollowsContainer>()
        {
            @Override
            public void onResponse(Call<UserFollowsContainer> call, Response<UserFollowsContainer> response)
            {
                Error error = ErrorFactory.fromResponse(response);
                if (error == null)
                {
                    UserFollowsContainer userFollowsContainer = response.body();
                    userFollowsContainer.user = user;
                    userFollowsContainer.limit = limit == null ? TwitchV3Service.DEFAULT_LIMIT : limit;
                    userFollowsContainer.offset = offset == null ? 0 : offset;
                    userFollowsContainer.direction = direction == null ? Direction.DEFAULT : direction;
                    userFollowsContainer.sortBy = sortBy == null ? SortBy.DEFAULT : sortBy;
                    listener.onSuccess(userFollowsContainer);
                } else
                {
                    listener.onError(error);
                }
            }

            @Override
            public void onFailure(Call<UserFollowsContainer> call, Throwable t)
            {
                listener.onError(ErrorFactory.fromThrowable(t));
            }
        });
        return call;
    }

    public void getAllUserFollows(
            String user,
            Direction direction,
            SortBy sortBy,
            ResponseListener<List<UserFollow>> listener)
    {
        getAllUserFollowsRecursively(user, TwitchV3Service.MAX_LIMIT, 0, direction, sortBy, listener, new ArrayList<UserFollow>(TwitchV3Service.MAX_LIMIT));
    }

    private void getAllUserFollowsRecursively(
            final String user,
            final int limit,
            final int offset,
            final Direction direction,
            final SortBy sortBy,
            final ResponseListener<List<UserFollow>> listener,
            final List<UserFollow> cache)
    {
        getUserFollows(user, limit, offset, direction, sortBy, new ResponseListener<UserFollowsContainer>()
        {
            @Override
            public void onSuccess(UserFollowsContainer userFollowsContainer)
            {
                cache.addAll(userFollowsContainer.userFollows);
                if (userFollowsContainer.total > offset + limit)
                {
                    getAllUserFollowsRecursively(user, limit, offset + limit, direction, sortBy, listener, cache);
                } else
                {
                    listener.onSuccess(cache);
                }
            }

            @Override
            public void onError(Error error)
            {
                listener.onError(error);
            }
        });
    }
    //</editor-fold>

    //<editor-fold desc="Stream(s)">
    public Call<StreamContainer> getStream(String channel, final ResponseListener<Stream> listener)
    {
        Call<StreamContainer> call = getService().getStream(channel);

        call.enqueue(new Callback<StreamContainer>()
        {
            @Override
            public void onResponse(Call<StreamContainer> call, Response<StreamContainer> response)
            {
                Error error = ErrorFactory.fromResponse(response);
                if (error == null)
                {
                    listener.onSuccess(response.body().stream);
                } else
                {
                    listener.onError(error);
                }
            }

            @Override
            public void onFailure(Call<StreamContainer> call, Throwable t)
            {
                listener.onError(ErrorFactory.fromThrowable(t));
            }
        });

        return call;
    }

    public Call<StreamsContainer> getStreams(
            final String game,
            final List<Channel> channels,
            final Integer limit,
            final Integer offset,
            final String clientId,
            final StreamType streamType,
            final ResponseListener<StreamsContainer> listener)
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


        Call<StreamsContainer> call = getService().getStreams(
                game,
                channelNames != null ? StringUtil.joinStrings(channelNames, ",") : null,
                limit,
                offset,
                clientId,
                streamType);

        call.enqueue(new Callback<StreamsContainer>()
        {
            @Override
            public void onResponse(Call<StreamsContainer> call, Response<StreamsContainer> response)
            {
                Error error = ErrorFactory.fromResponse(response);
                if (error == null)
                {
                    StreamsContainer streamsContainer = response.body();
                    streamsContainer.game = game;
                    streamsContainer.channels = channels;
                    streamsContainer.limit = limit == null ? TwitchV3Service.DEFAULT_LIMIT : limit;
                    streamsContainer.offset = offset == null ? 0 : offset;
                    streamsContainer.clientId = clientId;
                    streamsContainer.streamType = streamType == null ? StreamType.DEFAULT : streamType;
                    listener.onSuccess(response.body());
                } else
                {
                    listener.onError(error);
                }
            }

            @Override
            public void onFailure(Call<StreamsContainer> call, Throwable t)
            {
                listener.onError(ErrorFactory.fromThrowable(t));
            }
        });
        return call;
    }

    public void getAllStreams(
            String game,
            List<Channel> channels,
            String clientId,
            StreamType streamType,
            int resultLimit,
            ResponseListener<List<Stream>> listener)
    {
        resultLimit = resultLimit < TwitchV3Service.DEFAULT_LIMIT ? TwitchV3Service.DEFAULT_LIMIT : resultLimit;
        int perRequestLimit = TwitchV3Service.MAX_LIMIT;
        if (resultLimit < perRequestLimit)
        {
            perRequestLimit = resultLimit;
        }
        getAllStreamsRecursively(game, channels, perRequestLimit, 0, clientId, streamType, resultLimit, listener, new ArrayList<Stream>(resultLimit));
    }

    private void getAllStreamsRecursively(
            final String game,
            final List<Channel> channels,
            final int perRequestLimit,
            final int offset,
            final String clientId,
            final StreamType streamType,
            final int resultLimit,
            final ResponseListener<List<Stream>> listener,
            final List<Stream> cache)
    {
        getStreams(game, channels, perRequestLimit, offset, clientId, streamType, new ResponseListener<StreamsContainer>()
        {
            @Override
            public void onSuccess(StreamsContainer streamsContainer)
            {
                if(streamsContainer.streams.size() != perRequestLimit) {

                }
                if(cache.size() + streamsContainer.streams.size() > resultLimit) {
                    for (Stream stream : streamsContainer.streams)
                    {
                        cache.add(stream);
                        if(cache.size() == resultLimit) {
                            listener.onSuccess(cache);
                            return;
                        }
                    }
                }

                if (cache.size() < resultLimit && streamsContainer.total > offset + perRequestLimit)
                {
                    getAllStreamsRecursively(game, channels, perRequestLimit, offset + perRequestLimit, clientId, streamType, resultLimit, listener, cache);
                } else
                {
                    listener.onSuccess(cache);
                }
            }

            @Override
            public void onError(Error error)
            {
                listener.onError(error);
            }
        });
    }

    public void getUser(String user, final ResponseListener<User> listener)
    {
        getService().getUser(user).enqueue(new Callback<User>()
        {
            @Override
            public void onResponse(Call<User> call, Response<User> response)
            {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t)
            {
                listener.onError(ErrorFactory.fromThrowable(t));
            }
        });
    }
    //</editor-fold>

    //<editor-fold desc="Inner Classes / Interfaces">
    public interface ResponseListener<T>
    {
        void onSuccess(T t);

        void onError(Error error);
    }
    //</editor-fold>
}
