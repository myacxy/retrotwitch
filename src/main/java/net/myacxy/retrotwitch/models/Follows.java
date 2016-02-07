package net.myacxy.retrotwitch.models;

import com.google.gson.annotations.SerializedName;
import com.sun.istack.internal.Nullable;
import net.myacxy.retrotwitch.Caller;
import net.myacxy.retrotwitch.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Follows extends BaseModel<Follows.Links>
{
    @SerializedName("_links")
    public Links links;
    @SerializedName("_total")
    public Integer total;
    @SerializedName("follows")
    public List<Follow> follows = new ArrayList<>();

    @Override
    public Links getLinks()
    {
        return links;
    }

    public void getPrevious(Caller.ResponseListener<Follows> listener)
    {
        if(!StringUtils.isBlank(links.prev))
        {
            Caller.getInstance().getUserFollows(links.prev, listener);
        }
    }

    public void getNext(Caller.ResponseListener<Follows> listener)
    {
        if(!StringUtils.isBlank(links.next))
        {
            Caller.getInstance().getUserFollows(links.next, listener);
        }
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
