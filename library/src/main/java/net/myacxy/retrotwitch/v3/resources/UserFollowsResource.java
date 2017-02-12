package net.myacxy.retrotwitch.v3.resources;

import net.myacxy.retrotwitch.v3.Caller;
import net.myacxy.retrotwitch.v3.FluentCaller;
import net.myacxy.retrotwitch.v3.api.TwitchV3Service;
import net.myacxy.retrotwitch.v3.helpers.ErrorFactory;
import net.myacxy.retrotwitch.v3.models.Direction;
import net.myacxy.retrotwitch.v3.models.Error;
import net.myacxy.retrotwitch.v3.models.SortBy;
import net.myacxy.retrotwitch.v3.models.UserFollow;
import net.myacxy.retrotwitch.v3.models.UserFollowsContainer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFollowsResource extends BaseMultiResource<UserFollowsResource, List<UserFollow>>
{
    private BuilderBase<? extends BuilderBase> builder;

    private UserFollowsResource(BuilderBase<? extends BuilderBase> builder)
    {
        this.builder = builder;
    }

    @Override
    public FluentCaller enqueue(final Caller.ResponseListener<List<UserFollow>> listener)
    {
        if (builder instanceof AllBuilder)
        {
            getAllUserFollowsRecursively(
                    builder.user,
                    builder.limit,
                    builder.offset,
                    ((AllBuilder) builder).maximum,
                    builder.direction,
                    builder.sortBy,
                    listener,
                    new ArrayList<UserFollow>(TwitchV3Service.MAX_LIMIT));
        } else if (builder instanceof LimitedBuilder)
        {
            createCall(builder.user, builder.limit, builder.offset, builder.direction, builder.sortBy)
                    .enqueue(new Callback<UserFollowsContainer>()
                    {
                        @Override
                        public void onResponse(Call<UserFollowsContainer> call, Response<UserFollowsContainer> response)
                        {
                            Error error = ErrorFactory.fromResponse(response);
                            if (error == null)
                            {
                                UserFollowsContainer userFollowsContainer = response.body();
                                builder.total = userFollowsContainer.total;
                                listener.onSuccess(userFollowsContainer.userFollows);
                            } else
                            {
                                listener.onError(error);
                            }
                        }

                        @Override
                        public void onFailure(Call<UserFollowsContainer> call, Throwable t)
                        {
                            listener.onError(ErrorFactory.fromThrowable(t));
                        }
                    });
        }

        return FluentCaller.getInstance();
    }

    public void getPrevious(Caller.ResponseListener<List<UserFollow>> listener)
    {
        if (hasPrevious())
        {
            builder.offset = builder.offset - builder.limit;
            enqueue(listener);
            return;
        }
        // TODO
//        listener.onError();
    }

    public void getNext(Caller.ResponseListener<List<UserFollow>> listener)
    {
        if (hasNext())
        {
            builder.offset = builder.offset + builder.limit;
            enqueue(listener);
            return;
        }
        // TODO
//        listener.onError();
    }

    private boolean hasPrevious()
    {
        return builder.total != null && builder.offset > 0 && builder.offset < builder.total;
    }

    private boolean hasNext()
    {
        return builder.total != null && builder.total > builder.offset + builder.limit;
    }

    public Integer getTotal()
    {
        return builder.total;
    }

    private Call<UserFollowsContainer> createCall(String user, int limit, int offset, Direction direction, SortBy sortBy)
    {
        return Caller.getInstance()
                .getService()
                .getUserFollows(user, limit, offset, direction, sortBy);
    }

    private void getAllUserFollowsRecursively(
            final String user,
            final int limit,
            final int offset,
            final Integer maximum,
            final Direction direction,
            final SortBy sortBy,
            final Caller.ResponseListener<List<UserFollow>> listener,
            final List<UserFollow> cache)
    {
        createCall(user, limit, offset, direction, sortBy)
                .enqueue(new Callback<UserFollowsContainer>()
                {
                    @Override
                    public void onResponse(Call<UserFollowsContainer> call, Response<UserFollowsContainer> response)
                    {
                        cache.addAll(response.body().userFollows);
                        builder.total = response.body().total;
                        // TODO maximum
                        if (cache.size() != response.body().total && response.body().total > offset + limit)
                        {
                            getAllUserFollowsRecursively(user, limit, offset + limit, maximum, direction, sortBy, listener, cache);
                        } else
                        {
                            listener.onSuccess(cache);
                        }
                    }

                    @Override
                    public void onFailure(Call<UserFollowsContainer> call, Throwable t)
                    {
                        listener.onError(ErrorFactory.fromThrowable(t));
                    }
                });
    }

    private static abstract class BuilderBase<B extends BuilderBase> implements Serializable
    {
        final String user;
        Integer total = null;
        int limit = TwitchV3Service.DEFAULT_LIMIT;
        int offset = 0;
        Direction direction = Direction.DEFAULT;
        SortBy sortBy = SortBy.DEFAULT;

        public BuilderBase(String user)
        {
            this.user = user;
        }

        public B withLimit(int limit)
        {
            this.limit = limit;
            return (B) this;
        }

        public B withOffset(int offset)
        {
            this.offset = offset;
            return (B) this;
        }

        public B withDirection(Direction direction)
        {
            this.direction = direction;
            return (B) this;
        }

        public B withSortBy(SortBy sortBy)
        {
            this.sortBy = sortBy;
            return (B) this;
        }

        public UserFollowsResource build()
        {
            return new UserFollowsResource(this);
        }
    }

    public static class LimitedBuilder extends BuilderBase<LimitedBuilder>
    {
        public LimitedBuilder(String user)
        {
            super(user);
        }
    }

    public static class AllBuilder extends BuilderBase<AllBuilder>
    {
        Integer maximum;

        public AllBuilder(String user)
        {
            super(user);
        }

        public AllBuilder withMaximum(int maximum)
        {
            this.maximum = maximum;
            return this;
        }
    }

    public static class Builder
    {
        private String user;

        public Builder(String user)
        {
            this.user = user;
        }

        public AllBuilder all()
        {
            return new AllBuilder(user);
        }

        public LimitedBuilder limited()
        {
            return new LimitedBuilder(user);
        }
    }
}
