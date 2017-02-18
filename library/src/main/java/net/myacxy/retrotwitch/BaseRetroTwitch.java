package net.myacxy.retrotwitch;

import net.myacxy.retrotwitch.utils.StringUtil;
import net.myacxy.retrotwitch.v5.AuthenticationBuilder;
import net.myacxy.retrotwitch.v5.api.BaseCaller;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public abstract class BaseRetroTwitch<SELF extends BaseRetroTwitch<SELF>> {

    //<editor-fold desc="Members">
    protected OkHttpClient client;
    protected HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
    protected Configuration configuration = new Configuration();
    //</editor-fold>

    //<editor-fold desc="Constructor">
    public BaseRetroTwitch() {
        client = createClient(configuration);
    }
    //</editor-fold>

    //<editor-fold desc="Public Methods">
    public AuthenticationBuilder authenticate() {
        return new AuthenticationBuilder();
    }

    public SELF configure(Configuration configuration) {
        this.configuration = configuration;
        // noinspection unchecked
        return (SELF) this;
    }
    //</editor-fold>

    //<editor-fold desc="Abstract Methods">
    public abstract BaseCaller<?> channels();

    public abstract BaseCaller<?> users();

    public abstract BaseCaller<?> streams();
    //</editor-fold>

    //<editor-fold desc="Private Methods">
    private OkHttpClient createClient(final Configuration configuration) {
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder builder = chain.request()
                                .newBuilder()
                                .header("Accept", "application/vnd.twitchtv.v5+json")
                                .header("Client-ID", configuration.getClientId());
                        if (StringUtil.isEmpty(configuration.getAuthToken())) {
                            builder.header("Authorization", String.format("OAuth %s", configuration.getAuthToken()));
                        }
                        return chain.proceed(builder.build());
                    }
                })
                .addInterceptor(httpLoggingInterceptor.setLevel(configuration.level))
                .build();
    }
    //</editor-fold>
}
