package net.myacxy.retrotwitch.v5.api;

import net.myacxy.retrotwitch.v5.api.common.Error;

public interface ResponseListener<T> {
    void onSuccess(T t);

    void onError(Error error);
}
