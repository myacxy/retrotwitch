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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PrivilegedChannel that = (PrivilegedChannel) o;

        if (!email.equals(that.email)) return false;
        return streamKey.equals(that.streamKey);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + streamKey.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PrivilegedChannel{");
        sb.append("id=").append(id);
        sb.append(", broadcasterLanguage='").append(broadcasterLanguage).append('\'');
        sb.append(", createdAt='").append(createdAt).append('\'');
        sb.append(", displayName='").append(displayName).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", followers=").append(followers);
        sb.append(", game='").append(game).append('\'');
        sb.append(", language='").append(language).append('\'');
        sb.append(", logo='").append(logo).append('\'');
        sb.append(", mature=").append(mature);
        sb.append(", name='").append(name).append('\'');
        sb.append(", partner=").append(partner);
        sb.append(", profileBanner='").append(profileBanner).append('\'');
        sb.append(", profileBannerBackgroundColor='").append(profileBannerBackgroundColor).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", streamKey='").append(streamKey).append('\'');
        sb.append(", updatedAt='").append(updatedAt).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", videoBanner='").append(videoBanner).append('\'');
        sb.append(", views=").append(views);
        sb.append('}');
        return sb.toString();
    }
}
