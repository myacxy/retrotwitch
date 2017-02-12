package net.myacxy.retrotwitch.v5.api.streams;

import com.google.gson.annotations.SerializedName;

import net.myacxy.retrotwitch.v5.api.channels.SimpleChannel;

public class Stream {

    @SerializedName("_id")
    private Long id;
    @SerializedName("average_fps")
    private Double averageFps;
    @SerializedName("channel")
    private SimpleChannel channel;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("delay")
    private Integer delay;
    @SerializedName("game")
    private String game;
    @SerializedName("is_playlist")
    private Boolean isPlaylist;
    @SerializedName("preview")
    private Preview preview;
    @SerializedName("video_height")
    private Integer videoHeight;
    @SerializedName("viewers")
    private Integer viewers;

    public Long getId() {
        return id;
    }

    public Double getAverageFps() {
        return averageFps;
    }

    public SimpleChannel getChannel() {
        return channel;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Integer getDelay() {
        return delay;
    }

    public String getGame() {
        return game;
    }

    public Boolean getPlaylist() {
        return isPlaylist;
    }

    public Preview getPreview() {
        return preview;
    }

    public Integer getVideoHeight() {
        return videoHeight;
    }

    public Integer getViewers() {
        return viewers;
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
