package net.myacxy.retrotwitch.v5.helpers;

import com.google.gson.JsonSyntaxException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import net.myacxy.retrotwitch.v5.api.common.RetroTwitchError;

public class RxRetroTwitchErrorFactory extends RetroTwitchErrorFactory {

    public static RetroTwitchError fromJson(String json) {
        try {
            return GSON.fromJson(json, RetroTwitchError.class);
        } catch (JsonSyntaxException e) {
            return unexpected(e);
        }
    }

    public static RetroTwitchError fromHttpException(HttpException exception) {
        try {
            RetroTwitchError error = fromJson(exception.response().errorBody().string());
            error.setCause(exception);
            return error;
        } catch (Exception e) {
            return unexpected(exception.code(), exception.message(), e);
        }
    }

    public static RetroTwitchError fromThrowable(Throwable throwable) {
        if (throwable instanceof HttpException) {
            return fromHttpException((HttpException) throwable);
        }
        return unexpected(throwable);
    }
}
