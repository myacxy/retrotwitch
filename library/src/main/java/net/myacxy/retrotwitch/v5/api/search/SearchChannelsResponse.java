package net.myacxy.retrotwitch.v5.api.search;

import com.google.gson.annotations.SerializedName;

import net.myacxy.retrotwitch.v5.api.channels.SimpleChannel;

import java.util.List;

public class SearchChannelsResponse {

    @SerializedName("_total")
    private int total;
    @SerializedName("channels")
    private List<SimpleChannel> channels;

    public int getTotal() {
        return total;
    }

    public List<SimpleChannel> getChannels() {
        return channels;
    }

    @Override
    public String toString() {
        return "SearchChannelsResponse{" +
                "total=" + total +
                ", channels=" + channels +
                '}';
    }
}
