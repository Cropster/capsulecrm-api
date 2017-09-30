package com.cropster.capsulecrm.client.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Wither;

/**
 * @author Casey Link
 * created on 2017-01-18
 */
@Builder(toBuilder = true)
@Wither
@Value
@JsonRootName("tagdefinitions")
@JsonDeserialize(builder = TagDefinition.TagDefinitionBuilder.class)
public class TagDefinition
{
    private Long id;
    private String name;
    private String description;
    private boolean dataTag;

    private List<NestedCustomFieldDefinition> definitions;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class TagDefinitionBuilder
    {
    }
}
