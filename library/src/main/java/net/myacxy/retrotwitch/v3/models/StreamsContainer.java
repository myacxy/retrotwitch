package net.myacxy.retrotwitch.v3.models;

import com.google.gson.annotations.SerializedName;

import net.myacxy.retrotwitch.v3.api.TwitchV3Service;

import java.util.ArrayList;
import java.util.List;

public class StreamsContainer extends BaseModel<StreamsContainer.Links>
{
    @SerializedName("_total")
    public Integer total;
    @SerializedName("streams")
    public List<Stream> streams = new ArrayList<>();
    @SerializedName("_links")
    public Links links;

    public transient String game = null;
    public transient List<net.myacxy.retrotwitch.v3.models.Channel> channels = null;
    public transient int limit = TwitchV3Service.DEFAULT_LIMIT;
    public transient int offset = 0;
    public transient String clientId = null;
    public transient StreamType streamType = StreamType.DEFAULT;

    private StreamsContainer() { }

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
        @SerializedName("prev")
        public String prev;
        @SerializedName("next")
        public String next;
        @SerializedName("featured")
        public String featured;
    }
}
