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
@JsonRootName("addresses")
@JsonDeserialize(builder = Address.AddressBuilder.class)
public class Address
{
    private Long id;
    private String type;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zip;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class AddressBuilder
    {
    }
}
