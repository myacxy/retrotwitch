package net.myacxy.retrotwitch.v5.api.users;

import com.google.gson.annotations.SerializedName;

import net.myacxy.retrotwitch.v5.api.common.Direction;
import net.myacxy.retrotwitch.v5.api.common.SortBy;
import net.myacxy.retrotwitch.v5.api.common.TwitchDefaults;

import java.util.ArrayList;
import java.util.List;

public class UserFollowsResponse {
    // TODO: 11.02.2017
    public transient long userId = 0;
    public transient int limit = TwitchDefaults.DEFAULT_LIMIT;
    public transient int offset = TwitchDefaults.DEFAULT_OFFSET;
    public transient Direction direction = TwitchDefaults.DEFAULT_DIRECTION;
    public transient SortBy sortBy = TwitchDefaults.DEFAULT_SORT_BY;

    @SerializedName("_total")
    private Integer total;
    @SerializedName("follows")
    private List<UserFollow> userFollows = new ArrayList<>();

    public Integer getTotal() {
        return total;
    }

    public List<UserFollow> getUserFollows() {
        return userFollows;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserFollowsResponse{");
        sb.append("total=").append(total);
        sb.append(", userFollows=").append(userFollows);
        sb.append('}');
        return sb.toString();
    }
}
