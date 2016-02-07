package net.myacxy.retrotwitch;

import net.myacxy.retrotwitch.api.Direction;
import net.myacxy.retrotwitch.api.SortBy;
import net.myacxy.retrotwitch.api.StreamType;
import net.myacxy.retrotwitch.api.TwitchV3Service;
import net.myacxy.retrotwitch.models.*;
import net.myacxy.retrotwitch.responses.GetStreamResponse;
import net.myacxy.retrotwitch.utils.StringUtils;
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
    public void getUserFollows(String url, final ResponseListener<Follows> listener)
    {
        Call<Follows> response = mService.getUserFollows(url);

        response.enqueue(new Callback<Follows>()
        {
            @Override
            public void onResponse(Call<Follows> call, Response<Follows> response)
            {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Follows> call, Throwable t)
            {
                listener.onError();
            }
        });
    }


    public void getUserFollows(String user, Integer limit, Integer offset, Direction direction, SortBy sortBy, final ResponseListener<Follows> listener)
    {
        Call<Follows> response = mService.getUserFollows(user, limit, offset, direction, sortBy);

        response.enqueue(new Callback<Follows>()
        {
            @Override
            public void onResponse(Call<Follows> call, Response<Follows> response)
            {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Follows> call, Throwable t)
            {
                t.printStackTrace();
                listener.onError();
            }
        });
    }

    public void getUserFollowsRaw(String user, final ResponseListener<Follows> listener)
    {
        Call<Follows> response = mService.getUserFollows(user, null, null, null, null);

        response.enqueue(new Callback<Follows>()
        {
            @Override
            public void onResponse(Call<Follows> call, Response<Follows> response)
            {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Follows> call, Throwable t)
            {
                listener.onError();
            }
        });
    }

    public void getStream(String channel, final ResponseListener<Stream> listener)
    {
        Call<GetStreamResponse> response = mService.getStream(channel);

        response.enqueue(new Callback<GetStreamResponse>()
        {
            @Override
            public void onResponse(Call<GetStreamResponse> call, Response<GetStreamResponse> response)
            {
                listener.onSuccess(response.body().stream);
            }

            @Override
            public void onFailure(Call<GetStreamResponse> call, Throwable t)
            {
                listener.onError();
            }
        });
    }

    public void getStreamRaw(String channel, final ResponseListener<GetStreamResponse> listener)
    {
        Call<GetStreamResponse> response = mService.getStream(channel);

        response.enqueue(new Callback<GetStreamResponse>()
        {
            @Override
            public void onResponse(Call<GetStreamResponse> call, Response<GetStreamResponse> response)
            {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<GetStreamResponse> call, Throwable t)
            {
                listener.onError();
            }
        });
    }

    public void getStreams(String game,
                           List<Channel> channels,
                           Integer limit,
                           Integer offset,
                           String clientId,
                           StreamType streamType,
                           final ResponseListener<Streams> listener)
    {
        List<String> channelNames = null;
        if(channels != null) {
            channelNames = new ArrayList<>(channels.size());
            for (Channel channel : channels)
            {
                channelNames.add(channel.name);
            }
        }

        Call<Streams> response = mService.getStreams(
                game,
                channelNames != null ? StringUtils.joinStrings(channelNames, ",") : null,
                limit,
                offset,
                clientId,
                streamType);

        response.enqueue(new Callback<Streams>()
        {
            @Override
            public void onResponse(Call<Streams> call, Response<Streams> response)
            {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Streams> call, Throwable t)
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
}
