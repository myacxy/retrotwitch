package net.myacxy.retrotwitch.sample.android.rxjava;

import android.app.Application;

import com.orhanobut.logger.AndroidLogTool;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import net.myacxy.retrotwitch.Configuration.ConfigurationBuilder;
import net.myacxy.retrotwitch.v5.RxRetroTwitch;

import okhttp3.logging.HttpLoggingInterceptor;

public class AppApplication extends Application {

    private static RxRetroTwitch RETRO_TWITCH;
    private static SimpleViewModelLocator VIEW_MODEL_LOCATOR;

    public synchronized static RxRetroTwitch getRetroTwitch() {
        if (RETRO_TWITCH == null) {
            RETRO_TWITCH = new RxRetroTwitch().configure(new ConfigurationBuilder()
                    .setClientId("75gzbgqhk0tg6dhjbqtsphmy8sdayrr")
                    .setLogLevel(HttpLoggingInterceptor.Level.BODY)
                    .build()
            );
        }
        return RETRO_TWITCH;
    }

    public synchronized static SimpleViewModelLocator getViewModelLocator() {
        if (VIEW_MODEL_LOCATOR == null) {
            VIEW_MODEL_LOCATOR = new SimpleViewModelLocator(getRetroTwitch());
        }
        return VIEW_MODEL_LOCATOR;
    }

    @Override

    public void onCreate() {
        super.onCreate();

        Logger.init()                         // default PRETTYLOGGER or use just init()
                .methodCount(2)                 // default 2
                .logLevel(LogLevel.FULL)        // default LogLevel.FULL
                .methodOffset(0)                // default 0
                .logTool(new AndroidLogTool()); // custom log tool, optional
    }
}
