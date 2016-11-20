package net.myacxy.retrotwitch;

import net.myacxy.retrotwitch.api.TwitchV3Service;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseCaller<S>
{
    final OkHttpClient mClient;
    final Retrofit mRetrofit;
    final S mService;
    private HttpLoggingInterceptor mLoggingInterceptor;
    private String clientId = "";

    BaseCaller(HttpLoggingInterceptor.Level level)
    {
        mClient = createClient(level);
        mRetrofit = createRetrofit();
        mService = createService(mRetrofit);
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
                                .addHeader("Accept", "application/vnd.twitchtv.v3+json")
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
                .client(mClient)
                .build();
    }

    public abstract S createService(Retrofit retrofit);

    public final S getService()
    {
        return mService;
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
