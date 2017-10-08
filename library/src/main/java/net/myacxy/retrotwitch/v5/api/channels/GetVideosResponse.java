package net.myacxy.retrotwitch.v5.api.channels;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetVideosResponse {

    @SerializedName("_total")
    private Integer total;
    @SerializedName("videos")
    private List<Video> videos = new ArrayList<>();

    public Integer getTotal() {
        return total;
    }

    public List<Video> getVideos() {
        return videos;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GetVideosResponse{");
        sb.append("videos=").append(videos);
        sb.append(", total=").append(total);
        sb.append('}');
        return sb.toString();
    }
}
