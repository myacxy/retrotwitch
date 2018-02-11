package net.myacxy.retrotwitch.v5.helpers;

import com.google.gson.JsonSyntaxException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import net.myacxy.retrotwitch.v5.api.common.Error;

import java.io.IOException;

public class RxErrorFactory extends ErrorFactory {

    public static Error fromJson(String json) {
        try {
            return GSON.fromJson(json, Error.class);
        } catch (JsonSyntaxException e) {
            return unexpected(-1, e.getMessage());
        }
    }

    public static Error fromHttpException(HttpException exception) {
        try {
            return fromJson(exception.response().errorBody().string());
        } catch (IOException e) {
            return unexpected(-1, exception.message());
        }
    }

    public static Error fromThrowable(Throwable throwable) {
        if (throwable instanceof HttpException) {
            return fromHttpException((HttpException) throwable);
        }
        return unexpected(-1, throwable.getMessage());
    }
}
