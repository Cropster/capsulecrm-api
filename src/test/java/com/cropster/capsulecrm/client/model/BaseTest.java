package com.cropster.capsulecrm.client.model;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.extern.java.Log;
import okhttp3.mockwebserver.MockWebServer;

import com.cropster.capsulecrm.client.CapsuleClient;
import com.cropster.capsulecrm.client.Logger;

/**
 * @author Casey Link
 * created on 2017-09-20
 */
@Log
public abstract class BaseTest
{
    protected ObjectMapper objectMapper;
    protected MockWebServer server;
    protected CapsuleClient client;
    protected StringBuilder clientLog;

    public BaseTest()
    {
        objectMapper = CapsuleClient.Builder.defaultObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @BeforeEach
    public void setup() throws IOException
    {
        server = new MockWebServer();
        server.start();

        clientLog = new StringBuilder();
        client = CapsuleClient.builder()
            .accessToken("test")
            .objectMapper(objectMapper)
            .userAgent("CapsuleAPI Test")
            .endpoint(server.url("/api/v2/").toString())
            .logLevel(Logger.Level.FULL)
            .logger(log::info)
            .build();
    }

    @AfterEach
    public void teardown() throws IOException
    {
        server.shutdown();
    }

    protected String serialze(Object value) throws JsonProcessingException
    {
        return this.objectMapper.writeValueAsString(value);
    }
}
