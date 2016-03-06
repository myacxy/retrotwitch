package net.myacxy.retrotwitch.resources;

import net.myacxy.retrotwitch.Caller;
import net.myacxy.retrotwitch.FluentCaller;
import net.myacxy.retrotwitch.models.BaseModel;

import java.util.List;

public abstract class BaseMultiResource<R extends BaseMultiResource, L extends List<? extends BaseModel>>
{
    public abstract FluentCaller enqueue(Caller.ResponseListener<L> listener);
}
