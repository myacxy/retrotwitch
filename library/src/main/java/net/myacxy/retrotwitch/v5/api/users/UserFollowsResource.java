package net.myacxy.retrotwitch.v5.api.users;

import net.myacxy.retrotwitch.v5.RetroTwitch;
import net.myacxy.retrotwitch.v5.api.ResponseListener;
import net.myacxy.retrotwitch.v5.api.common.Direction;
import net.myacxy.retrotwitch.v5.api.common.Error;
import net.myacxy.retrotwitch.v5.api.common.SortBy;
import net.myacxy.retrotwitch.v5.api.common.TwitchConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserFollowsResource implements Serializable {
    private BuilderBase<? extends BuilderBase> builder;

    private UserFollowsResource(BuilderBase<? extends BuilderBase> builder) {
        this.builder = builder;
    }

    public void enqueue(RetroTwitch retroTwitch, final ResponseListener<List<UserFollow>> listener) {
        // TODO: 14.02.2017 builder.enqueue?

        if (builder instanceof AllBuilder) {
            getAllUserFollowsRecursively(
                    retroTwitch,
                    builder.userId,
                    builder.limit,
                    builder.offset,
                    ((AllBuilder) builder).maximum,
                    builder.direction,
                    builder.sortBy,
                    listener,
                    new ArrayList<UserFollow>(TwitchConstants.MAX_LIMIT));
        } else if (builder instanceof LimitedBuilder) {
            retroTwitch.users().getUserFollows(builder.userId, builder.limit, builder.offset, builder.direction, builder.sortBy, new ResponseListener<UserFollowsResponse>() {
                @Override
                public void onSuccess(UserFollowsResponse userFollowsResponse) {
                    builder.total = userFollowsResponse.getTotal();
                    listener.onSuccess(userFollowsResponse.getUserFollows());
                }

                @Override
                public void onError(Error error) {
                    listener.onError(error);
                }
            });
        }
    }

    public void getPrevious(RetroTwitch retroTwitch, ResponseListener<List<UserFollow>> listener) {
        if (hasPrevious()) {
            builder.offset = builder.offset - builder.limit;
            enqueue(retroTwitch, listener);
            return;
        }
//        listener.onError();
    }

    public void getNext(RetroTwitch retroTwitch, ResponseListener<List<UserFollow>> listener) {
        if (hasNext()) {
            builder.offset = builder.offset + builder.limit;
            enqueue(retroTwitch, listener);
            return;
        }
        // TODO
//        listener.onError();
    }

    public boolean hasPrevious() {
        return builder.total != null && builder.offset > 0 && builder.offset < builder.total;
    }

    public boolean hasNext() {
        return builder.total != null && builder.total > builder.offset + builder.limit;
    }

    public Integer getTotal() {
        return builder.total;
    }

    private void getAllUserFollowsRecursively(
            final RetroTwitch retroTwitch,
            final long userId,
            final int limit,
            final int offset,
            final Integer maximum,
            final Direction direction,
            final SortBy sortBy,
            final ResponseListener<List<UserFollow>> listener,
            final List<UserFollow> cache) {
        retroTwitch.users().getUserFollows(userId, limit, offset, direction, sortBy, new ResponseListener<UserFollowsResponse>() {
            @Override
            public void onSuccess(UserFollowsResponse userFollowsResponse) {
                cache.addAll(userFollowsResponse.getUserFollows());
                builder.total = userFollowsResponse.getTotal();
                // TODO maximum
                if (userFollowsResponse.getTotal() > offset + limit) {
                    getAllUserFollowsRecursively(retroTwitch, userId, limit, offset + limit, maximum, direction, sortBy, listener, cache);
                } else {
                    listener.onSuccess(cache);
                }
            }

            @Override
            public void onError(Error error) {
                listener.onError(error);
            }
        });
    }

    public static class Builder {
        private long userId;

        public Builder(long userId) {
            this.userId = userId;
        }

        public AllBuilder all() {
            return new AllBuilder(userId);
        }

        public LimitedBuilder limited() {
            return new LimitedBuilder(userId);
        }
    }

    private static abstract class BuilderBase<B extends BuilderBase> implements Serializable {
        final long userId;
        Integer total = null;
        int limit = TwitchConstants.DEFAULT_LIMIT;
        int offset = 0;
        Direction direction = TwitchConstants.DEFAULT_DIRECTION;
        SortBy sortBy = TwitchConstants.DEFAULT_SORT_BY;

        public BuilderBase(long userId) {
            this.userId = userId;
        }

        public B withLimit(int limit) {
            this.limit = limit;
            return (B) this;
        }

        public B withOffset(int offset) {
            this.offset = offset;
            return (B) this;
        }

        public B withDirection(Direction direction) {
            this.direction = direction;
            return (B) this;
        }

        public B withSortBy(SortBy sortBy) {
            this.sortBy = sortBy;
            return (B) this;
        }

        public UserFollowsResource build() {
            return new UserFollowsResource(this);
        }
    }

    public static class LimitedBuilder extends BuilderBase<LimitedBuilder> {
        public LimitedBuilder(long userId) {
            super(userId);
        }
    }

    public static class AllBuilder extends BuilderBase<AllBuilder> {
        Integer maximum;

        public AllBuilder(long userId) {
            super(userId);
        }

        public AllBuilder withMaximum(int maximum) {
            this.maximum = maximum;
            return this;
        }
    }
}
