package net.myacxy.retrotwitch.v3;

import net.myacxy.retrotwitch.v3.api.TwitchV3Service;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseCaller<S>
{
    final OkHttpClient client;
    final Retrofit retrofit;
    final S service;
    private HttpLoggingInterceptor mLoggingInterceptor;
    private String clientId = "";

    public BaseCaller(HttpLoggingInterceptor.Level level)
    {
        client = createClient(level);
        retrofit = createRetrofit();
        service = createService(retrofit);
    }

    OkHttpClient createClient(HttpLoggingInterceptor.Level level)
    {
        return new OkHttpClient.Builder()
                .addInterceptor(mLoggingInterceptor = new HttpLoggingInterceptor().setLevel(level))
                .addInterceptor(new Interceptor()
                {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException
                    {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Accept", "application/vnd.twitchtv.v5+json")
                                .addHeader("Client-ID", clientId)
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();
    }

    Retrofit createRetrofit()
    {
        return new Retrofit.Builder()
                .baseUrl(TwitchV3Service.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public abstract S createService(Retrofit retrofit);

    public final S getService()
    {
        return service;
    }

    public final void setClientId(String clientId)
    {
        this.clientId = clientId;
    }

    public final void setLoggingLevel(HttpLoggingInterceptor.Level level)
    {
        mLoggingInterceptor.setLevel(level);
    }
}
