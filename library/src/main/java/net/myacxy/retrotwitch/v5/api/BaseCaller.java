package net.myacxy.retrotwitch.v5.api;

import net.myacxy.retrotwitch.v3.api.TwitchV3Service;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseCaller<S> {
    final OkHttpClient client;
    final Retrofit retrofit;
    final S service;

    public BaseCaller(OkHttpClient client) {
        this.client = client;
        retrofit = createRetrofit();
        service = createService(retrofit);
    }

    Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(TwitchV3Service.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public abstract S createService(Retrofit retrofit);

    public final S getService() {
        return service;
    }
}
