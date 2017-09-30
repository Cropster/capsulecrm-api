package com.cropster.capsulecrm.client.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@JsonRootName("tags")
@JsonDeserialize(builder = Tag.TagBuilder.class)
public class Tag
{
    private Long id;
    private String name;
    private String description;
    private boolean dataTag;
    @JsonProperty("_delete")
    private Boolean delete;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class TagBuilder
    {
        TagBuilder _delete(boolean _delete)
        {
            this.delete = _delete;
            return this;
        }
    }

    @JsonRootName("tags")
    public static class TagList extends ArrayList<Tag>
    {
    }
}
