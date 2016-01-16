package net.myacxy.retrotwitch;

import net.myacxy.retrotwitch.responses.GetFollowsResponse;
import net.myacxy.retrotwitch.responses.GetStreamResponse;
import net.myacxy.retrotwitch.responses.GetStreamsResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface TwitchV3Service
{
    String BASE_URL = "https://api.twitch.tv/kraken/";

    @GET("users/{user}/follows/channels")
    Call<GetFollowsResponse> getUserFollows(@Path("user") String user);

    /**
     *
     * @param user optional
     * @param limit optional
     * @param offset optional
     * @param direction optional
     * @param sortBy optional
     * @return
     */
    @GET("users/{user}/follows/channels")
    Call<GetFollowsResponse> getUserFollows(
            @Path("user") String user,
            @Query("limit") int limit,
            @Query("offset") int offset,
            @Query("direction") Direction direction,
            @Query("sortby") SortBy sortBy);

    @GET("streams/{channel}")
    Call<GetStreamResponse> getStream(@Path("channel") String channel);

    /**
     *
     * @param game optional
     * @param channelOrChannels optional (comma separated if multiple)
     * @param limit optional
     * @param offset optional
     * @param client_id optional
     * @param streamType optional
     * @return
     */
    @GET("streams")
    Call<GetStreamsResponse> getStreams(
            @Query("game") String game,
            @Query("channel") String channelOrChannels,
            @Query("limit") Integer limit,
            @Query("offset") Integer offset,
            @Query("client_id") String client_id,
            @Query("stream_type") StreamType streamType);

    enum Direction
    {
        DEFAULT("desc"),
        ASCENDING("asc"),
        DESCENDING("desc");

        private String mDirection;

        Direction(String direction)
        {
            mDirection = direction;
        }

        @Override
        public String toString()
        {
            return mDirection;
        }
    }

    enum SortBy
    {
        DEFAULT("created_at"),
        CREATED_AT("created_at"),
        LAST_BROADCAST("last_broadcast"),
        LOGIN("login");

        private String mSortBy;

        SortBy(String sortBy)
        {
            mSortBy = sortBy;
        }

        @Override
        public String toString()
        {
            return mSortBy;
        }
    }

    enum StreamType
    {
        DEFAULT("all"),
        ALL("all"),
        PLAYLIST("playlist"),
        LIVE("live");

        private String mStreamType;

        StreamType(String streamType)
        {
            mStreamType = streamType;
        }

        @Override
        public String toString()
        {
            return mStreamType;
        }
    }
}
