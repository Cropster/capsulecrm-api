package com.cropster.capsulecrm.client.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;

import io.reactivex.Flowable;
import retrofit2.Response;
import retrofit2.Retrofit;

import com.cropster.capsulecrm.client.CArray;
import com.cropster.capsulecrm.client.Constants;
import com.cropster.capsulecrm.client.ModuleParty;
import com.cropster.capsulecrm.client.Utils;
import com.cropster.capsulecrm.client.model.CustomField;
import com.cropster.capsulecrm.client.model.CustomFieldDefinition;
import com.cropster.capsulecrm.client.model.Filter;
import com.cropster.capsulecrm.client.model.FilterCondition;
import com.cropster.capsulecrm.client.model.Party;
import com.cropster.capsulecrm.client.model.Tag;

/**
 * @author Casey Link
 * created on 2017-09-19
 */
public class ModulePartyImpl extends AbstractModule<ServiceParty> implements ModuleParty
{
    Set<FilterCondition> conditions = new HashSet<>();

    public ModulePartyImpl(final Retrofit retrofit, final Executor callbackExecutor)
    {
        super(retrofit, callbackExecutor);
    }

    @Override
    protected ServiceParty createService(final Retrofit retrofit)
    {
        return retrofit.create(ServiceParty.class);
    }

    @Override
    public ModuleParty ofType(Party.PartyType partyType)
    {
        conditions.add(FilterCondition.builder()
                .field("type")
                .operator(Constants.FilterOperators.IS)
                .value(partyType.name().toLowerCase())
                .build());
        return this;
    }

    @Override
    public ModuleParty withTag(String tag)
    {
        conditions.add(hasTag(tag));
        return this;
    }

    @Override
    public CArray<Party> all()
    {
        return this.all(defaultPartyParams());
    }

    @Override
    public CArray<Party> all(Map<String, String> query)
    {
        if (conditions.isEmpty())
            return fromResponse(
                    service.fetchAll().blockingFirst()
            );
        else
            return fromResponse(
                    filter(
                            Filter.builder().conditions(conditions).build()
                    ).blockingFirst());
    }

    @Override
    public CArray<Party> nextPage(final CArray<Party> prevPage)
    {
        return fromResponse(service.followLink(prevPage.getLink()).blockingFirst());
    }

    @Override
    public Optional<Party> one(Long id)
    {
        return Optional
                .ofNullable(service.fetchOne(id, defaultPartyParams()).blockingFirst().body());
    }

    @Override
    public Optional<Party> update(Party party)
    {
        Utils.validateNonNull(party.getId(), "To update a party it must already exist");
        return Optional.ofNullable(
                service.update(party.getId(), party)
                        .blockingFirst()
                        .body());
    }

    @Override
    public CArray<CustomFieldDefinition> definitions()
    {
        return fromResponse(
                service.definitions(defaultTagParams())
                        .blockingFirst()
        );
    }

    @Override
    public Optional<CustomFieldDefinition> definition(long id)
    {
        return Optional.ofNullable(service.definition(id).blockingFirst().body());
    }

    @Override
    public CArray<Tag> tags()
    {
        return fromResponse(
                service.tags(defaultTagParams())
                        .blockingFirst()
        );
    }

    @Override
    public Optional<Tag> tag(long id)
    {
        return Optional.ofNullable(service.tag(id).blockingFirst().body());
    }

    @Override
    public Optional<Party> remove(Party party, Tag tag)
    {
        return update(
                Party.builder()
                        .id(party.getId())
                        .deleteTag(tag)
                        .build()
        );
    }

    @Override
    public Optional<Party> remove(Party party, CustomField field)
    {
        return update(
                Party.builder()
                        .id(party.getId())
                        .deleteField(field)
                        .build()
        );
    }

    @Override
    public Optional<Party> add(Party party, Tag tag)
    {
        return update(
                Party.builder()
                        .id(party.getId())
                        .tag(tag)
                        .build()
        );
    }

    @Override
    public Optional<Party> add(Party party, CustomField field)
    {
        return update(
                Party.builder()
                        .id(party.getId())
                        .field(field)
                        .build()
        );
    }

    protected Flowable<Response<Party.PartyList>> filter(Filter filters)
    {
        validateFilters(filters);

        return service.filter(filters, defaultPartyParams());
    }
}
