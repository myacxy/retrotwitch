package net.myacxy.retrotwitch.sample.android;

import android.app.Application;

import net.myacxy.retrotwitch.Configuration.ConfigurationBuilder;
import net.myacxy.retrotwitch.v5.RetroTwitch;

import okhttp3.logging.HttpLoggingInterceptor;

public class AppApplication extends Application {

    private static RetroTwitch RETRO_TWITCH;

    public static RetroTwitch getRetroTwitch() {
        if (RETRO_TWITCH == null) {
            RETRO_TWITCH = new RetroTwitch()
                    .configure(new ConfigurationBuilder()
                            .setClientId("75gzbgqhk0tg6dhjbqtsphmy8sdayrr")
                            .setLogLevel(HttpLoggingInterceptor.Level.BASIC)
                            .build()
                    );
        }
        return RETRO_TWITCH;
    }
}
