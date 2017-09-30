package com.cropster.capsulecrm.client.impl;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

import com.cropster.capsulecrm.client.model.CustomFieldDefinition;
import com.cropster.capsulecrm.client.model.Filter;
import com.cropster.capsulecrm.client.model.Party;
import com.cropster.capsulecrm.client.model.Tag;

/**
 * @author Casey Link
 * created on 2017-09-19
 */
interface ServiceParty
{

    @GET("parties")
    Flowable<Response<Party.PartyList>> fetchAll();

    @GET("parties")
    Flowable<Response<Party.PartyList>> fetchAll(@QueryMap Map<String, String> query);

    @GET("parties/{partyId}")
    Flowable<Response<Party>> fetchOne(@Path("partyId") Long partyId,
            @QueryMap Map<String, String> query);

    @POST("parties/filters/results")
    Flowable<Response<Party.PartyList>> filter(@Body Filter filters,
            @QueryMap Map<String, String> query);

    @PUT("parties/{partyId}")
    Flowable<Response<Party>> update(@Path("partyId") Long partyId, @Body Party party);

    @GET("parties/fields/definitions")
    Flowable<Response<CustomFieldDefinition.CustomFieldDefinitionList>> definitions(
            @QueryMap Map<String, String> query);

    @GET("parties/fields/definitions/{fieldId}")
    Flowable<Response<CustomFieldDefinition>> definition(@Path("fieldId") Long fieldId);

    @GET("parties/tags")
    Flowable<Response<Tag.TagList>> tags(@QueryMap Map<String, String> query);

    @GET("parties/tags/{tagId}")
    Flowable<Response<Tag>> tag(@Path("tagId") Long tagId);

    @GET
    Flowable<Response<Party.PartyList>> followLink(@Url String link);

}

