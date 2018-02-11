package net.myacxy.retrotwitch.v5.api.channels;

import net.myacxy.retrotwitch.Configuration;
import net.myacxy.retrotwitch.utils.StringUtil;
import net.myacxy.retrotwitch.v5.RxRetroTwitch;
import net.myacxy.retrotwitch.v5.api.common.Sort;
import net.myacxy.retrotwitch.v5.api.users.SimpleUsersResponse;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;

/**
 * Created by Johannes on 21.07.2017.
 */
public class RxChannelsCallerTest {

    RxRetroTwitch rrt;

    @Before
    public void setUp() throws Exception {
        Configuration configuration = new Configuration.ConfigurationBuilder()
                .setLogLevel(HttpLoggingInterceptor.Level.BODY)
                .setClientId("75gzbgqhk0tg6dhjbqtsphmy8sdayrr")
                .build();
        rrt = new RxRetroTwitch().configure(configuration);
    }

    @Test
    public void getVideos() throws Exception {
        Response<SimpleUsersResponse> response1 = rrt.users()
                .translateUserNameToUserId("reckful")
                .blockingGet();

        long id = response1.body()
                .getUsers()
                .get(0)
                .getId();

        Response<GetVideosResponse> response2 = rrt.channels()
                .getVideos(id, BroadcastType.DEFAULT, null, Sort.DEFAULT, 100, 0)
                .blockingGet();

        Response<GetVideosResponse> response3 = rrt.channels()
                .getVideos(id, BroadcastType.DEFAULT, null, Sort.DEFAULT, 100, 100)
                .blockingGet();

        List<Video> videos = response2.body().getVideos();
        String s = listToSeparatedString(videos, "\n", (v) -> {
            long hours = v.getLength() / 3600;
            long minutes = (v.getLength() % 3600) / 60;
            long seconds = v.getLength() % 60;
            String length = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            return new StringBuilder(v.getTitle())
                    .append(" (")
                    .append(length)
                    .append(") ")
                    .append(v.getUrl())
                    .toString();
        });
        System.out.println(s);

        videos = response3.body().getVideos();
        s = listToSeparatedString(videos, "\n", (v) -> {
            long hours = v.getLength() / 3600;
            long minutes = (v.getLength() % 3600) / 60;
            long seconds = v.getLength() % 60;
            String length = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            return new StringBuilder(v.getTitle())
                    .append(" ")
                    .append("(")
                    .append(length)
                    .append(")")
                    .append(" ")
                    .append(v.getUrl())
                    .toString();
        });
        System.out.println(s);
    }

    private static <T> String listToSeparatedString(List<T> list, String separator, Function<T, String> function) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        List<String> result = new ArrayList<>(list.size());
        for (T t : list) {
            result.add(function.apply(t));
        }
        return StringUtil.joinStrings(result, separator);
    }
}