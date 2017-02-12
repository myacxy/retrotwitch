package net.myacxy.retrotwitch.v5.api.streams;

import com.google.gson.annotations.SerializedName;

import net.myacxy.retrotwitch.v5.api.channels.SimpleChannel;
import net.myacxy.retrotwitch.v5.api.common.StreamType;
import net.myacxy.retrotwitch.v5.api.common.TwitchDefaults;

import java.util.ArrayList;
import java.util.List;

public class StreamsResponse {
    // TODO: 11.02.2017
    public transient List<SimpleChannel> channels = null;
    public transient String game = null;
    public transient String language = null;
    public transient int limit = TwitchDefaults.DEFAULT_LIMIT;
    public transient int offset = TwitchDefaults.DEFAULT_OFFSET;
    public transient StreamType streamType = TwitchDefaults.DEFAULT_STREAM_TYPE;

    @SerializedName("_total")
    private Integer total;
    @SerializedName("streams")
    private List<Stream> streams = new ArrayList<>();

    public Integer getTotal() {
        return total;
    }

    public List<Stream> getStreams() {
        return streams;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StreamsResponse{");
        sb.append("streams=").append(streams);
        sb.append(", total=").append(total);
        sb.append('}');
        return sb.toString();
    }
}
