package net.myacxy.retrotwitch.v5.api.search;

import com.google.gson.annotations.SerializedName;

import net.myacxy.retrotwitch.v5.api.streams.Stream;

import java.util.List;

public class SearchStreamsResponse {

    @SerializedName("_total")
    private int total;
    @SerializedName("streams")
    private List<Stream> streams;

    public int getTotal() {
        return total;
    }

    public List<Stream> getStreams() {
        return streams;
    }

    @Override
    public String toString() {
        return "SearchStreamsResponse{" +
                "total=" + total +
                ", streams=" + streams +
                '}';
    }
}
