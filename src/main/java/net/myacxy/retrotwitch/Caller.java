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
    private static Caller sInstance;

    private final OkHttpClient mClient;
    private final Retrofit mRetrofit;
    private final TwitchV3Service mService;
    //</editor-fold>

    public static Caller getInstance()
    {
        return sInstance == null ? sInstance = new Caller() : sInstance;
    }

    private Caller()
    {
        mClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
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

    //<editor-fold desc="Public Methods">
    public Call<FollowsContainer> getUserFollows(String url, final ResponseListener<FollowsContainer> listener)
    {
        Call<FollowsContainer> call = mService.getUserFollows(url);

        call.enqueue(new Callback<FollowsContainer>()
        {
            @Override
            public void onResponse(Call<FollowsContainer> call, Response<FollowsContainer> response)
            {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<FollowsContainer> call, Throwable t)
            {
                listener.onError();
            }
        });

        return call;
    }


    public Call<FollowsContainer> getUserFollows(
            final String user,
            final Integer limit,
            final Integer offset,
            final Direction direction,
            final SortBy sortBy,
            final ResponseListener<FollowsContainer> listener)
    {
        Call<FollowsContainer> call = mService.getUserFollows(user, limit, offset, direction, sortBy);

        call.enqueue(new Callback<FollowsContainer>()
        {
            @Override
            public void onResponse(Call<FollowsContainer> call, Response<FollowsContainer> response)
            {
                FollowsContainer followsContainer = response.body();
                followsContainer.user = user;
                followsContainer.limit = limit == null ? TwitchV3Service.DEFAULT_LIMIT : limit;
                followsContainer.offset = offset == null ? 0 : offset;
                followsContainer.direction = direction == null ? Direction.DEFAULT : direction;
                followsContainer.sortBy = sortBy == null ? SortBy.DEFAULT : sortBy;
                listener.onSuccess(followsContainer);
            }

            @Override
            public void onFailure(Call<FollowsContainer> call, Throwable t)
            {
                t.printStackTrace();
                listener.onError();
            }
        });
        return call;
    }

    public void getAllUserFollows(final String user, final Direction direction, final SortBy sortBy, final ResponseListener<List<Follow>> listener)
    {
        getAllUserFollowsRecursively(user, TwitchV3Service.MAX_LIMIT, 0, direction, sortBy, listener, new ArrayList<Follow>(TwitchV3Service.MAX_LIMIT));
    }

    private void getAllUserFollowsRecursively(
            final String user,
            final int limit,
            final int offset,
            final Direction direction,
            final SortBy sortBy,
            final ResponseListener<List<Follow>> listener,
            final List<Follow> cache)
    {
        getUserFollows(user, limit, offset, direction, sortBy, new ResponseListener<FollowsContainer>()
        {
            @Override
            public void onSuccess(FollowsContainer followsContainer)
            {
                cache.addAll(followsContainer.follows);
                if(followsContainer.total > offset + limit)
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

    public Call<StreamsContainer> getStreams(String game,
                           List<Channel> channels,
                           Integer limit,
                           Integer offset,
                           String clientId,
                           StreamType streamType,
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
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<StreamsContainer> call, Throwable t)
            {
                listener.onError();
            }
        });

        return null;
    }
    //</editor-fold>

    //<editor-fold desc="Inner Classes / Interfaces">
    public interface ResponseListener<T>
    {
        void onSuccess(T t);

        void onError();
    }
}
