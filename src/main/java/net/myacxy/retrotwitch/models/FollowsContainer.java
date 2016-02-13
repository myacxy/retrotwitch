package net.myacxy.retrotwitch.models;

import com.google.gson.annotations.SerializedName;
import com.sun.istack.internal.Nullable;
import net.myacxy.retrotwitch.Caller;
import net.myacxy.retrotwitch.api.Direction;
import net.myacxy.retrotwitch.api.SortBy;
import net.myacxy.retrotwitch.api.TwitchV3Service;

import java.util.ArrayList;
import java.util.List;

public class FollowsContainer extends BaseModel<FollowsContainer.Links>
{
    @SerializedName("_links")
    public Links links;
    @SerializedName("_total")
    public Integer total;
    @SerializedName("follows")
    public List<Follow> follows = new ArrayList<>();

    public transient String user = null;
    public transient int limit = TwitchV3Service.DEFAULT_LIMIT;
    public transient int offset = 0;
    public transient Direction direction = Direction.DEFAULT;
    public transient SortBy sortBy = SortBy.DEFAULT;

    @Override
    public Links getLinks()
    {
        return links;
    }

    public void getPrevious(Caller.ResponseListener<FollowsContainer> listener)
    {
        if(hasPrevious())
        {
            Caller.getInstance().getUserFollows(user, limit, offset - limit, direction, sortBy, listener);
        }
    }

    public void getNext(Caller.ResponseListener<FollowsContainer> listener)
    {
        if(hasNext())
        {
            Caller.getInstance().getUserFollows(user, limit, offset + limit, direction, sortBy, listener);
        }
    }

    public boolean hasPrevious()
    {
        return total != null && offset > 0 && offset < total;
    }

    public boolean hasNext()
    {
        return total != null && total < offset + limit;
    }

    public class Links extends BaseModel.Links
    {
        @Nullable
        @SerializedName("prev")
        public String prev;
        @SerializedName("next")
        public String next;
    }
}
