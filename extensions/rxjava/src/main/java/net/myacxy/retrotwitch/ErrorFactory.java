package net.myacxy.retrotwitch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import net.myacxy.retrotwitch.models.Error;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;

public class ErrorFactory
{
    private static Gson mGson = new GsonBuilder().create();

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

    public static Error fromHttpException(HttpException exception)
    {
        try
        {
            return fromJson(exception.response().errorBody().string());
        } catch (IOException e)
        {
            e.printStackTrace();
            return unexpected();
        }
    }

    public static Error fromThrowable(Throwable throwable)
    {
        if (throwable instanceof HttpException)
        {
            return fromHttpException((HttpException) throwable);
        }
        return unexpected();
    }

    public static Error unexpected()
    {
        return new Error("Unexpected", -1, "An unexpected error occurred.");
    }
}
