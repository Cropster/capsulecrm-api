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

import com.cropster.capsulecrm.client.model.CapsuleApiException;

/**
 * This interceptor will only be used for throwing an exception, once the server returns an error.
 */
public class ErrorInterceptor implements Interceptor
{

    /**
     * Intercepts chain to check for unsuccessful requests.
     *
     * @param chain provided by the framework to check
     * @return the response if no error occurred
     * @throws IOException will get thrown if response code is unsuccessful
     */
    @Override
    public Response intercept(Chain chain) throws IOException
    {
        final Request request = chain.request();
        final Response response = chain.proceed(request);

        if (!response.isSuccessful())
        {
            throw new CapsuleApiException(request, response);
        }

        return response;
    }
}
