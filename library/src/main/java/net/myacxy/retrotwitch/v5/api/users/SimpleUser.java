package net.myacxy.retrotwitch.v5.api.users;

import com.google.gson.annotations.SerializedName;

public class SimpleUser {
    @SerializedName("_id")
    protected long id;
    @SerializedName("bio")
    protected String bio;
    @SerializedName("created_at")
    protected String createdAt;
    @SerializedName("display_name")
    protected String displayName;
    @SerializedName("logo")
    protected String logo;
    @SerializedName("name")
    protected String name;
    @SerializedName("type")
    protected String type;
    @SerializedName("updated_at")
    protected String updatedAt;

    public long getId() {
        return id;
    }

    public String getBio() {
        return bio;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getLogo() {
        return logo;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SimpleUser{");
        sb.append("id='").append(id).append('\'');
        sb.append(", bio='").append(bio).append('\'');
        sb.append(", createdAt='").append(createdAt).append('\'');
        sb.append(", displayName='").append(displayName).append('\'');
        sb.append(", logo='").append(logo).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", updatedAt='").append(updatedAt).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
