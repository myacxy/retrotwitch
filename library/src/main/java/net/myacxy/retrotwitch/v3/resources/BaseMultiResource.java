package net.myacxy.retrotwitch.v3.resources;

import net.myacxy.retrotwitch.v3.Caller;
import net.myacxy.retrotwitch.v3.FluentCaller;
import net.myacxy.retrotwitch.v3.models.BaseModel;

import java.io.Serializable;
import java.util.List;

public abstract class BaseMultiResource<R extends BaseMultiResource, L extends List<? extends BaseModel>> implements Serializable
{
    public abstract FluentCaller enqueue(Caller.ResponseListener<L> listener);
}
