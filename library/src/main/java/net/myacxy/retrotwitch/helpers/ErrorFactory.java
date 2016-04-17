package net.myacxy.retrotwitch.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import net.myacxy.retrotwitch.models.Error;

import java.io.IOException;

import retrofit2.Response;

public class ErrorFactory
{
    private static Gson mGson = new GsonBuilder().create();

    ErrorFactory()
    {
        throw new IllegalAccessError();
    }

    public static Error fromJson(String json)
    {
        try
        {
            return mGson.fromJson(json, Error.class);
        } catch (JsonSyntaxException e)
        {
            return unexpected();
        }
    }

    public static Error fromResponse(Response<?> response)
    {
        if (response.code() == 200)
        {
            return null;
        }
        try
        {
            return fromJson(response.errorBody().string());
        } catch (IOException e)
        {
            e.printStackTrace();
            return unexpected(response.code(), response.message());
        }
    }

    public static Error fromThrowable(Throwable t)
    {
        return unexpected(-1, t.toString());
    }

    public static Error unexpected(int code, String message)
    {
        return new Error("Unexpected", code, message);
    }

    public static Error unexpected()
    {
        return unexpected(-1, "An unexpected error occurred.");
    }
}
