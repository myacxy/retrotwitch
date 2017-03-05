package net.myacxy.retrotwitch.v5.api.games;

import net.myacxy.retrotwitch.v5.api.BaseCaller;
import net.myacxy.retrotwitch.v5.api.ResponseListener;
import net.myacxy.retrotwitch.v5.api.SimpleRetroTwitchCallback;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;

public class GamesCaller extends BaseCaller<TwitchGamesService> {

    public GamesCaller(OkHttpClient client) {
        super(client);
    }

    @Override
    protected TwitchGamesService createService(Retrofit retrofit) {
        return retrofit.create(TwitchGamesService.class);
    }

    //<editor-fold desc="API Calls">

    /**
     * <p>Gets games sorted by number of current viewers on Twitch, most popular first.</p>
     * <p>
     * <p><b>Authentication</b></p>
     * <p>None</p>
     *
     * @param limit  Maximum number of objects to return, sorted by number of viewers. Default: 25. Maximum: 100.
     * @param offset Object offset for pagination of results. Default: 0.
     */
    public Call<TopGamesResponse> getTopGames(int limit, int offset, final ResponseListener<TopGamesResponse> listener) {
        Call<TopGamesResponse> call = getService().getTopGames(limit, offset);
        call.enqueue(new SimpleRetroTwitchCallback<>(listener));
        return call;
    }
    //</editor-fold>
}
