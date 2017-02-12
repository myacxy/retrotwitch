package net.myacxy.retrotwitch.v5;

import net.myacxy.retrotwitch.utils.StringUtil;
import net.myacxy.retrotwitch.v5.api.channels.ChannelsCaller;
import net.myacxy.retrotwitch.v5.api.common.Scope;
import net.myacxy.retrotwitch.v5.api.streams.StreamsCaller;
import net.myacxy.retrotwitch.v5.api.users.UsersCaller;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class RetroTwitch {

    private static RetroTwitch INSTANCE = new RetroTwitch();

    //<editor-fold desc="Member">
    private OkHttpClient client;
    private ConfigurationBuilder configuration = new ConfigurationBuilder();
    private ChannelsCaller channels;
    private UsersCaller users;
    private StreamsCaller streams;
    //</editor-fold>

    private RetroTwitch() {
        client = createClient(configuration);
        channels = new ChannelsCaller(client);
        users = new UsersCaller(client);
        streams = new StreamsCaller(client);
    }

    public static RetroTwitch getInstance() {
        return INSTANCE;
    }

    //<editor-fold desc="Public Methods">
    public AuthenticationBuilder authenticate() {
        return new AuthenticationBuilder();
    }

    public ConfigurationBuilder configure() {
        return configuration;
    }

    public ChannelsCaller channels() {
        return channels;
    }

    public UsersCaller users() {
        return users;
    }

    public StreamsCaller streams() {
        return streams;
    }
    //</editor-fold>

    //<editor-fold desc="Private Methods">
    private OkHttpClient createClient(final ConfigurationBuilder configuration) {
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .header("Accept", "application/vnd.twitchtv.v5+json")
                                .header("Client-ID", configuration.clientId)
                                .header("Authentication", configuration.authToken)
                                .build();
                        return chain.proceed(request);
                    }
                })
                .addInterceptor(configuration.httpLoggingInterceptor)
                .build();
    }
    //</editor-fold>

    //<editor-fold desc="Inner Classes">
    public static class AuthenticationBuilder {
        Scope[] scopes = new Scope[0];
        String clientId = "";
        String redirectUri = "http://localhost";

        public AuthenticationBuilder setClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public AuthenticationBuilder setScopes(Scope... scopes) {
            this.scopes = scopes;
            return this;
        }

        public AuthenticationBuilder setRedirectUri(String redirectUri) {
            this.redirectUri = redirectUri;
            return this;
        }

        public RetroTwitch build(Callback callback) {
            callback.authenticate(buildUrl());
            return RetroTwitch.INSTANCE;
        }

        public String buildUrl() {
            ArrayList<String> scopeStrings = new ArrayList<>(scopes.length);
            for (Scope scope : scopes) {
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

        public interface Callback {
            void authenticate(String url);
        }
    }

    public static class ConfigurationBuilder {
        private HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.NONE;
        private HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        private String clientId = "";
        private String tmpClientId = "";
        private String authToken = "";
        private String tmpAuthToken = "";

        public ConfigurationBuilder setLogLevel(HttpLoggingInterceptor.Level level) {
            this.level = level;
            return this;
        }

        public ConfigurationBuilder setClientId(String clientId) {
            tmpClientId = clientId;
            return this;
        }

        public ConfigurationBuilder setAuthenticationToken(String token) {
            tmpAuthToken = token;
            return this;
        }

        public RetroTwitch apply() {
            httpLoggingInterceptor.setLevel(level);
            clientId = tmpClientId;
            authToken = tmpAuthToken;
            return RetroTwitch.INSTANCE;
        }
    }
    //</editor-fold>
}
