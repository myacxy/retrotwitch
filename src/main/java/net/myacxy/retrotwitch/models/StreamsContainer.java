package net.myacxy.retrotwitch.models;

import com.google.gson.annotations.SerializedName;
import com.sun.istack.internal.Nullable;
import net.myacxy.retrotwitch.Caller;
import net.myacxy.retrotwitch.api.StreamType;
import net.myacxy.retrotwitch.api.TwitchV3Service;

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
    public transient List<Channel> channels = null;
    public transient int limit = TwitchV3Service.DEFAULT_LIMIT;
    public transient int offset = 0;
    public transient String clientId = null;
    public transient StreamType streamType = StreamType.DEFAULT;

    @Override
    public Links getLinks()
    {
        return links;
    }



    public void getPrevious(Caller.ResponseListener<StreamsContainer> listener)
    {
        if(hasPrevious())
        {
            Caller.getInstance().getStreams(game, channels, limit, offset - limit, clientId, streamType, listener);
        }
    }

    public void getNext(Caller.ResponseListener<StreamsContainer> listener)
    {
        if(hasNext())
        {
            Caller.getInstance().getStreams(game, channels, limit, offset + limit, clientId, streamType, listener);
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
