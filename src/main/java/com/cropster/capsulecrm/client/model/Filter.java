package com.cropster.capsulecrm.client.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

/*
 * The capsule api expects the filters data structure to have the form:
 * <pre><code>
 *   {
 *      "filter": {
 *        "conditions": [
 *           {
 *           ...
 *           }
 *        ]
 *       }
 *   }
 * </code></pre>
*/
@Value
@Builder
@JsonRootName("filter")
public class Filter
{
    @Singular
    List<FilterCondition> conditions;

    public void validate()
    {
        if (getConditions() == null || getConditions().isEmpty())
            throw new IllegalArgumentException("filters cannot be empty");
    }
}
