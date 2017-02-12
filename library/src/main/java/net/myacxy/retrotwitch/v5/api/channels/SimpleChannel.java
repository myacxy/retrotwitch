package net.myacxy.retrotwitch.v5.api.channels;

import com.google.gson.annotations.SerializedName;

public class SimpleChannel {
    @SerializedName("_id")
    private Integer id;
    @SerializedName("broadcaster_language")
    private String broadcasterLanguage;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("display_name")
    private String displayName;
    @SerializedName("followers")
    private Integer followers;
    @SerializedName("game")
    private String game;
    @SerializedName("language")
    private String language;
    @SerializedName("logo")
    private String logo;
    @SerializedName("mature")
    private Boolean mature;
    @SerializedName("name")
    private String name;
    @SerializedName("partner")
    private Boolean partner;
    @SerializedName("profile_banner")
    private String profileBanner;
    @SerializedName("profile_banner_background_color")
    private String profileBannerBackgroundColor;
    @SerializedName("status")
    private String status;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("url")
    private String url;
    @SerializedName("video_banner")
    private String videoBanner;
    @SerializedName("views")
    private Integer views;

    public Integer getId() {
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

    public Integer getFollowers() {
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

    public Boolean getMature() {
        return mature;
    }

    public String getName() {
        return name;
    }

    public Boolean getPartner() {
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

    public Integer getViews() {
        return views;
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
