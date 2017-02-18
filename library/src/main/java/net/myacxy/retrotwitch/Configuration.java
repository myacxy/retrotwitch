package net.myacxy.retrotwitch;

import okhttp3.logging.HttpLoggingInterceptor;

public class Configuration {
    HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.NONE;
    String clientId = "";
    String authToken = "";

    public HttpLoggingInterceptor.Level getLoggingLevel() {
        return level;
    }

    public String getClientId() {
        return clientId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public static class ConfigurationBuilder {

        private Configuration configuration = new Configuration();

        public ConfigurationBuilder setLogLevel(HttpLoggingInterceptor.Level level) {
            configuration.level = level;
            return this;
        }

        public ConfigurationBuilder setClientId(String clientId) {
            configuration.clientId = clientId;
            return this;
        }

        public ConfigurationBuilder setAuthenticationToken(String token) {
            configuration.authToken = token;
            return this;
        }

        public Configuration build() {
            return configuration;
        }
    }
}
