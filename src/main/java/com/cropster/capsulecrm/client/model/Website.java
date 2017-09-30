package com.cropster.capsulecrm.client.model;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Wither;

/**
 * @author Casey Link
 * created on 2017-01-17
 */
@Builder(toBuilder = true)
@Wither
@Value
@JsonRootName("websites")
@JsonDeserialize(builder = Website.WebsiteBuilder.class)
public class Website
{
    private Long id;
    private String type;
    private String service;
    private String address;
    private String url;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class WebsiteBuilder
    {
    }

}
