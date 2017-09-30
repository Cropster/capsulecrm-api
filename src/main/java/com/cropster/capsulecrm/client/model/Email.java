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
@JsonRootName("emailAddresses")
@JsonDeserialize(builder = Email.EmailBuilder.class)
public class Email
{
    private Long id;
    private String type;
    private String address;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class EmailBuilder
    {
    }
}
