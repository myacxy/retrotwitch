package net.myacxy.retrotwitch.v5.api.games.games;

import net.myacxy.retrotwitch.v5.api.BaseCaller;
import net.myacxy.retrotwitch.v5.api.games.TopGamesResponse;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RxGamesCaller extends BaseCaller<RxTwitchGamesService> {

    public RxGamesCaller(OkHttpClient client) {
        super(client);
    }

    @Override
    protected RxTwitchGamesService createService(Retrofit retrofit) {
        return retrofit.create(RxTwitchGamesService.class);
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
    public Observable<Response<TopGamesResponse>> getTopGames(int limit, int offset) {
        return getService().getTopGames(limit, offset);
    }
    //</editor-fold>
}
