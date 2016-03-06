package net.myacxy.retrotwitch;

import net.myacxy.retrotwitch.api.*;
import net.myacxy.retrotwitch.models.*;
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

public class Caller
{
    //<editor-fold desc="Members">
    private static Caller sInstance = new Caller();

    private final OkHttpClient mClient;
    private final Retrofit mRetrofit;
    private final TwitchV3Service mService;
    //</editor-fold>

    //<editor-fold desc="Constructor">
    private Caller(HttpLoggingInterceptor.Level level) {
        mClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(level))
                .addInterceptor(new Interceptor()
                {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException
                    {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Accept", "application/vnd.twitchtv.v3+json")
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(TwitchV3Service.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(mClient)
                .build();

        mService = mRetrofit.create(TwitchV3Service.class);
    }

    private Caller()
    {
        this(HttpLoggingInterceptor.Level.NONE);
    }
    //</editor-fold>

    public static Caller getInstance()
    {
        return sInstance;
    }

    public static Caller newInstance(HttpLoggingInterceptor.Level level) {
        return sInstance = new Caller(level);
    }

    public TwitchV3Service getService() {
        return mService;
    }

    //<editor-fold desc="User Follows">
    public Call<UserFollowsContainer> getUserFollows(
            String url,
            final ResponseListener<UserFollowsContainer> listener)
    {
        Call<UserFollowsContainer> call = mService.getUserFollows(url);

        call.enqueue(new Callback<UserFollowsContainer>()
        {
            @Override
            public void onResponse(Call<UserFollowsContainer> call, Response<UserFollowsContainer> response)
            {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<UserFollowsContainer> call, Throwable t)
            {
                listener.onError();
            }
        });

        return call;
    }


    public Call<UserFollowsContainer> getUserFollows(
            final String user,
            final Integer limit,
            final Integer offset,
            final Direction direction,
            final SortBy sortBy,
            final ResponseListener<UserFollowsContainer> listener)
    {
        Call<UserFollowsContainer> call = mService.getUserFollows(user, limit, offset, direction, sortBy);

        call.enqueue(new Callback<UserFollowsContainer>()
        {
            @Override
            public void onResponse(Call<UserFollowsContainer> call, Response<UserFollowsContainer> response)
            {
                UserFollowsContainer userFollowsContainer = response.body();
                userFollowsContainer.user = user;
                userFollowsContainer.limit = limit == null ? TwitchV3Service.DEFAULT_LIMIT : limit;
                userFollowsContainer.offset = offset == null ? 0 : offset;
                userFollowsContainer.direction = direction == null ? Direction.DEFAULT : direction;
                userFollowsContainer.sortBy = sortBy == null ? SortBy.DEFAULT : sortBy;
                listener.onSuccess(userFollowsContainer);
            }

            @Override
            public void onFailure(Call<UserFollowsContainer> call, Throwable t)
            {
                listener.onError();
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
                if(userFollowsContainer.total > offset + limit)
                {
                    getAllUserFollowsRecursively(user, limit, offset + limit, direction, sortBy, listener, cache);
                }
                else
                {
                    listener.onSuccess(cache);
                }
            }

            @Override
            public void onError()
            {
                listener.onError();
            }
        });
    }
    //</editor-fold>

    //<editor-fold desc="Stream(s)">
    public Call<StreamContainer> getStream(String channel, final ResponseListener<Stream> listener)
    {
        Call<StreamContainer> call = mService.getStream(channel);

        call.enqueue(new Callback<StreamContainer>()
        {
            @Override
            public void onResponse(Call<StreamContainer> call, Response<StreamContainer> response)
            {
                listener.onSuccess(response.body().stream);
            }

            @Override
            public void onFailure(Call<StreamContainer> call, Throwable t)
            {
                listener.onError();
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
        if(channels != null)
        {
            channelNames = new ArrayList<>(channels.size());
            for (Channel channel : channels)
            {
                channelNames.add(channel.name);
            }
        }


        Call<StreamsContainer> call = mService.getStreams(
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
                StreamsContainer streamsContainer = response.body();
                streamsContainer.game = game;
                streamsContainer.channels = channels;
                streamsContainer.limit = limit == null ? TwitchV3Service.DEFAULT_LIMIT : limit;
                streamsContainer.offset = offset == null ? 0 : offset;
                streamsContainer.clientId = clientId;
                streamsContainer.streamType = streamType == null ? StreamType.DEFAULT : streamType;
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<StreamsContainer> call, Throwable t)
            {
                listener.onError();
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
        if(resultLimit < perRequestLimit) {
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
                cache.addAll(streamsContainer.streams);
                if(cache.size() < resultLimit && streamsContainer.total > offset + perRequestLimit)
                {
                    getAllStreamsRecursively(game, channels, perRequestLimit, offset + perRequestLimit, clientId, streamType, resultLimit, listener, cache);
                }
                else
                {
                    listener.onSuccess(cache);
                }
            }

            @Override
            public void onError()
            {
                listener.onError();
            }
        });
    }
    //</editor-fold>

    //<editor-fold desc="Inner Classes / Interfaces">
    public interface ResponseListener<T>
    {
        void onSuccess(T t);

        void onError();
    }

    public static abstract class CallBuilder<M extends BaseModel>
    {
        public abstract Call<M> build();
        public abstract M buildAndExecute() throws IOException;
        public abstract void buildAndEnqueue(ResponseListener<M> listener);
    }
    //</editor-fold>
}