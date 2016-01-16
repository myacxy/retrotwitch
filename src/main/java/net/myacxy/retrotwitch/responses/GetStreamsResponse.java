package net.myacxy.retrotwitch.responses;

import com.google.gson.annotations.SerializedName;
import net.myacxy.retrotwitch.models.BaseModel;
import net.myacxy.retrotwitch.models.Stream;

import java.util.ArrayList;
import java.util.List;

public class GetStreamsResponse extends BaseModel
{
    @SerializedName("_total")
    public Integer total;
    @SerializedName("streams")
    public List<Stream> streams = new ArrayList<>();
    @SerializedName("_links")
    public Links links;

    public class Links
    {
        @SerializedName("summary")
        public String summary;
        @SerializedName("followed")
        public String followed;
        @SerializedName("next")
        public String next;
        @SerializedName("featured")
        public String featured;
        @SerializedName("self")
        public String self;
    }
}
