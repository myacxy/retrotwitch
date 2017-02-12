package net.myacxy.retrotwitch.v5.api;

public class SimpleRetroTwitchCallback<I> extends RetroTwitchCallback<I, I> {

    public SimpleRetroTwitchCallback(ResponseListener<I> listener) {
        super(listener);
    }

    @Override
    public I beforeOnSuccess(I i) {
        return i;
    }
}
