package net.myacxy.retrotwitch;

import okhttp3.logging.HttpLoggingInterceptor;

public class RetroTwitch
{

    private RetroTwitch()
    {
        // use builder
    }

    public static ConfigurationBuilder configure()
    {
        return new ConfigurationBuilder();
    }

    public FluentCaller getCaller()
    {
        return FluentCaller.INSTANCE;
    }

    protected static class ConfigurationBuilder
    {
        private HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.NONE;

        public ConfigurationBuilder setLogLevel(HttpLoggingInterceptor.Level level)
        {
            this.level = level;
            return this;
        }

        public RetroTwitch apply()
        {
            Caller.newInstance(level);
            return new RetroTwitch();
        }
    }
}
