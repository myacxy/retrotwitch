package net.myacxy.retrotwitch.v5.api;

import net.myacxy.retrotwitch.v5.api.common.Error;
import net.myacxy.retrotwitch.v5.helpers.ErrorFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class RetroTwitchCallback<I, O> implements Callback<I>, ResponseListener<O> {

    private ResponseListener<O> listener;

    public RetroTwitchCallback(ResponseListener<O> listener) {
        this.listener = listener;
    }

    @Override
    public void onResponse(Call<I> call, Response<I> response) {
        Error error = ErrorFactory.fromResponse(response);
        if (error == null) {
            onSuccess(beforeOnSuccess(response.body()));
        } else {
            onError(error);
        }
    }

    @Override
    public void onFailure(Call<I> call, Throwable t) {
        onError(ErrorFactory.fromThrowable(t));
    }

    @Override
    public void onSuccess(O o) {
        listener.onSuccess(o);
    }

    @Override
    public void onError(Error error) {
        listener.onError(error);
    }

    public abstract O beforeOnSuccess(I i);
}
