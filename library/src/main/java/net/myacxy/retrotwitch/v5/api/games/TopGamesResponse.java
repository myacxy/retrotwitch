package net.myacxy.retrotwitch.v5.api.games;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TopGamesResponse {

    @SerializedName("_total")
    private Integer total;
    @SerializedName("top")
    private List<TopGame> top = new ArrayList<>();

    public Integer getTotal() {
        return total;
    }

    public List<TopGame> getTop() {
        return top;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TopGamesResponse{");
        sb.append("top=").append(top);
        sb.append(", total=").append(total);
        sb.append('}');
        return sb.toString();
    }
}
