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

package com.cropster.capsulecrm.client;

/**
 * Custom logger interface, used for logging network traffic.
 */
public interface Logger
{
    /**
     * Abstract method to be implemented by client.
     * <p>
     * This method gets called, once there is something to log
     *
     * @param message to be delivered to the logger.
     */
    void log(String message);

    /**
     * Determine the level of logging
     */
    enum Level
    {
        /**
         * Do not log anything.
         */
        NONE,

        /**
         * Do basic logging, not all requests will be logged.
         */
        BASIC,

        /**
         * Log all requests.
         */
        FULL
    }
}
