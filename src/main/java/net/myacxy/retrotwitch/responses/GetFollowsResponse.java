package net.myacxy.retrotwitch.responses;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;
import net.myacxy.retrotwitch.models.BaseModel;
import net.myacxy.retrotwitch.models.Follow;

public class GetFollowsResponse extends BaseModel
{
    @SerializedName("_links")
    public Links links;
    @SerializedName("_total")
    public Integer total;
    @SerializedName("follows")
    public List<Follow> follows = new ArrayList<>();

    public class Links extends BaseModel
    {
        @SerializedName("next")
        public String next;
        @SerializedName("self")
        public String self;
    }
}
