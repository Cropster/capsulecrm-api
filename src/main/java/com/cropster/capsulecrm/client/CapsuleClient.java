package com.cropster.capsulecrm.client;

import static com.cropster.capsulecrm.client.Logger.Level.NONE;
import static com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.DeserializationFeature.UNWRAP_ROOT_VALUE;
import static com.fasterxml.jackson.databind.SerializationFeature.WRAP_ROOT_VALUE;

import java.util.concurrent.Executor;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import com.cropster.capsulecrm.client.impl.ModulePartyImpl;
import com.cropster.capsulecrm.client.interceptor.AuthorizationHeaderInterceptor;
import com.cropster.capsulecrm.client.interceptor.ErrorInterceptor;
import com.cropster.capsulecrm.client.interceptor.LogInterceptor;
import com.cropster.capsulecrm.client.interceptor.RateLimitInterceptor;
import com.cropster.capsulecrm.client.interceptor.RateLimitsListener;
import com.cropster.capsulecrm.client.interceptor.UserAgentHeaderInterceptor;

/**
 * @author Casey Link
 * created on 2017-09-19
 */

public class CapsuleClient
{

    private Retrofit retrofit;

    public CapsuleClient(final Builder builder)
    {
        if (builder.accessToken == null)
        {
            throw new IllegalArgumentException("Access token missing.");
        }

        // Retrofit Retrofit
        Retrofit.Builder retrofitBuilder =
                new Retrofit.Builder()
                        .addConverterFactory(JacksonConverterFactory.create(builder.objectMapper))
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .baseUrl(builder.endpoint);

        if (builder.callbackExecutor != null)
            retrofitBuilder.callbackExecutor(builder.callbackExecutor);

        retrofitBuilder.callFactory(defaultCoreCallFactoryBuilder(builder).build());

        this.retrofit = retrofitBuilder.build();

    }

    public static CapsuleClient.Builder builder()
    {
        return new CapsuleClient.Builder();
    }

    public ModuleParty parties()
    {
        return new ModulePartyImpl(retrofit, retrofit.callbackExecutor());
    }

    /**
     * @return default core call factory builder, used by the sdk.
     */
    private OkHttpClient.Builder defaultCoreCallFactoryBuilder(Builder builder)
    {
        final OkHttpClient.Builder okBuilder = new OkHttpClient.Builder()
                .addInterceptor(new AuthorizationHeaderInterceptor(builder.accessToken))
                .addInterceptor(new UserAgentHeaderInterceptor(builder.userAgent))
                .addInterceptor(new ErrorInterceptor());

        if (builder.rateLimitListener != null)
        {
            okBuilder
                    .addInterceptor(
                            new RateLimitInterceptor(builder.rateLimitListener)
                    );
        }

        return setLogger(builder, okBuilder);
    }

    private OkHttpClient.Builder setLogger(Builder builder, OkHttpClient.Builder okBuilder)
    {
        if (builder.logLevel != null)
        {
            switch (builder.logLevel)
            {
                case NONE:
                default:
                    break;
                case BASIC:
                    return okBuilder.addInterceptor(new LogInterceptor(builder.logger));
                case FULL:
                    return okBuilder.addNetworkInterceptor(new LogInterceptor(builder.logger));
            }
        } else
        {
            if (builder.logLevel != NONE)
            {
                throw new IllegalArgumentException(
                        "Cannot log to a null logger. Please set either no logLevel or set a custom Logger");
            }
        }
        return okBuilder;
    }

    public static class Builder
    {
        private String accessToken;
        private String userAgent;
        private String endpoint = Constants.API_ENDPOINT;
        private Executor callbackExecutor;

        private ObjectMapper objectMapper = defaultObjectMapper();
        private RateLimitsListener rateLimitListener;
        private Logger.Level logLevel = NONE;
        private Logger logger;

        public static ObjectMapper defaultObjectMapper()
        {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(ACCEPT_SINGLE_VALUE_AS_ARRAY);
            mapper.enable(UNWRAP_ROOT_VALUE);
            mapper.enable(WRAP_ROOT_VALUE);
            // ObjectMapper does NOT ignore unknown properties by default
            // We thus configure it with the FAIL_ON_UNKNOWN_PROPERTIES feature set to false
            mapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.findAndRegisterModules();
            return mapper;
        }

        public Builder userAgent(String userAgent)
        {
            if (userAgent == null)
            {
                throw new IllegalArgumentException("User agent cannot be null");
            }
            this.userAgent = userAgent;
            return this;
        }

        public Builder accessToken(String accessToken)
        {
            if (accessToken == null)
            {
                throw new IllegalArgumentException("Access token cannot be null");
            }
            this.accessToken = accessToken;
            return this;
        }

        public Builder endpoint(String endpoint)
        {
            if (!StringUtils.endsWith(endpoint, "/"))
            {
                throw new IllegalArgumentException("endpoint url must end in /");
            }
            this.endpoint = endpoint;
            return this;
        }

        public Builder objectMapper(ObjectMapper objectMapper)
        {
            if (objectMapper == null)
            {
                throw new IllegalArgumentException("ObjectMapper cannot be null");
            }
            this.objectMapper = objectMapper;
            return this;
        }

        public Builder rateLimitsListener(RateLimitsListener rateLimitListener)
        {
            this.rateLimitListener = rateLimitListener;
            return this;
        }

        /**
         * Sets the log level for this client.
         *
         * @param logLevel {@link Logger.Level} value
         * @return this {@code Builder} instance
         */
        public Builder logLevel(Logger.Level logLevel)
        {
            if (logLevel == null)
            {
                throw new IllegalArgumentException("Logger.Level cannot be null");
            }
            this.logLevel = logLevel;
            return this;
        }

        public Builder logger(Logger logger)
        {
            this.logger = logger;
            return this;
        }

        public CapsuleClient build()
        {
            Utils.validateNonNull(accessToken, "access token is required");
            Utils.validateNonNull(userAgent, "user agent is required");
            return new CapsuleClient(this);
        }
    }
}
