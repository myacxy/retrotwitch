package net.myacxy.retrotwitch.v5;

import net.myacxy.retrotwitch.utils.StringUtil;
import net.myacxy.retrotwitch.v5.api.common.Scope;

import java.util.ArrayList;

import okhttp3.HttpUrl;

public class AuthenticationBuilder {
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
}
