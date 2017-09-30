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

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * This class adds custom headers to all requests it intercepts.
 */
public class HeaderInterceptor implements Interceptor
{
    private final String name;
    private final String value;

    /**
     * Create an arbitrary header adding interceptor.
     *
     * @param name  of the header to be used.
     * @param value value of the new header.
     */
    public HeaderInterceptor(String name, String value)
    {
        this.name = name;
        this.value = value;
    }

    /**
     * Method called by framework, to enrich current request chain with requested header information.
     *
     * @param chain the execution chain for the request.
     * @return the response received.
     * @throws IOException in case of failure down the line.
     */
    @Override
    public Response intercept(Chain chain) throws IOException
    {
        final Request request = chain.request();

        return chain.proceed(request.newBuilder()
                .addHeader(name, value)
                .build());
    }

    /**
     * @return the name of this header.
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return the value of this header.
     */
    public String getValue()
    {
        return value;
    }

}
