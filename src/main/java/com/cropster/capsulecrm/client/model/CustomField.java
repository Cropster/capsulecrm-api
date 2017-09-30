package com.cropster.capsulecrm.client.model;

import java.time.LocalDate;

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
@JsonRootName("fields")
@JsonDeserialize(builder = CustomField.CustomFieldBuilder.class)
public class CustomField
{
    private Long id;
    private String value;
    private NestedCustomFieldDefinition definition;

    @JsonProperty("_delete")
    private Boolean delete;

    public LocalDate valueAsDate()
    {
        return LocalDate.parse(value);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class CustomFieldBuilder
    {
        CustomFieldBuilder _delete(boolean _delete)
        {
            this.delete = _delete;
            return this;
        }

        public CustomFieldBuilder definitionFromId(Long fieldDefinitionId)
        {
            return this.definition(NestedCustomFieldDefinition.builder()
                    .id(fieldDefinitionId)
                    .build());
        }

        public CustomFieldBuilder value(String value)
        {
            this.value = value;
            return this;
        }

        public CustomFieldBuilder valueBool(boolean value)
        {
            return this.value(Boolean.toString(value));
        }

        public CustomFieldBuilder valueDate(LocalDate value)
        {
            return this.value(value.toString());
        }

        public CustomFieldBuilder valueNumber(Number number)
        {
            return this.value(number.toString());
        }
    }

}
