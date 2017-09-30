package com.cropster.capsulecrm.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class FilterCondition
{
    private final String field;
    private final String operator;
    private final String value;

}
