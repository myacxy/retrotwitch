package net.myacxy.retrotwitch.resources;

import net.myacxy.retrotwitch.Caller;
import net.myacxy.retrotwitch.FluentCaller;
import net.myacxy.retrotwitch.models.BaseModel;

public abstract class BaseSingleResource<R extends BaseSingleResource, M extends BaseModel>
{
    public abstract FluentCaller enqueue(Caller.ResponseListener<M> listener);

    public static abstract class Builder<R extends BaseSingleResource>
    {
        public abstract R build();
    }

    interface Start
    {

    }

    interface End<R extends BaseSingleResource>
    {
        R build();
    }
}
