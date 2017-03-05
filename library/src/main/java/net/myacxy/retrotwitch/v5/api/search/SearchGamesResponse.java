package net.myacxy.retrotwitch.v5.api.search;

import com.google.gson.annotations.SerializedName;

import net.myacxy.retrotwitch.v5.api.games.Game;

import java.util.List;

public class SearchGamesResponse {

    @SerializedName("channels")
    private List<Game> games;

    public List<Game> getGames() {
        return games;
    }

    @Override
    public String toString() {
        return "SearchGamesResponse{" +
                "channels=" + games +
                '}';
    }
}
