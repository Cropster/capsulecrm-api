package com.cropster.capsulecrm.client.model;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.java.Log;

import com.cropster.capsulecrm.client.Constants;
import com.cropster.capsulecrm.client.TestUtils;

@Log
public class FiltersTest extends BaseTest
{

    @Test
    public void testSerialization() throws JsonProcessingException, JSONException
    {
        Filter filters = Filter.builder()
            .condition(
                FilterCondition.builder()
                    .field("tag")
                    .operator(Constants.FilterOperators.IS)
                    .value("YourTag").build()
            )
            .condition(FilterCondition.builder()
                .field("type")
                .operator(Constants.FilterOperators.IS)
                .value("organisation").build())
            .build();

        String output = serialze(filters);
        String expected = TestUtils.fileToString("filter_list.json");

//        log.info(output);
//        log.info(expected);

        JSONAssert.assertEquals(expected, output, true);
    }
}
