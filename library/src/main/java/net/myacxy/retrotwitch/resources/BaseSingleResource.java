package net.myacxy.retrotwitch.resources;

import net.myacxy.retrotwitch.Caller;
import net.myacxy.retrotwitch.FluentCaller;
import net.myacxy.retrotwitch.models.BaseModel;

import java.io.Serializable;

public abstract class BaseSingleResource<R extends BaseSingleResource, M extends BaseModel> implements Serializable
{
    public abstract FluentCaller enqueue(Caller.ResponseListener<M> listener);

    public static abstract class Builder<R extends BaseSingleResource> implements Serializable
    {
        public abstract R build();
    }
}
