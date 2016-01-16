package net.myacxy.retrotwitch.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class BaseModel
{
    private static final Gson mGson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    @Override
    public String toString()
    {
        return mGson.toJson(this);
    }
}
