package net.myacxy.retrotwitch.v5.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import net.myacxy.retrotwitch.v5.api.common.Error;

import retrofit2.Response;

public class ErrorFactory {

    protected static Gson GSON = new GsonBuilder().create();

    public static Error fromJson(String json) {
        try {
            return GSON.fromJson(json, Error.class);
        } catch (JsonSyntaxException e) {
            return unexpected();
        }
    }

    public static <T> Error fromResponse(Response<T> response) {
        if (response.code() == 200) {
            return null;
        }
        try {
            return fromJson(response.errorBody().string());
        } catch (Exception e) {
            return unexpected(response.code(), response.message());
        }
    }

    public static Error fromThrowable(Throwable t) {
        return unexpected(-1, t.toString());
    }

    public static Error unexpected(int code, String message) {
        return new Error("Unexpected", code, message);
    }

    public static Error unexpected() {
        return unexpected(-1, "An unexpected error occurred.");
    }
}
