package com.cropster.capsulecrm.client.model;

import java.util.List;
import java.util.Map;

/**
 * For requests using Bearer Token Authentication, each Capsule user can make up to 4,000 requests per hour.
 * https://developer.capsulecrm.com/v2/overview/handling-api-responses
 */
public class RateLimits
{
    private long limit;
    private long remaining;
    private long reset;

    /**
     * @return The maximum number of requests that the user is allowed to make per hour.
     */
    public long getLimit()
    {
        return limit;
    }

    /**
     * @return The number of requests remaining in the current rate limit window.
     */
    public long getRemaining()
    {
        return remaining;
    }

    /**
     * @return The time at which the current rate limit window resets in UTC epoch seconds.
     */
    public long getReset()
    {
        return reset;
    }

    public interface Parser
    {
        public static String HEADER_RATELIMIT_LIMIT = "X-RateLimit-Limit";
        public static String HEADER_RATELIMIT_REMAINING = "X-RateLimit-Remaining";
        public static String HEADER_RATELIMIT_RESET = "X-RateLimit-Reset";

        RateLimits parse(Map<String, List<String>> headers);
    }

    /**
     * Default parser for rate limits, comming from HTTP Headers.
     */
    public static class DefaultParser implements Parser
    {
        /**
         * Analyzes and converts a map of given headers to its rate limits.
         *
         * @param headers map of headers returned in an HTTP response from Contentful.
         * @return a RateLimits object, filled with available rate limit header information.
         */
        @Override
        public RateLimits parse(Map<String, List<String>> headers)
        {
            return new Builder()
                    .setLimit(findLimit(headers, HEADER_RATELIMIT_LIMIT))
                    .setRemaining(findLimit(headers, HEADER_RATELIMIT_REMAINING))
                    .setReset(findLimit(headers, HEADER_RATELIMIT_RESET))
                    .build();
        }

        private int findLimit(Map<String, List<String>> headers, String key)
        {
            if (headers.containsKey(key) && headers.get(key) != null)
            {
                try
                {
                    return Integer.parseInt(headers.get(key).get(0));
                } catch (NumberFormatException e)
                {
                    return -1;
                }
            } else
            {
                return 0;
            }
        }
    }

    /**
     * Builder for the rate limits
     */
    static class Builder
    {
        RateLimits limits;

        /**
         * Create a new builder.
         */
        public Builder()
        {
            limits = new RateLimits();
        }

        /**
         * set limit for the rate limits
         */
        public Builder setLimit(int limit)
        {
            limits.limit = limit;
            return this;
        }

        /**
         * set remaining for the rate limits
         */
        public Builder setRemaining(int remaining)
        {
            limits.remaining = remaining;
            return this;
        }

        /**
         * set reset for the rate limits
         */
        public Builder setReset(int reset)
        {
            limits.reset = reset;
            return this;
        }

        public RateLimits build()
        {
            return limits;
        }
    }

}
