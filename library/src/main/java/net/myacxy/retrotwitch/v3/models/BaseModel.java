package net.myacxy.retrotwitch.v3.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public abstract class BaseModel<L extends BaseModel.Links>
{
    private static final Gson sGson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public abstract L getLinks();

    @Override
    public String toString()
    {
        return sGson.toJson(this);
    }

    public class Links
    {
        @SerializedName("self")
        public String self;

        @Override
        public String toString()
        {
            return sGson.toJson(this);
        }
    }
}
