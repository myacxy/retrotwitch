package net.myacxy.retrotwitch.sample.android.rxjava;

import android.app.Application;

import com.orhanobut.logger.AndroidLogTool;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import net.myacxy.retrotwitch.v5.RxCaller;

import okhttp3.logging.HttpLoggingInterceptor;

public class AppApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        RxCaller.getInstance().setClientId("75gzbgqhk0tg6dhjbqtsphmy8sdayrr");
        RxCaller.getInstance().setLoggingLevel(HttpLoggingInterceptor.Level.BODY);

        // TODO: 20.11.2016
//        RetroTwitch.getInstance()
//                .configure()
//                .setClientId("75gzbgqhk0tg6dhjbqtsphmy8sdayrr")
//                .setLogLevel(HttpLoggingInterceptor.Level.BODY)
//                .apply();

        Logger.init()                         // default PRETTYLOGGER or use just init()
            .methodCount(2)                 // default 2
            .logLevel(LogLevel.FULL)        // default LogLevel.FULL
            .methodOffset(0)                // default 0
            .logTool(new AndroidLogTool()); // custom log tool, optional
    }
}
