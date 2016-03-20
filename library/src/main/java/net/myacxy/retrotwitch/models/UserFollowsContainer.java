package net.myacxy.retrotwitch.models;

import com.google.gson.annotations.SerializedName;
import net.myacxy.retrotwitch.Caller;
import net.myacxy.retrotwitch.api.Direction;
import net.myacxy.retrotwitch.api.SortBy;
import net.myacxy.retrotwitch.api.TwitchV3Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserFollowsContainer extends BaseModel<UserFollowsContainer.Links>
{
    @SerializedName("_links")
    public Links links;
    @SerializedName("_total")
    public Integer total;
    @SerializedName("follows")
    public List<UserFollow> userFollows = new ArrayList<>();

    public transient String user = null;
    public transient int limit = TwitchV3Service.DEFAULT_LIMIT;
    public transient int offset = 0;
    public transient Direction direction = Direction.DEFAULT;
    public transient SortBy sortBy = SortBy.DEFAULT;

    private UserFollowsContainer() { }

    @Override
    public Links getLinks()
    {
        return links;
    }

    public class Links extends BaseModel.Links
    {
        @SerializedName("prev")
        public String prev;
        @SerializedName("next")
        public String next;
    }
}
