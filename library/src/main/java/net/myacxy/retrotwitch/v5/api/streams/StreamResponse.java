package net.myacxy.retrotwitch.v5.api.streams;

import com.google.gson.annotations.SerializedName;

public class StreamResponse {
    @SerializedName("stream")
    public Stream stream;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StreamResponse{");
        sb.append("stream=").append(stream);
        sb.append('}');
        return sb.toString();
    }
}
