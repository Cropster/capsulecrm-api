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

/**
 * Interceptor to add authorization header to requests
 */
public class AuthorizationHeaderInterceptor extends HeaderInterceptor
{
    public static final String HEADER_NAME = "Authorization";

    /**
     * Create Header interceptor, saving parameters.
     *
     * @param token the access token to be used with *every* request.
     */
    public AuthorizationHeaderInterceptor(String token)
    {
        super(HEADER_NAME, "Bearer " + token);
    }

}
