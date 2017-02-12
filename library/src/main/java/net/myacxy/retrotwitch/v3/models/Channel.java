package net.myacxy.retrotwitch.v3.models;

import com.google.gson.annotations.SerializedName;

public class Channel extends BaseModel<Channel.Links>
{
    @SerializedName("mature")
    public Boolean mature;
    @SerializedName("status")
    public String status;
    @SerializedName("broadcaster_language")
    public String broadcasterLanguage;
    @SerializedName("display_name")
    public String displayName;
    @SerializedName("game")
    public String game;
    @SerializedName("delay")
    public Long delay;
    @SerializedName("language")
    public String language;
    @SerializedName("_id")
    public Long id;
    @SerializedName("name")
    public String name;
    @SerializedName("created_at")
    public String createdAt;
    @SerializedName("updated_at")
    public String updatedAt;
    @SerializedName("logo")
    public String logo;
    @SerializedName("banner")
    public String banner;
    @SerializedName("video_banner")
    public String videoBanner;
    @SerializedName("background")
    public Object background;
    @SerializedName("profile_banner")
    public String profileBanner;
    @SerializedName("profile_banner_background_color")
    public String profileBannerBackgroundColor;
    @SerializedName("partner")
    public Boolean partner;
    @SerializedName("url")
    public String url;
    @SerializedName("views")
    public Integer views;
    @SerializedName("followers")
    public Integer followers;
    @SerializedName("_links")
    public Links links;

    private Channel()
    {

    }

    @Override
    public Links getLinks()
    {
        return links;
    }

    public class Links extends BaseModel.Links
    {
        @SerializedName("follows")
        public String follows;
        @SerializedName("commercial")
        public String commercial;
        @SerializedName("stream_key")
        public String streamKey;
        @SerializedName("chat")
        public String chat;
        @SerializedName("features")
        public String features;
        @SerializedName("subscriptions")
        public String subscriptions;
        @SerializedName("editors")
        public String editors;
        @SerializedName("teams")
        public String teams;
        @SerializedName("videos")
        public String videos;
    }
}
