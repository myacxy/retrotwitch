package net.myacxy.retrotwitch.v5.api.users;

import com.google.gson.annotations.SerializedName;

import net.myacxy.retrotwitch.v5.api.channels.SimpleChannel;

public class UserFollow {
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("notifications")
    private Boolean notifications;
    @SerializedName("channel")
    private SimpleChannel channel;

    public String getCreatedAt() {
        return createdAt;
    }

    public Boolean getNotifications() {
        return notifications;
    }

    public SimpleChannel getChannel() {
        return channel;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserFollow{");
        sb.append("createdAt='").append(createdAt).append('\'');
        sb.append(", notifications=").append(notifications);
        sb.append(", channel=").append(channel);
        sb.append('}');
        return sb.toString();
    }
}
