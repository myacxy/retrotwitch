package net.myacxy.retrotwitch;

import net.myacxy.retrotwitch.models.Channel;
import net.myacxy.retrotwitch.models.Follow;
import net.myacxy.retrotwitch.models.Stream;
import net.myacxy.retrotwitch.responses.GetFollowsResponse;
import net.myacxy.retrotwitch.responses.GetStreamResponse;
import net.myacxy.retrotwitch.responses.GetStreamsResponse;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.List;

public class Caller
{
    //<editor-fold desc="Members">
    private static Caller mInstance;

    final private HttpLoggingInterceptor mLoggingInterceptor = new HttpLoggingInterceptor();

    final private OkHttpClient mClient = new OkHttpClient.Builder()
            .addInterceptor(mLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
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

    final private Retrofit mRetrofit = new Retrofit.Builder()
            .baseUrl(TwitchV3Service.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(mClient)
            .build();

    final private TwitchV3Service mService = mRetrofit.create(TwitchV3Service.class);
    //</editor-fold>

    //<editor-fold desc="Public Methods">
    public static Caller getInstance()
    {
        return mInstance == null ? mInstance = new Caller() : mInstance;
    }

    public void getUserFollows(String user, final ResponseListener<List<Follow>> listener)
    {
        Call<GetFollowsResponse> response = mService.getUserFollows(user);

        response.enqueue(new Callback<GetFollowsResponse>()
        {
            @Override
            public void onResponse(Response<GetFollowsResponse> response)
            {
                listener.onSuccess(response.body().follows);
            }

            @Override
            public void onFailure(Throwable t)
            {
                listener.onError();
            }
        });
    }

    public void getUserFollowsRaw(String user, final ResponseListener<GetFollowsResponse> listener)
    {
        Call<GetFollowsResponse> response = mService.getUserFollows(user);

        response.enqueue(new Callback<GetFollowsResponse>()
        {
            @Override
            public void onResponse(Response<GetFollowsResponse> response)
            {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Throwable t)
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
            public void onResponse(Response<GetStreamResponse> response)
            {
                listener.onSuccess(response.body().stream);
            }

            @Override
            public void onFailure(Throwable t)
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
            public void onResponse(Response<GetStreamResponse> response)
            {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Throwable t)
            {
                listener.onError();
            }
        });
    }

    public void getStreams(String game,
                           List<String> channels,
                           Integer limit,
                           Integer offset,
                           String clientId,
                           TwitchV3Service.StreamType streamType,
                           final ResponseListener<List<Stream>> listener)
    {
        // separate channels by comma
        StringBuilder csc = new StringBuilder();
        boolean firstEntry = true;
        for (String channel : channels)
        {
            if(firstEntry) firstEntry = false;
            else csc.append(",");
            csc.append(channel);
        }

        Call<GetStreamsResponse> response = mService.getStreams(
                game,
                csc.toString(),
                limit,
                offset,
                clientId,
                streamType);

        response.enqueue(new Callback<GetStreamsResponse>()
        {
            @Override
            public void onResponse(Response<GetStreamsResponse> response)
            {
                listener.onSuccess(response.body().streams);
            }

            @Override
            public void onFailure(Throwable t)
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
    //</editor-fold>
}
