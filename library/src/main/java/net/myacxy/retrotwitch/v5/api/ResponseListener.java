package net.myacxy.retrotwitch.v5.api;

import net.myacxy.retrotwitch.v5.api.common.RetroTwitchError;

public interface ResponseListener<T> {
    void onSuccess(T t);

    void onError(RetroTwitchError error);
}
