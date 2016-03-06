package net.myacxy.retrotwitch.models;

import com.google.gson.annotations.SerializedName;
import net.myacxy.retrotwitch.Caller;
import net.myacxy.retrotwitch.api.Direction;
import net.myacxy.retrotwitch.api.SortBy;
import net.myacxy.retrotwitch.api.TwitchV3Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserFollowsContainer extends BaseModel<UserFollowsContainer.Links>
{
    @SerializedName("_links")
    public Links links;
    @SerializedName("_total")
    public Integer total;
    @SerializedName("follows")
    public List<UserFollow> userFollows = new ArrayList<>();

    public transient String user = null;
    public transient int limit = TwitchV3Service.DEFAULT_LIMIT;
    public transient int offset = 0;
    public transient Direction direction = Direction.DEFAULT;
    public transient SortBy sortBy = SortBy.DEFAULT;

    private UserFollowsContainer()
    {
        // use builder
    }

    @Override
    public Links getLinks()
    {
        return links;
    }

    public void getPrevious(Caller.ResponseListener<UserFollowsContainer> listener)
    {
        if(hasPrevious())
        {
            new CallBuilder(user)
                    .withLimit(limit)
                    .withOffset(offset - limit)
                    .withDirection(direction)
                    .withSortBy(sortBy)
                    .buildAndEnqueue(listener);
        }
    }

    public void getNext(Caller.ResponseListener<UserFollowsContainer> listener)
    {
        if(hasNext())
        {
            new CallBuilder(user)
                    .withLimit(limit)
                    .withOffset(offset + limit)
                    .withDirection(direction)
                    .withSortBy(sortBy)
                    .buildAndEnqueue(listener);
        }
    }

    public boolean hasPrevious()
    {
        return total != null && offset > 0 && offset < total;
    }

    public boolean hasNext()
    {
        return total != null && total < offset + limit;
    }

    public class Links extends BaseModel.Links
    {
        @SerializedName("prev")
        public String prev;
        @SerializedName("next")
        public String next;
    }

    public static class CallBuilder extends Caller.CallBuilder<UserFollowsContainer>
    {
        final String user;
        int limit = TwitchV3Service.DEFAULT_LIMIT;
        int offset = 0;
        Direction direction = Direction.DEFAULT;
        SortBy sortBy = SortBy.DEFAULT;

        public CallBuilder(String user)
        {
            this.user = user;
        }

        public CallBuilder withLimit(int limit) {
            this.limit = limit;
            return this;
        }

        public CallBuilder withOffset(int offset) {
            this.offset = offset;
            return this;
        }

        public CallBuilder withDirection(Direction direction) {
            this.direction = direction;
            return this;
        }

        public CallBuilder withSortBy(SortBy sortBy) {
            this.sortBy = sortBy;
            return this;
        }

        @Override
        public Call<UserFollowsContainer> build()
        {
            return build(user, limit, offset, direction, sortBy);
        }

        private Call<UserFollowsContainer> build(String user, int limit, int offset, Direction direction, SortBy sortBy)
        {
            return Caller.getInstance()
                    .getService()
                    .getUserFollows(user, limit, offset, direction, sortBy);
        }

        @Override
        public UserFollowsContainer buildAndExecute() throws IOException
        {
            UserFollowsContainer userFollowsContainer = build().execute().body();
            assignCallParameters(userFollowsContainer);
            return userFollowsContainer;
        }

        @Override
        public void buildAndEnqueue(final Caller.ResponseListener<UserFollowsContainer> listener)
        {
            build().enqueue(new Callback<UserFollowsContainer>()
            {
                @Override
                public void onResponse(Call<UserFollowsContainer> call, Response<UserFollowsContainer> response)
                {
                    UserFollowsContainer userFollowsContainer = response.body();
                    assignCallParameters(userFollowsContainer);
                    listener.onSuccess(userFollowsContainer);
                }

                @Override
                public void onFailure(Call<UserFollowsContainer> call, Throwable t)
                {
                    listener.onError();
                }
            });
        }

        public void buildAndGetAll(final Caller.ResponseListener<List<UserFollow>> listener)
        {
            getAllUserFollowsRecursively(user, limit, offset, direction, sortBy, listener, new ArrayList<UserFollow>(TwitchV3Service.MAX_LIMIT));
        }

        private void getAllUserFollowsRecursively(
                final String user,
                final int limit,
                final int offset,
                final Direction direction,
                final SortBy sortBy,
                final Caller.ResponseListener<List<UserFollow>> listener,
                final List<UserFollow> cache)
        {
            build(user, limit, offset, direction, sortBy).enqueue(new Callback<UserFollowsContainer>()
            {
                @Override
                public void onResponse(Call<UserFollowsContainer> call, Response<UserFollowsContainer> response)
                {
                    cache.addAll(response.body().userFollows);
                    if(response.body().total > offset + limit)
                    {
                        getAllUserFollowsRecursively(user, limit, offset + limit, direction, sortBy, listener, cache);
                    }
                    else
                    {
                        listener.onSuccess(cache);
                    }
                }

                @Override
                public void onFailure(Call<UserFollowsContainer> call, Throwable t)
                {
                    listener.onError();
                }
            });
        }

        private void assignCallParameters(UserFollowsContainer userFollowsContainer)
        {
            userFollowsContainer.user = user;
            userFollowsContainer.limit = limit;
            userFollowsContainer.offset = offset;
            userFollowsContainer.direction = direction;
            userFollowsContainer.sortBy = sortBy;
        }
    }
}
