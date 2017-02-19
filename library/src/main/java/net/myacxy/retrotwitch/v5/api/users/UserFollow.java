package net.myacxy.retrotwitch.v5.api.users;

import com.google.gson.annotations.SerializedName;

import net.myacxy.retrotwitch.v5.api.channels.SimpleChannel;

public class UserFollow {
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("notifications")
    private boolean notifications;
    @SerializedName("channel")
    private SimpleChannel channel;

    public String getCreatedAt() {
        return createdAt;
    }

    public boolean getNotifications() {
        return notifications;
    }

    public SimpleChannel getChannel() {
        return channel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserFollow that = (UserFollow) o;

        if (notifications != that.notifications) return false;
        if (!createdAt.equals(that.createdAt)) return false;
        return channel.equals(that.channel);
    }

    @Override
    public int hashCode() {
        int result = createdAt.hashCode();
        result = 31 * result + (notifications ? 1 : 0);
        result = 31 * result + channel.hashCode();
        return result;
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
