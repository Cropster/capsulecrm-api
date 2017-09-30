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
@JsonRootName("phoneNumbers")
@JsonDeserialize(builder = Phone.PhoneBuilder.class)
public class Phone
{
    private Long id;
    private String type;
    private String number;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class PhoneBuilder
    {
    }
}
