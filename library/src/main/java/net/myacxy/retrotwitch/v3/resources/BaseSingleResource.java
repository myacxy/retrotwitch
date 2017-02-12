package net.myacxy.retrotwitch.v3.resources;

import net.myacxy.retrotwitch.v3.Caller;
import net.myacxy.retrotwitch.v3.FluentCaller;
import net.myacxy.retrotwitch.v3.models.BaseModel;

import java.io.Serializable;

public abstract class BaseSingleResource<R extends BaseSingleResource, M extends BaseModel> implements Serializable
{
    public abstract FluentCaller enqueue(Caller.ResponseListener<M> listener);

    public static abstract class Builder<R extends BaseSingleResource> implements Serializable
    {
        public abstract R build();
    }
}
