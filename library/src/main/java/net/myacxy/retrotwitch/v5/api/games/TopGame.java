package net.myacxy.retrotwitch.v5.api.games;

import com.google.gson.annotations.SerializedName;

public class TopGame {

    @SerializedName("channels")
    private int channels;
    @SerializedName("viewers")
    private int viewers;
    @SerializedName("game")
    private Game game;
    @SerializedName("name")
    private String name;
    @SerializedName("popularity")
    private int popularity;

    public int getChannels() {
        return channels;
    }

    public int getViewers() {
        return viewers;
    }

    public Game getGame() {
        return game;
    }

    public String getName() {
        return name;
    }

    public int getPopularity() {
        return popularity;
    }

    @Override
    public String toString() {
        return "TopGame{" +
                "channels=" + channels +
                ", viewers=" + viewers +
                ", game=" + game +
                ", name='" + name + '\'' +
                ", popularity=" + popularity +
                '}';
    }
}
