package net.myacxy.retrotwitch.v5.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import net.myacxy.retrotwitch.v5.api.common.RetroTwitchError;

import retrofit2.Response;

public class RetroTwitchErrorFactory {

    protected static Gson GSON = new GsonBuilder().create();

    public static RetroTwitchError unexpected(int code, String message, Throwable cause) {
        return new RetroTwitchError("Unexpected", code, message, cause);
    }

    public static RetroTwitchError unexpected(Throwable cause) {
        return unexpected(-1, cause.getMessage(), cause);
    }

    public static RetroTwitchError fromJson(String json) {
        try {
            return GSON.fromJson(json, RetroTwitchError.class);
        } catch (JsonSyntaxException e) {
            return unexpected(e);
        }
    }

    public static <T> RetroTwitchError fromResponse(Response<T> response) {
        if (response.isSuccessful()) {
            return null;
        }
        try {
            return fromJson(response.errorBody().string());
        } catch (Exception e) {
            return unexpected(response.code(), response.message(), e);
        }
    }

    public static RetroTwitchError fromThrowable(Throwable t) {
        return unexpected(-1, t.toString(), t);
    }
}
