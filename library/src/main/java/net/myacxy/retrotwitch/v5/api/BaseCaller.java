package net.myacxy.retrotwitch.v5.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseCaller<S> {

    static final String BASE_URL = "https://api.twitch.tv/kraken/";

    final OkHttpClient client;
    final Retrofit retrofit;
    final S service;

    public BaseCaller(OkHttpClient client) {
        this.client = client;
        retrofit = createRetrofit();
        service = createService(retrofit);
    }

    protected Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    protected abstract S createService(Retrofit retrofit);

    protected final S getService() {
        return service;
    }
}
