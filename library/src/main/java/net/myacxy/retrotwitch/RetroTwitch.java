package net.myacxy.retrotwitch;

import net.myacxy.retrotwitch.api.Scope;
import net.myacxy.retrotwitch.utils.StringUtil;

import java.util.ArrayList;

import okhttp3.HttpUrl;
import okhttp3.logging.HttpLoggingInterceptor;

public class RetroTwitch
{
    private static RetroTwitch INSTANCE = new RetroTwitch();

    RetroTwitch() {

    }

    public static RetroTwitch getInstance() {
        return INSTANCE;
    }

    public AuthenticationBuilder authenticate()
    {
        return new AuthenticationBuilder();
    }

    public ConfigurationBuilder configure()
    {
        return new ConfigurationBuilder();
    }

    public Caller getDefault()
    {
        return Caller.getInstance();
    }

    public FluentCaller getFluent()
    {
        return FluentCaller.getInstance();
    }

    public static class AuthenticationBuilder
    {
        Scope[] scopes = new Scope[0];
        String clientId = "";
        String redirectUri = "http://localhost";

        public AuthenticationBuilder setClientId(String clientId)
        {
            this.clientId = clientId;
            return this;
        }

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
            return RetroTwitch.INSTANCE;
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

    public static class ConfigurationBuilder
    {
        private HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.NONE;
        private String clientId = "";

        public ConfigurationBuilder setLogLevel(HttpLoggingInterceptor.Level level)
        {
            this.level = level;
            return this;
        }

        public ConfigurationBuilder setClientId(String clientId)
        {
            this.clientId = clientId;
            return this;
        }

        public RetroTwitch apply()
        {
            RetroTwitch.INSTANCE.getDefault().setLoggingLevel(level);
            RetroTwitch.INSTANCE.getDefault().setClientId(clientId);
            return RetroTwitch.INSTANCE;
        }
    }
}
