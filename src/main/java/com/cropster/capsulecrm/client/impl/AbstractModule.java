package com.cropster.capsulecrm.client.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import retrofit2.Response;
import retrofit2.Retrofit;

import com.cropster.capsulecrm.client.CArray;
import com.cropster.capsulecrm.client.Constants;
import com.cropster.capsulecrm.client.Utils;
import com.cropster.capsulecrm.client.model.Filter;
import com.cropster.capsulecrm.client.model.FilterCondition;
import com.cropster.capsulecrm.client.model.PageLinks;

/**
 * @author Casey Link
 * created on 2017-09-19
 */
abstract class AbstractModule<T>
{
    final T service;

    final Executor callbackExecutor;

    AbstractModule(Retrofit retrofit, Executor callbackExecutor)
    {
        this.service = createService(retrofit);
        this.callbackExecutor = callbackExecutor;
    }

    protected abstract T createService(Retrofit retrofit);

    public void validateFilters(Filter filters)
    {
        Utils.validateNonNull(filters, "Cannot filter with empty filters");
        filters.validate();
    }

    protected Map<String, String> defaultTagParams()
    {
        HashMap<String, String> params = new HashMap<>();
        params.put("perPage", "100");
        return params;
    }

    protected Map<String, String> defaultPartyParams()
    {
        HashMap<String, String> params = new HashMap<>();
        params.put("embed", "tags,fields");
        return params;
    }

    protected FilterCondition hasTag(String tag)
    {
        return FilterCondition.builder()
                .field("tag")
                .operator(Constants.FilterOperators.IS)
                .value(tag)
                .build();

    }

    protected FilterCondition isOrganisation()
    {
        return FilterCondition.builder()
                .field("type")
                .operator(Constants.FilterOperators.IS)
                .value("organisation")
                .build();

    }

    protected <E> CArray<E> fromResponse(Response<? extends ArrayList<E>> response)
    {
        PageLinks links = new PageLinks(response.headers());
        return new CArray<>(response.body(), links);
    }

}
