package net.myacxy.retrotwitch.v5.api.games;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RxTwitchGamesService {

    /**
     * <p>Gets games sorted by number of current viewers on Twitch, most popular first.</p>
     * <p>
     * <p><b>Authentication</b></p>
     * <p>None</p>
     *
     * @param limit  Maximum number of objects to return, sorted by number of viewers. Default: 25. Maximum: 100.
     * @param offset Object offset for pagination of results. Default: 0.
     */
    @GET("games/top")
    Observable<Response<TopGamesResponse>> getTopGames(@Query("limit") int limit, @Query("offset") int offset);
}
