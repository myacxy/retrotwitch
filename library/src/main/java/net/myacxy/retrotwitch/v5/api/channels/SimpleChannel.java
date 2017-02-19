package net.myacxy.retrotwitch.v5.api.channels;

import com.google.gson.annotations.SerializedName;

public class SimpleChannel {

    @SerializedName("_id")
    protected long id;
    @SerializedName("broadcaster_language")
    protected String broadcasterLanguage;
    @SerializedName("created_at")
    protected String createdAt;
    @SerializedName("display_name")
    protected String displayName;
    @SerializedName("followers")
    protected long followers;
    @SerializedName("game")
    protected String game;
    @SerializedName("language")
    protected String language;
    @SerializedName("logo")
    protected String logo;
    @SerializedName("mature")
    protected boolean mature;
    @SerializedName("name")
    protected String name;
    @SerializedName("partner")
    protected boolean partner;
    @SerializedName("profile_banner")
    protected String profileBanner;
    @SerializedName("profile_banner_background_color")
    protected String profileBannerBackgroundColor;
    @SerializedName("status")
    protected String status;
    @SerializedName("updated_at")
    protected String updatedAt;
    @SerializedName("url")
    protected String url;
    @SerializedName("video_banner")
    protected String videoBanner;
    @SerializedName("views")
    protected long views;

    public long getId() {
        return id;
    }

    public String getBroadcasterLanguage() {
        return broadcasterLanguage;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getDisplayName() {
        return displayName;
    }

    public long getFollowers() {
        return followers;
    }

    public String getGame() {
        return game;
    }

    public String getLanguage() {
        return language;
    }

    public String getLogo() {
        return logo;
    }

    public boolean getMature() {
        return mature;
    }

    public String getName() {
        return name;
    }

    public boolean getPartner() {
        return partner;
    }

    public String getProfileBanner() {
        return profileBanner;
    }

    public String getProfileBannerBackgroundColor() {
        return profileBannerBackgroundColor;
    }

    public String getStatus() {
        return status;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getUrl() {
        return url;
    }

    public String getVideoBanner() {
        return videoBanner;
    }

    public long getViews() {
        return views;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleChannel that = (SimpleChannel) o;

        if (id != that.id) return false;
        if (followers != that.followers) return false;
        if (mature != that.mature) return false;
        if (partner != that.partner) return false;
        if (views != that.views) return false;
        if (broadcasterLanguage != null ? !broadcasterLanguage.equals(that.broadcasterLanguage) : that.broadcasterLanguage != null)
            return false;
        if (!createdAt.equals(that.createdAt)) return false;
        if (!displayName.equals(that.displayName)) return false;
        if (game != null ? !game.equals(that.game) : that.game != null) return false;
        if (language != null ? !language.equals(that.language) : that.language != null)
            return false;
        if (logo != null ? !logo.equals(that.logo) : that.logo != null) return false;
        if (!name.equals(that.name)) return false;
        if (profileBanner != null ? !profileBanner.equals(that.profileBanner) : that.profileBanner != null)
            return false;
        if (profileBannerBackgroundColor != null ? !profileBannerBackgroundColor.equals(that.profileBannerBackgroundColor) : that.profileBannerBackgroundColor != null)
            return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (!updatedAt.equals(that.updatedAt)) return false;
        if (!url.equals(that.url)) return false;
        return videoBanner != null ? videoBanner.equals(that.videoBanner) : that.videoBanner == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (broadcasterLanguage != null ? broadcasterLanguage.hashCode() : 0);
        result = 31 * result + createdAt.hashCode();
        result = 31 * result + displayName.hashCode();
        result = 31 * result + (int) (followers ^ (followers >>> 32));
        result = 31 * result + (game != null ? game.hashCode() : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (logo != null ? logo.hashCode() : 0);
        result = 31 * result + (mature ? 1 : 0);
        result = 31 * result + name.hashCode();
        result = 31 * result + (partner ? 1 : 0);
        result = 31 * result + (profileBanner != null ? profileBanner.hashCode() : 0);
        result = 31 * result + (profileBannerBackgroundColor != null ? profileBannerBackgroundColor.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + updatedAt.hashCode();
        result = 31 * result + url.hashCode();
        result = 31 * result + (videoBanner != null ? videoBanner.hashCode() : 0);
        result = 31 * result + (int) (views ^ (views >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SimpleChannel{");
        sb.append("id=").append(id);
        sb.append(", broadcasterLanguage='").append(broadcasterLanguage).append('\'');
        sb.append(", createdAt='").append(createdAt).append('\'');
        sb.append(", displayName='").append(displayName).append('\'');
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
        sb.append(", updatedAt='").append(updatedAt).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append(", videoBanner='").append(videoBanner).append('\'');
        sb.append(", views=").append(views);
        sb.append('}');
        return sb.toString();
    }
}
