package net.myacxy.retrotwitch.v5.api.channels;

import com.google.gson.annotations.SerializedName;

public class PrivilegedChannel extends SimpleChannel {
    @SerializedName("email")
    private String email;
    @SerializedName("stream_key")
    private String streamKey;

    public String getEmail() {
        return email;
    }

    public String getStreamKey() {
        return streamKey;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PrivilegedChannel{");
        sb.append("email='").append(email).append('\'');
        sb.append(", streamKey='").append(streamKey).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
