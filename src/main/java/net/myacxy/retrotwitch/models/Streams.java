package net.myacxy.retrotwitch.models;

import com.google.gson.annotations.SerializedName;
import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Streams extends BaseModel<Streams.Links>
{
    @SerializedName("_total")
    public Integer total;
    @SerializedName("streams")
    public List<Stream> streams = new ArrayList<>();
    @SerializedName("_links")
    public Links links;

    @Override
    public Links getLinks()
    {
        return links;
    }

    public class Links extends BaseModel.Links
    {
        @SerializedName("summary")
        public String summary;
        @SerializedName("followed")
        public String followed;
        @Nullable
        @SerializedName("prev")
        public String prev;
        @SerializedName("next")
        public String next;
        @SerializedName("featured")
        public String featured;
    }
}
