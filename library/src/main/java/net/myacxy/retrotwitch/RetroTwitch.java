package net.myacxy.retrotwitch;

import net.myacxy.retrotwitch.api.Scope;
import net.myacxy.retrotwitch.api.TwitchV3Service;
import net.myacxy.retrotwitch.utils.StringUtil;
import okhttp3.HttpUrl;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;

import java.util.ArrayList;

public enum RetroTwitch
{
    INSTANCE;

    public AuthenticationBuilder authenticate()
    {
        return new AuthenticationBuilder();
    }

    public ConfigurationBuilder configure()
    {
        return new ConfigurationBuilder();
    }

    public FluentCaller getCaller()
    {
        return FluentCaller.INSTANCE;
    }

    protected static class AuthenticationBuilder
    {
        Scope[] scopes = new Scope[0];
        String clientId = "75gzbgqhk0tg6dhjbqtsphmy8sdayrr";
        String redirectUri = "http://localhost";

        public AuthenticationBuilder addScopes(Scope... scopes)
        {
            this.scopes = scopes;
            return this;
        }

        public AuthenticationBuilder setRedirectUri(String redirectUri)
        {
            this.redirectUri = redirectUri;
            return this;
        }

        public RetroTwitch build(Callback callback)
        {
            callback.authenticate(buildUrl());
            return INSTANCE;
        }

        public String buildUrl()
        {
            ArrayList<String> scopeStrings = new ArrayList<>(scopes.length);
            for (Scope scope : scopes)
            {
                scopeStrings.add(scope.toString());
            }
            String scopesString = StringUtil.joinStrings(scopeStrings, "+");

            return new HttpUrl.Builder()
                    .host("api.twitch.tv")
                    .scheme("https")
                    .encodedPath("/kraken/oauth2/authorize")
                    .addQueryParameter("response_type", "token")
                    .addQueryParameter("redirect_uri", redirectUri)
                    .addQueryParameter("client_id", clientId)
                    .addQueryParameter("scope", scopesString)
                    .build()
                    .toString();
        }

        public interface Callback
        {
            void authenticate(String url);
        }
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
            return INSTANCE;
        }
    }
}
