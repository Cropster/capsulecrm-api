/*
Copyright 2017 Contentful GmbH

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.cropster.capsulecrm.client.interceptor;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Response;

import com.cropster.capsulecrm.client.model.RateLimits;

/**
 * Get informed when ever a rate limit header is encountered.
 */
public class RateLimitInterceptor implements Interceptor
{

    final RateLimitsListener listener;

    /**
     * Create a new ratelimit interceptor.
     *
     * @param listener an object to be informed once a rate limit header is encountered.
     * @throws IllegalArgumentException if listener is null.
     */
    public RateLimitInterceptor(RateLimitsListener listener)
    {
        if (listener == null)
        {
            throw new IllegalArgumentException("listener cannot be null!");
        }
        this.listener = listener;
    }

    /**
     * Intercept a http call.
     *
     * @param chain the current chain of calls.
     * @return a response from this call.
     * @throws IOException if something goes wrong.
     */
    @Override
    public Response intercept(Chain chain) throws IOException
    {
        final Response response = chain.proceed(chain.request());

        final Headers headers = response.headers();
        final Map<String, List<String>> mappedHeaders = headers.toMultimap();
        final RateLimits limits = new RateLimits.DefaultParser().parse(mappedHeaders);

        listener.onRateLimitHeaderReceived(limits);

        return response;
    }
}
