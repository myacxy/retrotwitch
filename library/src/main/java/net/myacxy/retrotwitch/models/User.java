package net.myacxy.retrotwitch.models;

import com.google.gson.annotations.SerializedName;

public class User extends BaseModel
{
    @SerializedName("type")
    public String type;
    @SerializedName("name")
    public String name;
    @SerializedName("created_at")
    public String createdAt;
    @SerializedName("updated_at")
    public String updatedAt;
    @SerializedName("_links")
    public BaseModel.Links Links;
    @SerializedName("logo")
    public String logo;
    @SerializedName("_id")
    public long id;
    @SerializedName("display_name")
    public String displayName;
    @SerializedName("bio")
    public String bio;

    @Override
    public Links getLinks()
    {
        return null;
    }
}
