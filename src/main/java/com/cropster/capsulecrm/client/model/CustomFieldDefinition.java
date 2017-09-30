package com.cropster.capsulecrm.client.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.experimental.Wither;

/**
 * @author Casey Link
 * created on 2017-01-17
 */
@Builder(toBuilder = true)
@Wither
@Value
@JsonRootName("definitions")
@JsonDeserialize(builder = CustomFieldDefinition.CustomFieldDefinitionBuilder.class)
public class CustomFieldDefinition
{
    private Long id;
    private String name;
    private String description;
    private CustomFieldTypes type;
    private int displayOrder;
    private Tag tag;
    private String captureRule;
    @Singular private List<String> options;

    public enum CustomFieldTypes
    {
        @JsonProperty("text") TEXT,
        @JsonProperty("date") DATE,
        @JsonProperty("list") LIST,
        @JsonProperty("boolean") BOOLEAN,
        @JsonProperty("number") NUMBER;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class CustomFieldDefinitionBuilder
    {
    }

    @JsonRootName("definitions")
    public static class CustomFieldDefinitionList extends ArrayList<CustomFieldDefinition>
    {
    }
}
