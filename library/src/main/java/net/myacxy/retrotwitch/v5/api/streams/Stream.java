package net.myacxy.retrotwitch.v5.api.streams;

import com.google.gson.annotations.SerializedName;

import net.myacxy.retrotwitch.v5.api.channels.SimpleChannel;

public class Stream {

    @SerializedName("_id")
    private long id;
    @SerializedName("average_fps")
    private double averageFps;
    @SerializedName("channel")
    private SimpleChannel channel;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("delay")
    private long delay;
    @SerializedName("game")
    private String game;
    @SerializedName("is_playlist")
    private boolean isPlaylist;
    @SerializedName("preview")
    private Preview preview;
    @SerializedName("video_height")
    private int videoHeight;
    @SerializedName("viewers")
    private long viewers;

    public long getId() {
        return id;
    }

    public double getAverageFps() {
        return averageFps;
    }

    public SimpleChannel getChannel() {
        return channel;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public long getDelay() {
        return delay;
    }

    public String getGame() {
        return game;
    }

    public boolean getPlaylist() {
        return isPlaylist;
    }

    public Preview getPreview() {
        return preview;
    }

    public int getVideoHeight() {
        return videoHeight;
    }

    public long getViewers() {
        return viewers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stream stream = (Stream) o;

        if (id != stream.id) return false;
        if (Double.compare(stream.averageFps, averageFps) != 0) return false;
        if (delay != stream.delay) return false;
        if (isPlaylist != stream.isPlaylist) return false;
        if (videoHeight != stream.videoHeight) return false;
        if (viewers != stream.viewers) return false;
        if (!channel.equals(stream.channel)) return false;
        if (!createdAt.equals(stream.createdAt)) return false;
        if (game != null ? !game.equals(stream.game) : stream.game != null) return false;
        return preview != null ? preview.equals(stream.preview) : stream.preview == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        temp = Double.doubleToLongBits(averageFps);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + channel.hashCode();
        result = 31 * result + createdAt.hashCode();
        result = 31 * result + (int) (delay ^ (delay >>> 32));
        result = 31 * result + (game != null ? game.hashCode() : 0);
        result = 31 * result + (isPlaylist ? 1 : 0);
        result = 31 * result + (preview != null ? preview.hashCode() : 0);
        result = 31 * result + videoHeight;
        result = 31 * result + (int) (viewers ^ (viewers >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Stream{");
        sb.append("id=").append(id);
        sb.append(", averageFps=").append(averageFps);
        sb.append(", channel=").append(channel);
        sb.append(", createdAt='").append(createdAt).append('\'');
        sb.append(", delay=").append(delay);
        sb.append(", game='").append(game).append('\'');
        sb.append(", isPlaylist=").append(isPlaylist);
        sb.append(", preview=").append(preview);
        sb.append(", videoHeight=").append(videoHeight);
        sb.append(", viewers=").append(viewers);
        sb.append('}');
        return sb.toString();
    }

    public class Preview {

        @SerializedName("small")
        private String small;
        @SerializedName("medium")
        private String medium;
        @SerializedName("large")
        private String large;
        @SerializedName("template")
        private String template;

        public String getSmall() {
            return small;
        }

        public String getMedium() {
            return medium;
        }

        public String getLarge() {
            return large;
        }

        public String getTemplate() {
            return template;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Preview preview = (Preview) o;

            if (small != null ? !small.equals(preview.small) : preview.small != null) return false;
            if (medium != null ? !medium.equals(preview.medium) : preview.medium != null)
                return false;
            if (large != null ? !large.equals(preview.large) : preview.large != null) return false;
            return template != null ? template.equals(preview.template) : preview.template == null;

        }

        @Override
        public int hashCode() {
            int result = small != null ? small.hashCode() : 0;
            result = 31 * result + (medium != null ? medium.hashCode() : 0);
            result = 31 * result + (large != null ? large.hashCode() : 0);
            result = 31 * result + (template != null ? template.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Preview{");
            sb.append("small='").append(small).append('\'');
            sb.append(", medium='").append(medium).append('\'');
            sb.append(", large='").append(large).append('\'');
            sb.append(", template='").append(template).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}
