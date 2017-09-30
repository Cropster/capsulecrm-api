package com.cropster.capsulecrm.client.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * @author Casey Link
 * created on 2017-09-28
 */
class CustomFieldTest
{

    @Test
    public void testCustomFieldTypes()
    {
        CustomField f = CustomField.builder().build();
        assertThat(f.getValue()).isNull();

        f = CustomField.builder().valueBool(true).build();
        assertThat(f.getValue()).isEqualTo("true");

        f = CustomField.builder().valueBool(false).build();
        assertThat(f.getValue()).isEqualTo("false");

        f = CustomField.builder().valueDate(LocalDate.parse("2010-01-01")).build();
        assertThat(f.getValue()).isEqualTo("2010-01-01");

        f = CustomField.builder().valueNumber(42).build();
        assertThat(f.getValue()).isEqualTo("42");

        f = CustomField.builder().valueNumber(10.1f).build();
        assertThat(f.getValue()).isEqualTo("10.1");

        f = CustomField.builder().valueNumber(Math.PI).build();
        assertThat(f.getValue()).isEqualTo(Double.toString(Math.PI));

        f = CustomField.builder().valueNumber(new BigDecimal("10000000000")).build();
        assertThat(f.getValue()).isEqualTo("10000000000");
    }

    @Test
    public void testNestedDefinition()
    {
        CustomField f = CustomField.builder().definitionFromId(20L).build();
        assertThat(f.getDefinition().getId()).isEqualTo(20L);
    }
}
